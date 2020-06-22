package com.deigon.geomancy.init;

import com.deigon.geomancy.Geomancy;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Geomancy.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Geomancy.MOD_ID)
public class ItemInit {

    public static final Item divination_rod = null;

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event){
        event.getRegistry()
                .register(new Item(new Item.Properties().group(ItemGroup.MISC)).setRegistryName("divination_rod"));
    }
}
