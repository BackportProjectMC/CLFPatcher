package pm.c7.clfpatcher.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.resource.language.TranslationStorage;
import org.lwjgl.opengl.GL11;
import pm.c7.clfpatcher.CLFPatcher;

public class FOVSlider extends Button {
    public float value = 1.0F;
    public boolean dragged = false;

    public FOVSlider(int id, int x, int y, float value) {
        super(id, x, y, 150, 20, "");
        this.value = (value - 30.0F) / (110.0F - 30.0F);


        float readableValue = 30.0F + this.value * (110.0F - 30.0F);

        TranslationStorage translator = TranslationStorage.getInstance();
        String prefix = translator.translate("options.fov") + ": ";

        if (Math.round(readableValue) == 70) {
            this.text = prefix + translator.translate("options.fov.min");
        } else if(Math.round(readableValue) == 110) {
            this.text = prefix + translator.translate("options.fov.max");
        } else {
            this.text = prefix + Math.round(readableValue);
        }
    }

    protected int getYImage(boolean hovered) {
        return 0;
    }

    protected void postRender(Minecraft minecraft, int mouseX, int mouseY) {
        if (this.visible) {
            if (this.dragged) {
                this.value = (float)(mouseX - (this.x + 4)) / (float)(this.width - 8);

                if (this.value < 0.0F) {
                    this.value = 0.0F;
                }

                if (this.value > 1.0F) {
                    this.value = 1.0F;
                }

                float readableValue = 30.0F + this.value * (110.0F - 30.0F);

                CLFPatcher.config.putIntegerValue("fov", Math.round(readableValue));

                TranslationStorage translator = TranslationStorage.getInstance();
                String prefix = translator.translate("options.fov") + ": ";

                if (Math.round(readableValue) == 70) {
                    this.text = prefix + translator.translate("options.fov.min");
                } else if(Math.round(readableValue) == 110) {
                    this.text = prefix + translator.translate("options.fov.max");
                } else {
                    this.text = prefix + Math.round(readableValue);
                }
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.blit(this.x + (int)(this.value * (float)(this.width - 8)), this.y, 0, 66, 4, 20);
            this.blit(this.x + (int)(this.value * (float)(this.width - 8)) + 4, this.y, 196, 66, 4, 20);
        }
    }

    public boolean isMouseOver(Minecraft minecraft, int mouseX, int mouseY) {
        if (super.isMouseOver(minecraft, mouseX, mouseY)) {
            this.value = (float)(mouseX - (this.x + 4)) / (float)(this.width - 8);
            if (this.value < 0.0F) {
                this.value = 0.0F;
            }

            if (this.value > 1.0F) {
                this.value = 1.0F;
            }

            float readableValue = 30.0F + this.value * (110.0F - 30.0F);

            CLFPatcher.config.putIntegerValue("fov", Math.round(readableValue));

            TranslationStorage translator = TranslationStorage.getInstance();
            String prefix = translator.translate("options.fov") + ": ";

            if (Math.round(readableValue) == 70) {
                this.text = prefix + translator.translate("options.fov.min");
            } else if(Math.round(readableValue) == 110) {
                this.text = prefix + translator.translate("options.fov.max");
            } else {
                this.text = prefix + Math.round(readableValue);
            }
            this.dragged = true;
            return true;
        } else {
            return false;
        }
    }

    public void mouseReleased(int mouseX, int mouseY) {
        this.dragged = false;
    }
}
