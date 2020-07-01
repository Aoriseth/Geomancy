package com.aoriseth.geomancy.registry;

import com.aoriseth.geomancy.Geomancy;
import com.aoriseth.geomancy.items.GeomancerTomeItem;
import com.aoriseth.geomancy.items.StoneGauntletItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GeomancyItems {

    public static final Item DOWSING_ROD = register("dowsing_rod", new Item(getUnstackable()));
    public static final Item DIVINATION_ROD = register("divination_rod", new Item(getDefaultSettings()));
    public static final Item DIRT_BALL = register("dirt_ball", new Item(getDefaultSettings()));
    public static final Item STONE_GAUNTLET = register("stone_gauntlet", new StoneGauntletItem());
    public static final Item GEOMANCER_TOME = register("geomancer_tome", new GeomancerTomeItem());
    public static final Item MAGNETITE_SHARD = register("magnetite_shard", new Item(getDefaultSettings()));


    public static Item register(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier("geomancy", name), item);
    }

    static Item.Settings getDefaultSettings() {
        return new Item.Settings().group(Geomancy.itemGroup);
    }

    private static Item.Settings getUnstackable(){
        return getDefaultSettings().maxCount(1);
    }

    public static void init() {
        // Initialize registry
    }
}
