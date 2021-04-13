package pm.c7.clfpatcher.skins;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedModel;

public class PlayerModel extends BipedModel {
    public ModelPart leftSleeve;
    public ModelPart rightSleeve;
    public ModelPart leftPantLeg;
    public ModelPart rightPantLeg;
    public ModelPart jacket;

    public PlayerModel(float scale, boolean slim) {
        super(scale);

        if (slim) {
            this.leftArm = createModelPart(32, 48);
            this.leftArm.addCuboid(-1.0F, -2.0F, -2.0F, 3, 12, 4, scale);
            this.leftArm.setPivot(5.0F, 2.5F, 0.0F);
            this.rightArm = createModelPart(40, 16);
            this.rightArm.addCuboid(-2.0F, -2.0F, -2.0F, 3, 12, 4, scale);
            this.rightArm.setPivot(-5.0F, 2.5F, 0.0F);

            this.leftSleeve = createModelPart(48, 48);
            this.leftSleeve.addCuboid(-1.0F, -2.0F, -2.0F, 3, 12, 4, scale + 0.25F);
            this.leftSleeve.setPivot(5.0F, 2.5F, 0.0F);
            this.rightSleeve = createModelPart(40, 32);
            this.rightSleeve.addCuboid(-2.0F, -2.0F, -2.0F, 3, 12, 4, scale + 0.25F);
            this.rightSleeve.setPivot(-5.0F, 2.5F, 0.0F);
        } else {
            this.leftArm = createModelPart(32, 48);
            this.leftArm.addCuboid(-1.0F, -2.0F, -2.0F, 4, 12, 4, scale);
            this.leftArm.setPivot(5.0F, 2.0F, 0.0F);

            this.leftSleeve = createModelPart(48, 48);
            this.leftSleeve.addCuboid(-1.0F, -2.0F, -2.0F, 4, 12, 4, scale + 0.25F);
            this.leftSleeve.setPivot(5.0F, 2.0F, 0.0F);
            this.rightSleeve = createModelPart(40, 32);
            this.rightSleeve.addCuboid(-3.0F, -2.0F, -2.0F, 4, 12, 4, scale + 0.25F);
            this.rightSleeve.setPivot(-5.0F, 2.0F, 0.0F);
        }

        this.leftLeg = createModelPart(16, 48);
        this.leftLeg.addCuboid(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale);
        this.leftLeg.setPivot(2.0F, 12.0F, 0.0F);

        this.leftPantLeg = createModelPart(0, 48);
        this.leftPantLeg.addCuboid(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale + 0.25F);
        this.leftPantLeg.setPivot(2.0F, 12.0F, 0.0F);
        this.rightPantLeg = createModelPart(0, 32);
        this.rightPantLeg.addCuboid(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale + 0.25F);
        this.rightPantLeg.setPivot(-2.0F, 12.0F, 0.0F);

        this.jacket = createModelPart(16, 32);
        this.jacket.addCuboid(-4.0F, 0.0F, -2.0F, 8, 12, 4, scale + 0.25F);
        this.jacket.setPivot(0.0F, 0.0F, 0.0F);
    }

    private ModelPart createModelPart(int x, int y) {
        ModelPart part = new ModelPart(x, y);
        ((ModelPartAccessor)part).setTextureHeight(64);
        return part;
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(f, f1, f2, f3, f4, f5);

        this.leftSleeve.render(f5);
        this.rightSleeve.render(f5);
        this.leftPantLeg.render(f5);
        this.rightPantLeg.render(f5);
        this.jacket.render(f5);
    }

    public void copyPositionAndRotation(ModelPart from, ModelPart to) {
        to.setPivot(from.pivotX, from.pivotY, from.pivotZ);
        to.pitch = from.pitch;
        to.yaw = from.yaw;
        to.roll = from.roll;
    }

    public void setAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        super.setAngles(f, f1, f2, f3, f4, f5);

        copyPositionAndRotation(this.leftLeg, this.leftPantLeg);
        copyPositionAndRotation(this.rightLeg, this.rightPantLeg);
        copyPositionAndRotation(this.leftArm, this.leftSleeve);
        copyPositionAndRotation(this.rightArm, this.rightSleeve);
        copyPositionAndRotation(this.body, this.jacket);
    }

    public void drawFirstPersonHand() {
        this.rightSleeve.render(0.0625F);
    }
}
