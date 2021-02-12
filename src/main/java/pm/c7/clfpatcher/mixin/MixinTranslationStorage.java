package pm.c7.clfpatcher.mixin;

import net.minecraft.client.resource.language.TranslationStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pm.c7.clfpatcher.CLFPatcher;

import java.io.IOException;
import java.util.Properties;

@Mixin(TranslationStorage.class)
public class MixinTranslationStorage {
    @Shadow
    private Properties translations = new Properties();

    @Inject(method = "<init>", at = @At("RETURN"))
    private void addOurLangStrings(CallbackInfo info) {
        try {
            this.translations.load(CLFPatcher.class.getResourceAsStream("/assets/clfpatcher/lang/en_US.lang"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
