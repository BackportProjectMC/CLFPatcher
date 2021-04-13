package pm.c7.clfpatcher.mixin;

import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.screen.ControlsScreen;
import net.minecraft.client.gui.screen.OptionsScreen;
import net.minecraft.client.gui.screen.TexturePackScreen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.gui.widgets.Slider;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
import net.minecraft.client.resource.language.TranslationStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pm.c7.clfpatcher.CLFPatcher;
import pm.c7.clfpatcher.gui.FOVSlider;

@Mixin(OptionsScreen.class)
public class MixinOptionsScreen extends Screen {
    @Shadow private GameOptions gameOptions;

    @Inject(method = "init", at = @At("RETURN"))
    private void addFOVSlider(CallbackInfo info) {
        TranslationStorage translator = TranslationStorage.getInstance();

        this.buttons.add(new FOVSlider(40, this.width / 2 - 155 + 160, this.height / 6 + 24 * (5 >> 1), CLFPatcher.config.getFloatValue("fov")));
        Button texturePacks = new Button(102, this.width / 2 - 155, this.height / 6 + 120 + 12, translator.translate("menu.mods"));
        ((ButtonAccessor)texturePacks).setWidth(150);
        this.buttons.add(texturePacks);

        for (Object obj : this.buttons.toArray()) {
            Button button = (Button)obj;
            if (button.id == 101) {
                button.x = this.width / 2 - 155;
                button.y = this.height / 6 + 96 + 12;
                ((ButtonAccessor)button).setWidth(150);
            } else if (button.id == 100) {
                button.x = this.width / 2 - 155 + 160;
                button.y = this.height / 6 + 96 + 12;
                ((ButtonAccessor)button).setWidth(150);
            }
        }
    }

    @Inject(method = "buttonClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/options/GameOptions;saveOptions()V"))
    private void saveConfigOnLeave(CallbackInfo info) {
        CLFPatcher.writeConfig();
    }

    @Inject(method = "buttonClicked", at = @At("RETURN"))
    private void customClickActions(Button button, CallbackInfo info) {
        if (button.active) {
            if (button.id == 102) {
                this.minecraft.options.saveOptions();
                this.minecraft.openScreen(new TexturePackScreen(this));
            }
        }
    }
}
