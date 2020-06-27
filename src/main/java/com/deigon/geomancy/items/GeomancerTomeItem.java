package com.deigon.geomancy.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.patchouli.api.PatchouliAPI;

import static java.util.Objects.requireNonNull;

public class GeomancerTomeItem extends Item {
    public GeomancerTomeItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        if (playerIn instanceof ServerPlayerEntity){
            ServerPlayerEntity player = (ServerPlayerEntity) playerIn;
            PatchouliAPI.instance.openBookGUI(player, requireNonNull(ForgeRegistries.ITEMS.getKey(this)));
        }
        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }
}
