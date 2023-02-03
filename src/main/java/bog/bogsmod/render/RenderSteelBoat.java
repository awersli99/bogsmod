package bog.bogsmod.render;


import bog.bogsmod.entity.EntitySteelBoat;
import net.minecraft.src.Entity;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelBoat;
import net.minecraft.src.Render;
import org.lwjgl.opengl.GL11;

public class RenderSteelBoat extends Render {
    protected ModelBase modelBoat;

    public RenderSteelBoat() {
        this.shadowSize = 0.5f;
        this.modelBoat = new ModelBoat();
    }

    public void func_157_a(EntitySteelBoat entityboat, double d, double d1, double d2, float f, float f1) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glRotatef(180.0f - f, 0.0f, 1.0f, 0.0f);
        float f2 = (float)entityboat.boatTimeSinceHit - f1;
        float f3 = (float)entityboat.boatCurrentDamage - f1;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f2 > 0.0f) {
            GL11.glRotatef(MathHelper.sin(f2) * f2 * f3 / 10.0f * (float)entityboat.boatRockDirection, 1.0f, 0.0f, 0.0f);
        }
        this.loadTexture("/terrain.png");
        float f4 = 0.75f;
        GL11.glScalef(f4, f4, f4);
        GL11.glScalef(1.0f / f4, 1.0f / f4, 1.0f / f4);
        this.loadTexture("/assets/bogsmod/model/SteelBoatModel.png");
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        this.modelBoat.render(0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        this.func_157_a((EntitySteelBoat)entity, d, d1, d2, f, f1);
    }
}
