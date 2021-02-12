package pm.c7.clfpatcher.mixin;

import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.screen.OptionsScreen;
import net.minecraft.client.gui.widgets.Slider;
import net.minecraft.client.options.Option;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pm.c7.clfpatcher.CLFPatcher;
import pm.c7.clfpatcher.gui.FOVSlider;

@Mixin(OptionsScreen.class)
public class MixinOptionsScreen extends Screen {
    @Inject(method = "init", at = @At("RETURN"))
    private void addFOVSlider(CallbackInfo info) {
        this.buttons.add(new FOVSlider(40, this.width / 2 - 155 + 160, this.height / 6 + 24 * (5 >> 1), CLFPatcher.config.getFloatValue("fov")));
    }

    @Inject(method = "buttonClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/options/GameOptions;saveOptions()V"))
    private void saveConfigOnLeave(CallbackInfo info) {
        CLFPatcher.writeConfig();
    }
}
