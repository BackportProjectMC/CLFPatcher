package pm.c7.clfpatcher.mixin;

import net.minecraft.client.Minecraft;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.PixelFormat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import pm.c7.clfpatcher.CLFPatcher;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;create()V", ordinal = 0))
    private void bitDepthFix() {
        CLFPatcher.log("Applying bitdepth patch");
        PixelFormat pixelFormat = new PixelFormat();
        pixelFormat = pixelFormat.withDepthBits(24);
        try {
            Display.create(pixelFormat);
        } catch (LWJGLException ignored) {}
    }
}
