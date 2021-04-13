package pm.c7.clfpatcher.mixin.skins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.Session;
import net.minecraft.entity.player.AbstractClientPlayer;
import net.minecraft.entity.player.Player;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pm.c7.clfpatcher.skins.SkinProcessor;

@Mixin(AbstractClientPlayer.class)
public class MixinAbstractClientPlayer extends Player {
    public MixinAbstractClientPlayer(Level arg) {
        super(arg);
    }

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;", ordinal = 0), index = 0)
    private String fixSkinUrl(String orig) {
        return "http://resourceproxy.pymcl.net/skinapi.php?user=";
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void fixSkin(Minecraft minecraft, Level level, Session session, int dimensionId, CallbackInfo info) {
        String uuid = SkinProcessor.getUUIDFromName(this.name);
        if (uuid != null) {
            SkinProcessor.SkinMetadata metadata = SkinProcessor.getMetadataFromUUID(uuid);
            if (metadata != null) {
                this.skinUrl = metadata.url;
                this.cloakUrl = metadata.capeUrl;
            }
        }
        String customCape = SkinProcessor.getCustomCape(this.name);
        if (customCape != null) {
            this.cloakUrl = customCape;
        }
    }

    @Shadow
    public void method_494() {}
}
