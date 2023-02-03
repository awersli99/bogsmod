package bog.bogsmod.entity;

import java.util.List;

import bog.bogsmod.BogsMod;
import net.minecraft.src.*;
import net.minecraft.src.helper.DamageType;

public class EntitySteelBoat extends Entity {
    public int boatCurrentDamage = 0;
    public int boatTimeSinceHit = 0;
    public int boatRockDirection = 1;
    private int field_9394_d;
    private double field_9393_e;
    private double field_9392_f;
    private double field_9391_g;
    private double field_9390_h;
    private double boatPitch;
    private double velocityX;
    private double velocityY;
    private double velocityZ;

    public EntitySteelBoat(World world) {
        super(world);
        this.preventEntitySpawning = true;
        this.setSize(1.5f, 0.6f);
        this.yOffset = this.height / 2.0f;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public AxisAlignedBB getCollisionBox(Entity entity) {
        return entity.boundingBox;
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    public EntitySteelBoat(World world, double d, double d1, double d2) {
        this(world);
        this.setPosition(d, d1 + (double)this.yOffset, d2);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = d;
        this.prevPosY = d1;
        this.prevPosZ = d2;
    }

    @Override
    public double getMountedYOffset() {
        return (double)this.height * 0.0 - (double)0.3f;
    }

    @Override
    public boolean attackEntityFrom(Entity entity, int i, DamageType type) {
        if (this.worldObj.isMultiplayerAndNotHost || this.isDead) {
            return true;
        }
        if (entity instanceof EntityPlayer && ((EntityPlayer)entity).getGamemode() == Gamemode.creative) {
            this.setEntityDead();
            return true;
        }
        this.boatRockDirection = -this.boatRockDirection;
        this.boatTimeSinceHit = 10;
        this.boatCurrentDamage += i * 10;
        this.setBeenAttacked();
        if (this.boatCurrentDamage > 40) {
            if (entity instanceof EntityPlayer) {
                if (this.riddenByEntity != null) {
                    this.riddenByEntity.mountEntity(this);
                }
                this.dropItemWithOffset(BogsMod.steelBoat.itemID, 1, 0.0f);
                this.setEntityDead();
            }
        }
        return true;
    }

    @Override
    public void performHurtAnimation() {
        this.boatRockDirection = -this.boatRockDirection;
        this.boatTimeSinceHit = 10;
        this.boatCurrentDamage += this.boatCurrentDamage * 10;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public void setPositionAndRotation2(double d, double d1, double d2, float f, float f1, int i) {
        this.field_9393_e = d;
        this.field_9392_f = d1;
        this.field_9391_g = d2;
        this.field_9390_h = f;
        this.boatPitch = f1;
        this.field_9394_d = i + 4;
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }

    @Override
    public void setVelocity(double d, double d1, double d2) {
        this.velocityX = this.motionX = d;
        this.velocityY = this.motionY = d1;
        this.velocityZ = this.motionZ = d2;
    }

    @Override
    public void onUpdate() {
        double d19;
        double maxSpeed;
        super.onUpdate();
        if (this.boatTimeSinceHit > 0) {
            --this.boatTimeSinceHit;
        }
        if (this.boatCurrentDamage > 0) {
            --this.boatCurrentDamage;
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        int i = 5;
        double d = 0.0;
        for (int j = 0; j < i; ++j) {
            double d5 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)j / (double)i - 0.125;
            double d9 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(j + 1) / (double)i - 0.125;
            AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBoxFromPool(this.boundingBox.minX, d5, this.boundingBox.minZ, this.boundingBox.maxX, d9, this.boundingBox.maxZ);
            if (!this.worldObj.isAABBInMaterial(axisalignedbb, Material.water)) continue;
            d += 1.0 / (double)i;
        }
        if (this.worldObj.isMultiplayerAndNotHost) {
            if (this.field_9394_d > 0) {
                double d14;
                double d1 = this.posX + (this.field_9393_e - this.posX) / (double)this.field_9394_d;
                double d6 = this.posY + (this.field_9392_f - this.posY) / (double)this.field_9394_d;
                double d10 = this.posZ + (this.field_9391_g - this.posZ) / (double)this.field_9394_d;
                for (d14 = this.field_9390_h - (double)this.rotationYaw; d14 < -180.0; d14 += 360.0) {
                }
                while (d14 >= 180.0) {
                    d14 -= 360.0;
                }
                this.rotationYaw = (float)((double)this.rotationYaw + d14 / (double)this.field_9394_d);
                this.rotationPitch = (float)((double)this.rotationPitch + (this.boatPitch - (double)this.rotationPitch) / (double)this.field_9394_d);
                --this.field_9394_d;
                this.setPosition(d1, d6, d10);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            } else {
                double d2 = this.posX + this.motionX;
                double d7 = this.posY + this.motionY;
                double d11 = this.posZ + this.motionZ;
                this.setPosition(d2, d7, d11);
                if (this.onGround) {
                    this.motionX *= 0.5;
                    this.motionY *= 0.5;
                    this.motionZ *= 0.5;
                }
                this.motionX *= (double)0.99f;
                this.motionY *= (double)0.95f;
                this.motionZ *= (double)0.99f;
            }
            return;
        }
        if (d < 1.0) {
            double d3 = d * 2.0 - 1.0;
            this.motionY += (double)0.04f * d3;
        } else {
            if (this.motionY < 0.0) {
                this.motionY /= 2.0;
            }
            this.motionY += (double)0.007f;
        }
        if (this.riddenByEntity != null) {
            this.motionX += this.riddenByEntity.motionX * 0.8;
            this.motionZ += this.riddenByEntity.motionZ * 0.8;
        }
        if (this.motionX < -(maxSpeed = 2.4)) {
            this.motionX = -maxSpeed;
        }
        if (this.motionX > maxSpeed) {
            this.motionX = maxSpeed;
        }
        if (this.motionZ < -maxSpeed) {
            this.motionZ = -maxSpeed;
        }
        if (this.motionZ > maxSpeed) {
            this.motionZ = maxSpeed;
        }
        if (this.onGround) {
            this.motionX *= 0.5;
            this.motionY *= 0.5;
            this.motionZ *= 0.5;
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        double d8 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        if (d8 > 0.15) {
            double d12 = Math.cos((double)this.rotationYaw * Math.PI / 180.0);
            double d15 = Math.sin((double)this.rotationYaw * Math.PI / 180.0);
            int i1 = 0;
            while ((double)i1 < 1.0 + d8 * 60.0) {
                double d18 = this.rand.nextFloat() * 2.0f - 1.0f;
                double d20 = (double)(this.rand.nextInt(2) * 2 - 1) * 0.7;
                if (this.rand.nextBoolean()) {
                    double d21 = this.posX - d12 * d18 * 0.8 + d15 * d20;
                    double d23 = this.posZ - d15 * d18 * 0.8 - d12 * d20;
                    this.worldObj.spawnParticle("splash", d21, this.posY - 0.125, d23, this.motionX, this.motionY, this.motionZ);
                } else {
                    double d22 = this.posX + d12 + d15 * d18 * 0.7;
                    double d24 = this.posZ + d15 - d12 * d18 * 0.7;
                    this.worldObj.spawnParticle("splash", d22, this.posY - 0.125, d24, this.motionX, this.motionY, this.motionZ);
                }
                ++i1;
            }
        }
        this.motionX *= (double)0.99f;
        this.motionY *= (double)0.95f;
        this.motionZ *= (double)0.99f;
        this.rotationPitch = 0.0f;
        double d13 = this.rotationYaw;
        double d16 = this.prevPosX - this.posX;
        double d17 = this.prevPosZ - this.posZ;
        if (d16 * d16 + d17 * d17 > 0.001) {
            d13 = (float)(Math.atan2(d17, d16) * 180.0 / Math.PI);
        }
        for (d19 = d13 - (double)this.rotationYaw; d19 >= 180.0; d19 -= 360.0) {
        }
        while (d19 < -180.0) {
            d19 += 360.0;
        }
        if (d19 > 20.0) {
            d19 = 20.0;
        }
        if (d19 < -20.0) {
            d19 = -20.0;
        }
        this.rotationYaw = (float)((double)this.rotationYaw + d19);
        this.setRotation(this.rotationYaw, this.rotationPitch);
        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.2f, 0.0, 0.2f));
        if (list != null && list.size() > 0) {
            for (int j1 = 0; j1 < list.size(); ++j1) {
                Entity entity = list.get(j1);
                if (entity == this.riddenByEntity || !entity.canBePushed() || !(entity instanceof EntitySteelBoat)) continue;
                entity.applyEntityCollision(this);
            }
        }
        for (int k1 = 0; k1 < 4; ++k1) {
            int j2;
            int i2;
            int l1 = MathHelper.floor_double(this.posX + ((double)(k1 % 2) - 0.5) * 0.8);
            if (this.worldObj.getBlockId(l1, i2 = MathHelper.floor_double(this.posY), j2 = MathHelper.floor_double(this.posZ + ((double)(k1 / 2) - 0.5) * 0.8)) != Block.layerSnow.blockID) continue;
            this.worldObj.setBlockWithNotify(l1, i2, j2, 0);
        }
        if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
            this.riddenByEntity = null;
        }
    }

    @Override
    public void updateRiderPosition() {
        if (this.riddenByEntity == null) {
            return;
        }
        double d = Math.cos((double)this.rotationYaw * Math.PI / 180.0) * 0.4;
        double d1 = Math.sin((double)this.rotationYaw * Math.PI / 180.0) * 0.4;
        this.riddenByEntity.setPosition(this.posX + d, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + d1);
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
    }

    @Override
    public float getShadowSize() {
        return 0.0f;
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != entityplayer) {
            return true;
        }
        if (!this.worldObj.isMultiplayerAndNotHost) {
            entityplayer.mountEntity(this);
        }
        return true;
    }

    @Override
    public Entity ejectEntity() {
        Entity entity = super.ejectEntity();
        if (entity == null) {
            return null;
        }
        entity.setLocationAndAngles(this.posX, this.boundingBox.maxY + 0.1, this.posZ, entity.rotationYaw, entity.rotationPitch);
        return entity;
    }
}
