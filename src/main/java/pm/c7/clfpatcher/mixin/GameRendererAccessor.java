package pm.c7.clfpatcher.mixin;

import net.minecraft.sortme.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRenderer.class)
public interface GameRendererAccessor {
    @Invoker("method_1848")
    float getFOVModifier(float f);
}
