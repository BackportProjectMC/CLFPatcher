package pm.c7.clfpatcher.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sortme.GameRenderer;
import net.minecraft.tile.material.Material;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pm.c7.clfpatcher.CLFPatcher;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {
    @Shadow
    private Minecraft minecraft;
    @Shadow
    private float field_2327;
    @Shadow
    private float field_2365;
    @Shadow
    private float field_2350;
    @Shadow
    private double field_2331 = 1.0D;
    @Shadow
    private double field_2332 = 0.0D;
    @Shadow
    private double field_2333 = 0.0D;

    @Redirect(method = "method_1840", at = @At(value = "INVOKE", target = "Lnet/minecraft/sortme/GameRenderer;method_1848(F)F"))
    private float fov(GameRenderer gameRenderer, float f) {
        LivingEntity var2 = this.minecraft.field_2807;
        float normalized = (CLFPatcher.config.getFloatValue("fov") - 30.0F) / (110.0F - 30.0F);
        float var3 = 70.0F;
        var3 += normalized * 40.0f;
        if (var2.isInFluid(Material.WATER)) {
            var3 = var3 * 60.0F / 70.0F;
        }

        if (var2.health <= 0) {
            float var4 = (float)var2.deathTime + f;
            var3 /= (1.0F - 500.0F / (var4 + 500.0F)) * 2.0F + 1.0F;
        }

        return var3 + this.field_2327 + (this.field_2365 - this.field_2327) * f;
    }

    @Inject(method = "method_1845", at = @At(value = "HEAD"))
    private void fixHandFOV(float f, int i, CallbackInfo info) {
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        float var3 = 0.07F;
        if (this.minecraft.options.anaglyph3d) {
            GL11.glTranslatef((float)(-(i * 2 - 1)) * var3, 0.0F, 0.0F);
        }

        if (this.field_2331 != 1.0D) {
            GL11.glTranslatef((float)this.field_2332, (float)(-this.field_2333), 0.0F);
            GL11.glScaled(this.field_2331, this.field_2331, 1.0D);
            GLU.gluPerspective(((GameRendererAccessor)this).getFOVModifier(f), (float)this.minecraft.actualWidth / (float)this.minecraft.actualHeight, 0.05F, this.field_2350 * 2.0F);
        } else {
            GLU.gluPerspective(((GameRendererAccessor)this).getFOVModifier(f), (float)this.minecraft.actualWidth / (float)this.minecraft.actualHeight, 0.05F, this.field_2350 * 2.0F);
        }

        GL11.glMatrixMode(5888);
    }
}
