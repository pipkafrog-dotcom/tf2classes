package com.tf2classes;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Tf2Classes implements ModInitializer {
    public static final String MOD_ID = "tf2classes";
    public static final Identifier SET_CLASS = new Identifier(MOD_ID, "set_class");

    // Выбранный класс каждого игрока. Пока хранится только до перезапуска сервера.
    private static final Map<UUID, TfClass> CLASSES = new HashMap<>();

    @Override
    public void onInitialize() {
        ServerPlayNetworking.registerGlobalReceiver(SET_CLASS, (server, player, handler, buf, sender) -> {
            int id = buf.readInt();
            server.execute(() -> {
                TfClass[] all = TfClass.values();
                if (id < 0 || id >= all.length) return;
                CLASSES.put(player.getUuid(), all[id]);
                apply(player, all[id]);
            });
        });

        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            TfClass c = CLASSES.get(newPlayer.getUuid());
            if (c != null) apply(newPlayer, c);
        });
    }

    private static void apply(ServerPlayerEntity player, TfClass c) {
        player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(c.health);
        player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1 * c.speed);
        player.setHealth(player.getMaxHealth());
        player.getInventory().clear();
        for (ItemStack stack : c.loadout()) {
            player.getInventory().offerOrDrop(stack);
        }
        player.sendMessage(Text.literal("Класс: " + c.displayName), true);
    }
}
