package com.deigon.geomancy.init;

import com.deigon.geomancy.Geomancy;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static com.deigon.geomancy.init.BlockInit.register;

@Mod.EventBusSubscriber(modid = Geomancy.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Geomancy.MOD_ID)
public class ItemInit {

    public static final Item divination_rod = null;
    public static final Item dirt_ball = null;

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event){
        IForgeRegistry<Item> r = event.getRegistry();

        register(r, "divination_rod", new Item(unstackable()));
        register(r, "dirt_ball", new Item(defaultBuilder().food(new Food.Builder().fastToEat().hunger(1).saturation(1f).setAlwaysEdible().build())));
    }

    public static Item.Properties defaultBuilder() {
        return new Item.Properties().group(Geomancy.GeomancyItemGroup.instance);
    }

    private static Item.Properties unstackable() {
        return defaultBuilder().maxStackSize(1);
    }
}
