package com.aoriseth.geomancy.items;

import com.aoriseth.geomancy.Geomancy;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import vazkii.patchouli.api.PatchouliAPI;

public class GeomancerTomeItem extends Item {

    public GeomancerTomeItem() {
        super(new Item.Settings()
        .maxCount(1)
        .group(Geomancy.itemGroup));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user instanceof ServerPlayerEntity){
            ServerPlayerEntity player = (ServerPlayerEntity) user;
            PatchouliAPI.instance.openBookGUI(player, Registry.ITEM.getId(this));
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
