package com.aoriseth.geomancy;

import com.aoriseth.geomancy.registry.GeomancyBlockEntities;
import com.aoriseth.geomancy.registry.GeomancyBlocks;
import com.aoriseth.geomancy.registry.GeomancyEffects;
import com.aoriseth.geomancy.registry.GeomancyItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import static com.aoriseth.geomancy.registry.GeomancyItems.DOWSING_ROD;

public class Geomancy implements ModInitializer {
	public static final ItemGroup itemGroup = FabricItemGroupBuilder.build(new Identifier("geomancy", "general"), () -> new ItemStack(DOWSING_ROD));
	public static final String MOD_ID = "geomancy";

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		System.out.println("Hello Fabric world!");

		GeomancyItems.init();
		GeomancyBlocks.init();
		GeomancyEffects.init();
		GeomancyBlockEntities.init();
	}
}
