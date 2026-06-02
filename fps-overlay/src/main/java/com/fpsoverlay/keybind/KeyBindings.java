package com.fpsoverlay.keybind;

import com.fpsoverlay.gui.OverlaySettingsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {

    public static KeyBinding OPEN_SETTINGS;

    public static void register() {
        OPEN_SETTINGS = new KeyBinding(
                "key.fpsoverlay.open_settings",
                InputMappings.Type.KEYSYM,
                GLFW.GLFW_KEY_F9,
                "key.categories.fpsoverlay"
        );
        ClientRegistry.registerKeyBinding(OPEN_SETTINGS);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (OPEN_SETTINGS != null && OPEN_SETTINGS.consumeClick()) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.screen == null) {
                mc.setScreen(new OverlaySettingsScreen());
            }
        }
    }
}
