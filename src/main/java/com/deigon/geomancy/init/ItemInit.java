package com.deigon.geomancy.init;

import com.deigon.geomancy.Geomancy;
import com.deigon.geomancy.items.DivinationRodItem;
import com.deigon.geomancy.items.GeomancerTomeItem;
import com.deigon.geomancy.items.StoneGauntletItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
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
    public static final Item stone_gauntlet = null;

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event){
        IForgeRegistry<Item> r = event.getRegistry();

        register(r, "divination_rod", new DivinationRodItem(unstackable()));
        register(r, "dirt_ball", new Item(defaultBuilder().food(new Food.Builder().fastToEat().hunger(1).saturation(1f).setAlwaysEdible().build())));
        register(r, "stone_gauntlet", new StoneGauntletItem(ItemTier.STONE, unstackable().maxDamage(20).setNoRepair()));
        register(r, "geomancer_tome", new GeomancerTomeItem(unstackable()));
    }

    public static Item.Properties defaultBuilder() {
        return new Item.Properties().group(Geomancy.GeomancyItemGroup.instance);
    }

    private static Item.Properties unstackable() {
        return defaultBuilder().maxStackSize(1);
    }
}
