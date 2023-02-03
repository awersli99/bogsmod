package bog.bogsmod.model;

import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelSteelBoat extends ModelBase {
    public ModelRenderer[] boatSides = new ModelRenderer[5];

    public ModelSteelBoat() {
        this.boatSides[0] = new ModelRenderer(0, 8);
        this.boatSides[1] = new ModelRenderer(0, 0);
        this.boatSides[2] = new ModelRenderer(0, 0);
        this.boatSides[3] = new ModelRenderer(0, 0);
        this.boatSides[4] = new ModelRenderer(0, 0);
        int byte0 = 24;
        int byte1 = 6;
        int byte2 = 20;
        int byte3 = 4;
        this.boatSides[0].addBox(-byte0 / 2, -byte2 / 2 + 2, -3.0f, byte0, byte2 - 4, 4, 0.0f);
        this.boatSides[0].setRotationPoint(0.0f, 0 + byte3, 0.0f);
        this.boatSides[1].addBox(-byte0 / 2 + 2, -byte1 - 1, -1.0f, byte0 - 4, byte1, 2, 0.0f);
        this.boatSides[1].setRotationPoint(-byte0 / 2 + 1, 0 + byte3, 0.0f);
        this.boatSides[2].addBox(-byte0 / 2 + 2, -byte1 - 1, -1.0f, byte0 - 4, byte1, 2, 0.0f);
        this.boatSides[2].setRotationPoint(byte0 / 2 - 1, 0 + byte3, 0.0f);
        this.boatSides[3].addBox(-byte0 / 2 + 2, -byte1 - 1, -1.0f, byte0 - 4, byte1, 2, 0.0f);
        this.boatSides[3].setRotationPoint(0.0f, 0 + byte3, -byte2 / 2 + 1);
        this.boatSides[4].addBox(-byte0 / 2 + 2, -byte1 - 1, -1.0f, byte0 - 4, byte1, 2, 0.0f);
        this.boatSides[4].setRotationPoint(0.0f, 0 + byte3, byte2 / 2 - 1);
        this.boatSides[0].rotateAngleX = 1.570796f;
        this.boatSides[1].rotateAngleY = 4.712389f;
        this.boatSides[2].rotateAngleY = 1.570796f;
        this.boatSides[3].rotateAngleY = 3.141593f;
    }

    @Override
    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        for (int i = 0; i < 5; ++i) {
            this.boatSides[i].render(f5);
        }
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
    }
}
