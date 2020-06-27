package com.deigon.geomancy.init;

import com.deigon.geomancy.Geomancy;
import com.deigon.geomancy.blocks.EssenceSpikeBlock;
import com.deigon.geomancy.blocks.MagnetiteOreBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
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
    public static final Block essence_spike = null;
    public static final Block magnetite_ore = null;

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event){
        IForgeRegistry<Block> r = event.getRegistry();

        Block.Properties dirtBuilder = Block.Properties.create(Material.EARTH).hardnessAndResistance(0).sound(SoundType.GROUND);

        register(r, "inscribed_dirt", new Block(dirtBuilder));
        register(r, "essence_spike", new EssenceSpikeBlock(defaultBlockBuilder().hardnessAndResistance(0.8f).harvestTool(ToolType.PICKAXE).harvestLevel(1)));
        register(r, "magnetite_ore", new MagnetiteOreBlock(defaultBlockBuilder().hardnessAndResistance(3f).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    }

    @SubscribeEvent
    public static void registerBlockItems(final RegistryEvent.Register<Item> event){
        IForgeRegistry<Item> r = event.getRegistry();

        register(r, "inscribed_dirt", new BlockItem(inscribed_dirt, defaultBuilder()));
        register(r, "essence_spike", new BlockItem(essence_spike, unstackable()));
        register(r, "magnetite_ore", new BlockItem(magnetite_ore, defaultBuilder()));
    }

    private static Block.Properties defaultBlockBuilder() {
        return Block.Properties.create(Material.EARTH);
    }

    private static Item.Properties defaultBuilder() {
        return new Item.Properties().group(Geomancy.GeomancyItemGroup.instance);
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
