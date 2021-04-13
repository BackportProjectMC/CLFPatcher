package pm.c7.clfpatcher.skins;

public class SizedTexturedQuad {
    public static int WIDTH;
    public static int HEIGHT;

    public static void setDefault() {
        WIDTH = 64;
        HEIGHT = 32;
    }

    static {
        setDefault();
    }
}
