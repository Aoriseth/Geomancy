package com.aoriseth.geomancy;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Geomancy implements ModInitializer {
	public static final Item DOWSING_ROD = new Item(defaultConstructor());

	public static final ItemGroup creativeTab = FabricItemGroupBuilder.build(new Identifier("geomancy", "geomancy"), () -> new ItemStack(DOWSING_ROD));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		Registry.register(Registry.ITEM, new Identifier("geomancy", "dowsing_rod"), DOWSING_ROD);
		System.out.println("Hello Fabric world!");
	}

	private static Item.Settings defaultConstructor(){
		return new Item.Settings().group(ItemGroup.MISC);
	}
}
