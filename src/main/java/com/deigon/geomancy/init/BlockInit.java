package com.deigon.geomancy.init;

import com.deigon.geomancy.Geomancy;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
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
public class BlockInit {

    public static final Block inscribed_dirt = null;

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event){
        IForgeRegistry<Block> r = event.getRegistry();

        Block.Properties dirtBuilder = Block.Properties.create(Material.EARTH).hardnessAndResistance(0).sound(SoundType.GROUND);

        register(r, "inscribed_dirt", new Block(dirtBuilder));
    }

    @SubscribeEvent
    public static void registerBlockItems(final RegistryEvent.Register<Item> event){
        IForgeRegistry<Item> r = event.getRegistry();

        register(r, "inscribed_dirt", new BlockItem(inscribed_dirt, defaultBuilder()));
    }


    private static Item.Properties defaultBuilder() {
        return new Item.Properties().group(ItemGroup.MISC);
    }

    private static Item.Properties unstackable() {
        return defaultBuilder().maxStackSize(1);
    }

    public static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> reg, ResourceLocation name, IForgeRegistryEntry<V> thing) {
        reg.register(thing.setRegistryName(name));
    }

    public static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> reg, String name, IForgeRegistryEntry<V> thing) {
        register(reg, new ResourceLocation(Geomancy.MOD_ID, name), thing);
    }
}
