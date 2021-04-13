package pm.c7.clfpatcher.mixin.skins;

import net.minecraft.class_552;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import pm.c7.clfpatcher.skins.SizedTexturedQuad;

@Mixin(class_552.class)
public class MixinClass552 {
    @ModifyConstant(method = "<init>([Lnet/minecraft/class_290;IIII)V", constant = @Constant(floatValue = 64.0F))
    private float getWidth(float width) {
        return SizedTexturedQuad.WIDTH;
    }

    @ModifyConstant(method = "<init>([Lnet/minecraft/class_290;IIII)V", constant = @Constant(floatValue = 32.0F))
    private float getHeight(float height) {
        return SizedTexturedQuad.HEIGHT;
    }
}
