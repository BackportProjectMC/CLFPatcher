package pm.c7.clfpatcher.mixin.skins;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.entity.model.BipedModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pm.c7.clfpatcher.skins.PlayerModel;
import pm.c7.clfpatcher.skins.PlayerRendererAccessor;

@Mixin(PlayerRenderer.class)
public class MixinPlayerRenderer extends LivingEntityRenderer implements PlayerRendererAccessor {
    public MixinPlayerRenderer(EntityModel arg, float f) {
        super(arg, f);
    }

    @Shadow
    private BipedModel field_294;

    public void setSlim(boolean slim) {
        this.field_909 = new PlayerModel(0.0F, slim);
        this.field_294 = (BipedModel)this.field_909;
    }

    @Inject(method = "method_345", at = @At("RETURN"))
    private void drawSleeveOnHand(CallbackInfo info) {
        if (this.field_909 instanceof PlayerModel) {
            ((PlayerModel)this.field_909).drawFirstPersonHand();
        }
    }

    @Shadow
    public void render(Entity entity, double x, double y, double z, float f, float f1) {

    }
}
