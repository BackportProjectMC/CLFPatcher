package pm.c7.clfpatcher.mixin.skins;

import net.minecraft.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class MixinPlayer {
    @ModifyArg(method = "initCloak", at = @At("HEAD"))
    private void fixCape(CallbackInfo info) {
        Player player = ((Player)(Object)this);
        player.playerCloakUrl = player.cloakUrl;
        info.cancel();
    }
}
