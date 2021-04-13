package pm.c7.clfpatcher.mixin.skins;

import net.minecraft.class_290;
import net.minecraft.class_552;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import pm.c7.clfpatcher.skins.ModelPartAccessor;
import pm.c7.clfpatcher.skins.SizedTexturedQuad;

@Mixin(ModelPart.class)
public abstract class MixinModelPart implements ModelPartAccessor {
    private int textureWidth = 64;
    private int textureHeight = 32;

    @Redirect(method = "addCuboid(FFFIIIF)V", at = @At(value = "NEW", target = "net/minecraft/class_552"))
    private class_552 fixQuad(class_290[] args, int i, int j, int k, int i1) {
        SizedTexturedQuad.WIDTH = getTextureWidth();
        SizedTexturedQuad.HEIGHT = getTextureHeight();
        class_552 newQuad = new class_552(args, i, j, k, i1);
        SizedTexturedQuad.setDefault();

        return newQuad;
    }

    public int getTextureWidth() {
        return this.textureWidth;
    }

    public void setTextureWidth(int textureWidth) {
        this.textureWidth = textureWidth;
    }

    public int getTextureHeight() {
        return this.textureHeight;
    }

    public void setTextureHeight(int textureHeight) {
        this.textureHeight = textureHeight;
    }
}
