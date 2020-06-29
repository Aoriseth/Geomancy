package com.deigon.geomancy.init;

import com.deigon.geomancy.Geomancy;
import com.deigon.geomancy.items.DivinationRodItem;
import com.deigon.geomancy.items.DowsingRodItem;
import com.deigon.geomancy.items.GeomancerTomeItem;
import com.deigon.geomancy.items.StoneGauntletItem;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class GeomancyRegistry {
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Geomancy.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Geomancy.MOD_ID);

    // Register Blocks
    public static final RegistryObject<Block> INSCRIBED_DIRT = BLOCKS.register("inscribed_dirt", () -> new Block(Block.Properties.create(Material.EARTH).hardnessAndResistance(0).sound(SoundType.GROUND)));
    public static final RegistryObject<Block> ESSENCE_SPIKE = BLOCKS.register("essence_spike", () -> new Block(defaultBlockBuilder().hardnessAndResistance(1f).harvestTool(ToolType.PICKAXE).harvestLevel(1)));
    public static final RegistryObject<Block> MAGNETITE_ORE = BLOCKS.register("magnetite_ore", () -> new Block(defaultBlockBuilder().hardnessAndResistance(3f).harvestTool(ToolType.PICKAXE).harvestLevel(1)));

    // Register BlockItems
    public static final RegistryObject<Item> INSCRIBED_DIRT_BLOCKITEM = ITEMS.register("inscribed_dirt", ()-> new BlockItem(INSCRIBED_DIRT.get(), defaultBuilder()));
    public static final RegistryObject<Item> ESSENCE_SPIKE_BLOCKITEM = ITEMS.register("essence_spike", ()-> new BlockItem(ESSENCE_SPIKE.get(), defaultBuilder()));
    public static final RegistryObject<Item> MAGNETITE_ORE_BLOCKITEM = ITEMS.register("magnetite_ore", ()-> new BlockItem(MAGNETITE_ORE.get(), defaultBuilder()));

    // Register Items
    public static final RegistryObject<Item> DIVINATION_ROD = ITEMS.register("divination_rod", ()->new DivinationRodItem(unstackable()));
    public static final RegistryObject<Item> DIRT_BALL = ITEMS.register("dirt_ball", ()->new Item(defaultBuilder().food(new Food.Builder().fastToEat().hunger(1).saturation(0).setAlwaysEdible().build())));
    public static final RegistryObject<Item> STONE_GAUNTLET = ITEMS.register("stone_gauntlet", ()->new StoneGauntletItem(ItemTier.STONE, unstackable().maxDamage(20).setNoRepair()));
    public static final RegistryObject<Item> GEOMANCER_TOME = ITEMS.register("geomancer_tome", ()->new GeomancerTomeItem(unstackable()));
    public static final RegistryObject<Item> MAGNETITE_SHARD = ITEMS.register("magnetite_shard", ()->new Item(defaultBuilder()));
    public static final RegistryObject<Item> DOWSING_ROD = ITEMS.register("dowsing_rod", ()->new DowsingRodItem(unstackable()));

    private static Item.Properties defaultBuilder() {
        return new Item.Properties().group(Geomancy.GeomancyItemGroup.instance);
    }

    private static Item.Properties unstackable() {
        return defaultBuilder().maxStackSize(1);
    }

    private static Block.Properties defaultBlockBuilder() {
        return Block.Properties.create(Material.EARTH);
    }
}
