package com.lne_paladins.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.registry.SpellRegistry;
import net.spell_engine.internals.SpellHelper;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class TemplarsSkySplitterProjectile extends ProjectileEntity {
    public static EntityType<TemplarsSkySplitterProjectile> ENTITY_TYPE;

    private static final float BASE_HITBOX = 0.6F;
    private static final double IMPACT_SEARCH_RADIUS = 2.0;
    private static final int MAX_AGE = 100;
    private static final int GROUND_LINGER_TICKS = 20;

    public static final float INITIAL_FALL_SPEED = 0.05F;
    private static final float FALL_ACCELERATION = 0.05F;
    private static final float MAX_FALL_SPEED = 1.2F;

    private RegistryEntry<Spell> spellEntry;
    private SpellHelper.ImpactContext context;
    private float scale = 1F;
    private boolean landed = false;
    private int groundTicks = 0;
    private final Set<Integer> struckEntityIds = new HashSet<>();

    private static final TrackedData<String> TRACKER_SPELL_ID =
            DataTracker.registerData(TemplarsSkySplitterProjectile.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Float> TRACKER_SCALE =
            DataTracker.registerData(TemplarsSkySplitterProjectile.class, TrackedDataHandlerRegistry.FLOAT);

    public TemplarsSkySplitterProjectile(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public TemplarsSkySplitterProjectile(World world, LivingEntity owner, RegistryEntry<Spell> spellEntry, SpellHelper.ImpactContext context, float scale) {
        super(ENTITY_TYPE, world);
        this.setOwner(owner);
        this.spellEntry = spellEntry;
        this.context = context;
        this.scale = scale;
        this.getDataTracker().set(TRACKER_SPELL_ID, spellEntry.getKey().get().getValue().toString());
        this.getDataTracker().set(TRACKER_SCALE, scale);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(TRACKER_SPELL_ID, "");
        builder.add(TRACKER_SCALE, 1F);
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        super.onTrackedDataSet(data);
        if (data.equals(TRACKER_SCALE)) {
            this.scale = this.getDataTracker().get(TRACKER_SCALE);
        } else if (this.getWorld().isClient && data.equals(TRACKER_SPELL_ID)) {
            var spellId = this.getDataTracker().get(TRACKER_SPELL_ID);
            if (spellId != null && !spellId.isEmpty()) {
                this.spellEntry = SpellRegistry.from(this.getWorld()).getEntry(Identifier.of(spellId)).orElse(null);
            }
        }
    }

    public float getRenderScale() {
        return scale;
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        var size = BASE_HITBOX * scale;
        return EntityDimensions.changing(size, size);
    }

    @Override
    public void tick() {
        super.tick();

        if (landed) {
            if (!this.getWorld().isClient) {
                groundTicks++;
                if (groundTicks >= GROUND_LINGER_TICKS) {
                    this.discard();
                }
            }
            return;
        }

        if (this.age > MAX_AGE) {
            land(null);
            return;
        }

        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        if (hitResult.getType() != HitResult.Type.MISS) {
            this.onCollision(hitResult);
            if (landed) return;
        }

        this.checkBlockCollision();

        var fallSpeed = Math.min(-(float) this.getVelocity().y + FALL_ACCELERATION, MAX_FALL_SPEED);
        this.setVelocity(0, -fallSpeed, 0);
        this.setPosition(this.getX(), this.getY() - fallSpeed, this.getZ());
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        var target = entityHitResult.getEntity();
        struckEntityIds.add(target.getId());
        applyImpact(target);
    }

    @Override
    protected boolean canHit(Entity entity) {
        return super.canHit(entity) && !struckEntityIds.contains(entity.getId());
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        land(nearestEnemy(blockHitResult.getPos()));
    }

    @Nullable
    private Entity nearestEnemy(Vec3d position) {
        var owner = this.getOwner();
        var box = new Box(position, position).expand(IMPACT_SEARCH_RADIUS);
        Entity nearest = null;
        var nearestDistance = Double.MAX_VALUE;
        for (var candidate : this.getWorld().getOtherEntities(this, box,
                e -> e != owner && e.isAttackable() && !struckEntityIds.contains(e.getId()))) {
            var distance = candidate.getPos().squaredDistanceTo(position);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearest = candidate;
            }
        }
        return nearest;
    }

    private void applyImpact(@Nullable Entity target) {
        if (this.getWorld().isClient) return;
        if (this.getOwner() instanceof LivingEntity caster && this.spellEntry != null) {
            var impactTarget = target != null ? target : caster;
            var impactContext = this.context != null ? this.context : new SpellHelper.ImpactContext();
            SpellHelper.projectileImpact(caster, this, impactTarget, this.spellEntry, impactContext.position(this.getPos()));
        }
    }

    private void land(@Nullable Entity target) {
        if (landed) return;
        landed = true;
        this.setVelocity(Vec3d.ZERO);
        applyImpact(target);
    }

    private static final String NBT_SPELL_ID = "SpellId";
    private static final String NBT_SCALE = "Scale";

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.spellEntry != null) {
            nbt.putString(NBT_SPELL_ID, this.spellEntry.getKey().get().getValue().toString());
        }
        nbt.putFloat(NBT_SCALE, scale);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains(NBT_SPELL_ID)) {
            var spellId = Identifier.of(nbt.getString(NBT_SPELL_ID));
            this.spellEntry = SpellRegistry.from(this.getWorld()).getEntry(spellId).orElse(null);
        }
        if (nbt.contains(NBT_SCALE)) {
            this.scale = nbt.getFloat(NBT_SCALE);
        }
    }
}
