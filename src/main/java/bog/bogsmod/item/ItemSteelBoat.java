package bog.bogsmod.item;

import bog.bogsmod.entity.EntitySteelBoat;
import bog.bogsmod.render.RenderSteelBoat;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumMovingObjectType;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class ItemSteelBoat extends Item {
    public ItemSteelBoat(int i) {
        super(i);
        this.maxStackSize = 1;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        float f9;
        float f6;
        float f8;
        double d3;
        float f5;
        float f = 1.0f;
        float f1 = entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * f;
        float f2 = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * f;
        double d = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * (double)f;
        double d1 = entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * (double)f + 1.62 - (double)entityplayer.yOffset;
        double d2 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * (double)f;
        Vec3D vec3d = Vec3D.createVector(d, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.01745329f - 3.141593f);
        float f4 = MathHelper.sin(-f2 * 0.01745329f - 3.141593f);
        float f7 = f4 * (f5 = -MathHelper.cos(-f1 * 0.01745329f));
        Vec3D vec3d1 = vec3d.addVector((double)f7 * (d3 = 5.0), (double)(f8 = (f6 = MathHelper.sin(-f1 * 0.01745329f))) * d3, (double)(f9 = f3 * f5) * d3);
        MovingObjectPosition movingobjectposition = world.rayTraceBlocks_do(vec3d, vec3d1, true);
        if (movingobjectposition == null) {
            return itemstack;
        }
        if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE) {
            int i = movingobjectposition.blockX;
            int j = movingobjectposition.blockY;
            int k = movingobjectposition.blockZ;
            if (!world.isMultiplayerAndNotHost) {
                if (world.getBlockId(i, j, k) == Block.layerSnow.blockID) {
                    --j;
                }
                // TODO: boat.setRotation(entityplayer.rotationYaw - 90.0f, 0.0f);
                EntitySteelBoat boat = new EntitySteelBoat(world, (float)i + 0.5f, (float)j + 1.0f, (float)k + 0.5f);
                world.entityJoinedWorld(boat);
            }
            itemstack.consumeItem(entityplayer);
        }
        return itemstack;
    }
}
