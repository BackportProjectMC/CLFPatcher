package pm.c7.clfpatcher.mixin.skins;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import pm.c7.clfpatcher.skins.ModelPartAccessor;
import pm.c7.clfpatcher.skins.PlayerModel;

@Mixin(BipedModel.class)
public class MixinBipedModel {
    @Redirect(method = {"<init>(FF)V"}, at = @At(value = "NEW", target = "net/minecraft/client/model/ModelPart"), slice = @Slice(from = @At(value = "NEW", target = "net/minecraft/client/model/ModelPart", shift = At.Shift.AFTER)))
    private ModelPart onTexturedQuad(int x, int y) {
        ModelPart modelRenderer = new ModelPart(x, y);
        if ((BipedModel)(Object)this instanceof PlayerModel)
            ((ModelPartAccessor)modelRenderer).setTextureHeight(64);
        return modelRenderer;
    }
}
