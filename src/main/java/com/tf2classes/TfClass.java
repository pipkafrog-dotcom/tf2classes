package com.tf2classes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.List;

/**
 * Классы TF2. Здоровье = здоровье TF2 / 7.5 (Хевви 300 -> 40 HP, Скаут 125 -> 16 HP).
 * Скорость = множитель от обычной скорости ходьбы Minecraft.
 * Снаряжение пока заглушки из ванильных предметов.
 */
public enum TfClass {
    SCOUT("Скаут", 16, 1.33),
    SOLDIER("Солдат", 26, 0.80),
    PYRO("Поджигатель", 23, 1.00),
    DEMOMAN("Подрывник", 23, 0.93),
    HEAVY("Хевви", 40, 0.77),
    ENGINEER("Инженер", 16, 1.00),
    MEDIC("Медик", 16, 1.07),
    SNIPER("Снайпер", 16, 1.00),
    SPY("Шпион", 16, 1.07);

    public final String displayName;
    public final double health;
    public final double speed;

    TfClass(String displayName, double health, double speed) {
        this.displayName = displayName;
        this.health = health;
        this.speed = speed;
    }

    public List<ItemStack> loadout() {
        return switch (this) {
            case SCOUT -> List.of(new ItemStack(Items.WOODEN_SWORD), new ItemStack(Items.CROSSBOW), new ItemStack(Items.ARROW, 16));
            case SOLDIER -> List.of(new ItemStack(Items.IRON_SHOVEL), new ItemStack(Items.CROSSBOW), new ItemStack(Items.ARROW, 16));
            case PYRO -> List.of(new ItemStack(Items.GOLDEN_AXE), new ItemStack(Items.FLINT_AND_STEEL));
            case DEMOMAN -> List.of(new ItemStack(Items.STONE_SWORD), new ItemStack(Items.TNT, 4), new ItemStack(Items.FLINT_AND_STEEL));
            case HEAVY -> List.of(new ItemStack(Items.IRON_AXE), new ItemStack(Items.BREAD, 16));
            case ENGINEER -> List.of(new ItemStack(Items.IRON_PICKAXE), new ItemStack(Items.IRON_INGOT, 32));
            case MEDIC -> List.of(new ItemStack(Items.STONE_SWORD), new ItemStack(Items.GOLDEN_APPLE, 4));
            case SNIPER -> List.of(new ItemStack(Items.BOW), new ItemStack(Items.ARROW, 64), new ItemStack(Items.STONE_SWORD));
            case SPY -> List.of(new ItemStack(Items.IRON_SWORD), new ItemStack(Items.ENDER_PEARL, 4));
        };
    }
}
