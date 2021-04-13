package pm.c7.clfpatcher.skins;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import pm.c7.clfpatcher.CLFPatcher;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class SkinProcessor {
    private static Map<String, String> uuidCache = new HashMap<>();
    private static Map<String, SkinMetadata> metadataCache = new HashMap<>();
    private static Map<String, Boolean> optifineCache = new HashMap<>();

    public static String getUUIDFromName(String name) {
        if (uuidCache.containsKey(name)) {
            return uuidCache.get(name);
        }

        try {
            HttpURLConnection connection = (HttpURLConnection) (new URL("https://api.mojang.com/users/profiles/minecraft/" + name)).openConnection();
            connection.connect();
            InputStream response = connection.getInputStream();
            Reader reader = new InputStreamReader(response);

            Profile profile = new Gson().fromJson(reader, Profile.class);
            connection.disconnect();

            uuidCache.put(name, profile.id);

            return profile.id;
        } catch (Exception err) {
            CLFPatcher.log("Failed to fetch UUID for %s:", name);
            err.printStackTrace();
        }

        return null;
    }

    public static SkinMetadata getMetadataFromUUID(String uuid) {
        if (metadataCache.containsKey(uuid)) {
            return metadataCache.get(uuid);
        }

        try {
            HttpURLConnection connection = (HttpURLConnection) (new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid)).openConnection();
            connection.connect();
            InputStream response = connection.getInputStream();
            Reader reader = new InputStreamReader(response);

            SessionProfile profile = new Gson().fromJson(reader, SessionProfile.class);
            connection.disconnect();

            JsonObject propsJson = profile.properties.get(0).getAsJsonObject();
            String propsDecoded = new String(Base64.getDecoder().decode(propsJson.get("value").getAsString()));

            SkinProperties properties = new Gson().fromJson(propsDecoded, SkinProperties.class);
            JsonObject skinJson = properties.textures.get("SKIN").getAsJsonObject();
            JsonElement cape = properties.textures.get("CAPE");
            JsonObject capeJson = cape != null ? cape.getAsJsonObject() : null;

            SkinMetadata metadata = new SkinMetadata(skinJson.get("url").getAsString(), capeJson != null ? capeJson.get("url").getAsString() : null, skinJson.get("metadata").getAsJsonObject().get("model").getAsString());

            metadataCache.put(uuid, metadata);

            return metadata;
        } catch (Exception err) {
            CLFPatcher.log("Failed to fetch metadata for %s:", uuid);
            err.printStackTrace();
        }

        return null;
    }

    public static String getCustomCape(String name) {
        String capeUrl = CLFPatcher.config.getStringValue("capeUrl");
        if (capeUrl == null)
            capeUrl = "http://s.optifine.net/capes/";

        String fullUrl = capeUrl + name + ".png";

        if (optifineCache.containsKey(name)) {
            return fullUrl;
        }

        try {
            HttpURLConnection connection = (HttpURLConnection) (new URL(fullUrl)).openConnection();
            connection.connect();
            if (connection.getResponseCode() != 200) {
                CLFPatcher.log("failed to get cape for %s: %d", name, connection.getResponseCode());
                optifineCache.put(name, false);
            }
            connection.disconnect();

            optifineCache.put(name, true);

            return fullUrl;
        } catch (Exception err) {
            err.printStackTrace();
            optifineCache.put(name, false);
        }

        return null;
    }

    private class Profile {
        public String id;
        public String name;
    }

    private class SessionProfile {
        public String id;
        public String name;
        public JsonArray properties;
    }

    private class SkinProperties {
        public String timestamp;
        public String profileId;
        public String profileName;
        public JsonObject textures;
    }

    public static class SkinMetadata {
        public String url;
        public String capeUrl;
        public ModelType model = ModelType.NORMAL;

        SkinMetadata(String url, String capeUrl, String model) {
            this.url = url;
            this.capeUrl = capeUrl;
            if (model.equals("slim")) {
                this.model = ModelType.SLIM;
            }
        };
    }

    public enum ModelType {
        NORMAL,
        SLIM
    }
}
