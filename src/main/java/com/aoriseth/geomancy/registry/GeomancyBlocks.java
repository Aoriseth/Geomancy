package com.aoriseth.geomancy.registry;

import com.aoriseth.geomancy.blocks.BurrowTunnelBlock;
import com.aoriseth.geomancy.blocks.SandDivinationBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GeomancyBlocks {

    public final static Block INSCRIBED_DIRT = register("inscribed_dirt", new Block(getDefaultSettings()));
    public final static Block ESSENCE_SPIKE = register("essence_spike", new Block(getDefaultSettings()));
    public final static Block MAGNETITE_ORE = register("magnetite_ore", new Block(FabricBlockSettings.of(Material.METAL).strength(3f).breakByTool(FabricToolTags.PICKAXES, MiningLevel.STONE.getLevel()).requiresTool()));
    public final static Block SUN_STONE = register("sun_stone", new Block(getDefaultSettings()));
    public final static Block DIVINATION_SAND = register("divination_sand", new SandDivinationBlock());
    public final static Block BURROW_TUNNEL_BLOCK = register("burrow_tunnel", new BurrowTunnelBlock());

    public static Block register(String name, Block block) {
        GeomancyItems.register(name, new BlockItem(block, GeomancyItems.getDefaultSettings()));
        return Registry.register(Registry.BLOCK, new Identifier("geomancy", name), block);
    }

    private static FabricBlockSettings getDefaultSettings() {
        return FabricBlockSettings.of(Material.STONE);
    }

    public static void init() {
        // Initialize registry
    }
}
