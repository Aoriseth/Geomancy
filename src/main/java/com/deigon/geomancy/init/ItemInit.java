package com.deigon.geomancy.init;

import com.deigon.geomancy.Geomancy;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Geomancy.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Geomancy.MOD_ID)
public class ItemInit {

    public static final Item divination_rod = null;

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event){
        IForgeRegistry<Item> r = event.getRegistry();
        register(r, new Item(unstackable()), "divination_rod");
    }

    private static Item.Properties defaultBuilder() {
        return new Item.Properties().group(ItemGroup.MISC);
    }

    private static Item.Properties unstackable() {
        return defaultBuilder().maxStackSize(1);
    }


    public static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> reg, IForgeRegistryEntry<V> thing, ResourceLocation name) {
        reg.register(thing.setRegistryName(name));
    }

    public static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> reg, IForgeRegistryEntry<V> thing, String name) {
        register(reg, thing, new ResourceLocation(Geomancy.MOD_ID, name));
    }
}
