package pm.c7.clfpatcher;

import io.github.minecraftcursedlegacy.api.config.Configs;
import io.github.minecraftcursedlegacy.api.registry.Id;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tk.valoeghese.zoesteriaconfig.api.ZoesteriaConfig;
import tk.valoeghese.zoesteriaconfig.api.container.WritableConfig;
import tk.valoeghese.zoesteriaconfig.api.template.ConfigTemplate;

import java.io.File;
import java.io.IOException;

public class CLFPatcher implements ClientModInitializer {
    public static WritableConfig config;

    public static void log(String fmt, Object ... values) {
        System.out.print("[CLFPatcher] ");
        System.out.printf(fmt, values);
        System.out.println();
    }

    public static void log(Object ... values) {
        System.out.print("[CLFPatcher] ");
        for (int i = 0; i < values.length; i++) {
            System.out.print(values[i]);
            if (i != values.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    @Override
    public void onInitializeClient() {
        log("Init");

        try {
            File configFile = new File(FabricLoader.getInstance().getConfigDirectory(), "clfpatcher.cfg");
            boolean createNew = configFile.createNewFile();
            config = ZoesteriaConfig.loadConfigWithDefaults(configFile, ConfigTemplate.builder()
                    .addDataEntry("fov", "70")
                    .build());
            if (createNew) {
                config.writeToFile(configFile);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeConfig() {
        File configFile = new File(FabricLoader.getInstance().getConfigDirectory(), "clfpatcher.cfg");
        config.writeToFile(configFile);
    }
}
