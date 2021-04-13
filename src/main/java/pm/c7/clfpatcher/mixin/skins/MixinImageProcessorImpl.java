package pm.c7.clfpatcher.mixin.skins;

import net.minecraft.client.ImageProcessorImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

@Mixin(ImageProcessorImpl.class)
public class MixinImageProcessorImpl {
    @ModifyConstant(method = "process", constant = @Constant(intValue = 32, ordinal = 0))
    private int fixHeight(int orig) {
        return 64;
    }

    @Inject(method = "process", at = @At(value = "INVOKE", target = "Ljava/awt/Graphics;dispose()V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void fixUpSkin(BufferedImage image, CallbackInfoReturnable<BufferedImage> info, BufferedImage parsed, Graphics graphic) {
        if (graphic instanceof Graphics2D) {
            Graphics2D newGraphic = (Graphics2D) graphic;
            if (image.getHeight() == 32) {
                newGraphic.drawImage(image.getSubimage(0, 16, 16, 16), 16, 48, 16, 16, null);
                newGraphic.drawImage(image.getSubimage(40, 16, 16, 16), 32, 48, 16, 16, null);
            }

            int i;
            for (i = 0; i < 2; i++) {
                flipArea(newGraphic, parsed, 16 + i * 32, 0, 8, 8);
            }
            for (i = 1; i <= 2; i++) {
                flipArea(newGraphic, parsed, 8, i * 16, 4, 4);
                flipArea(newGraphic, parsed, 28, i * 16, 8, 4);
                flipArea(newGraphic, parsed, 48, i * 16, 4, 4);
            }
            for (i = 0; i < 4; i++) {
                flipArea(newGraphic, parsed, 8 + i * 16, 48, 4, 4);
            }
        }
    }

    public BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    private void flipArea(Graphics2D graphics, BufferedImage bufferedImage, int x, int y, int width, int height) {
        BufferedImage image = deepCopy(bufferedImage.getSubimage(x, y, width, height));
        graphics.setBackground(new Color(0, true));
        graphics.clearRect(x, y, width, height);
        graphics.drawImage(image, x, y + height, width, -height, null);
    }
}
