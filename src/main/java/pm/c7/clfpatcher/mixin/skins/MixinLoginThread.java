package pm.c7.clfpatcher.mixin.skins;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinLoginThread {
    @Inject(method = "startLoginThread", at = @At("HEAD"), cancellable = true)
    private void noop(CallbackInfo info) {
        info.cancel();
    }
}
