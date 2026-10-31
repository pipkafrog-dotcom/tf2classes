package com.tf2classes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Tf2ClassesClient implements ClientModInitializer {
    private static KeyBinding openMenu;

    @Override
    public void onInitializeClient() {
        // Клавиша запятая = "Б" на русской раскладке. Меняется в настройках управления.
        openMenu = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.tf2classes.menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_COMMA, "key.categories.tf2classes"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenu.wasPressed()) {
                if (client.player != null && client.currentScreen == null) {
                    client.setScreen(new ClassMenuScreen());
                }
            }
        });
    }
}
