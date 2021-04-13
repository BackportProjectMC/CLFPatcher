package pm.c7.clfpatcher.mixin.skins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.Session;
import net.minecraft.entity.player.Player;
import net.minecraft.entity.player.RemoteClientPlayer;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pm.c7.clfpatcher.CLFPatcher;
import pm.c7.clfpatcher.skins.SkinProcessor;

@Mixin(RemoteClientPlayer.class)
public class MixinRemoteClientPlayer extends Player {
    public MixinRemoteClientPlayer(Level arg) {
        super(arg);
    }

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;", ordinal = 0), index = 0)
    private String fixSkinUrl(String orig) {
        return "http://resourceproxy.pymcl.net/skinapi.php?user=";
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void fixSkin(Level arg, String name, CallbackInfo info) {
        String uuid = SkinProcessor.getUUIDFromName(name);
        if (uuid != null) {
            SkinProcessor.SkinMetadata metadata = SkinProcessor.getMetadataFromUUID(uuid);
            if (metadata != null) {
                if (metadata.url != null)
                    this.skinUrl = metadata.url;
                if (metadata.capeUrl != null)
                    this.cloakUrl = metadata.capeUrl;
            }
        }
        String customCape = SkinProcessor.getCustomCape(name);
        if (customCape != null) {
            this.cloakUrl = customCape;
        }
    }

    @Shadow
    public void method_494() {}
}
