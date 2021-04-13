package pm.c7.clfpatcher.mixin.skins;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pm.c7.clfpatcher.skins.PlayerRendererAccessor;
import pm.c7.clfpatcher.skins.SkinProcessor;

import java.util.Map;

@Mixin(EntityRenderDispatcher.class)
public class MixinEntityRenderDispatcher {
    @Shadow
    private Map renderers;

    private PlayerRenderer NORMAL;
    private PlayerRenderer SLIM;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void addNewPlayerRenderers(CallbackInfo info) {
        this.NORMAL = (PlayerRenderer)this.renderers.get(Player.class);
        ((PlayerRendererAccessor)this.NORMAL).setSlim(false);
        this.SLIM = new PlayerRenderer();
        ((PlayerRendererAccessor)this.SLIM).setSlim(true);
        this.SLIM.setDispatcher((EntityRenderDispatcher)(Object)this);
    }

    @Inject(method = "get(Lnet/minecraft/entity/Entity;)Lnet/minecraft/client/render/entity/EntityRenderer;", at = @At("HEAD"), cancellable = true)
    private void redirectGet(Entity entity, CallbackInfoReturnable<EntityRenderer> info) {
        if (entity instanceof Player) {
            Player player = (Player)entity;
            String uuid = SkinProcessor.getUUIDFromName(player.name);
            if (uuid != null) {
                SkinProcessor.SkinMetadata metadata = SkinProcessor.getMetadataFromUUID(uuid);
                if (metadata != null) {
                    info.setReturnValue(metadata.model == SkinProcessor.ModelType.SLIM ? this.SLIM : this.NORMAL);
                }
            }
        }
    }
}
