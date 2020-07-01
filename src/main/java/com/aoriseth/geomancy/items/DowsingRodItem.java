package com.aoriseth.geomancy.items;

import com.aoriseth.geomancy.Geomancy;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.StructureFeature;

public class DowsingRodItem extends Item {

    public DowsingRodItem() {
        super(new Item.Settings()
        .group(Geomancy.itemGroup)
        .maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient){
            BlockPos structurePos = world.getServer().getWorld(World.OVERWORLD).locateStructure(StructureFeature.BURIED_TREASURE, user.getBlockPos(), 250, false);
            if (structurePos == null){
                user.sendMessage(new LiteralText("Failed to find treasure"), true);
                return TypedActionResult.success(user.getStackInHand(hand));
            }

            BlockPos userPos = user.getBlockPos();

            double distance = userPos.getManhattanDistance(structurePos);

            user.sendMessage(new LiteralText("Distance to treasure: " + distance + " Blocks"), true);
            return TypedActionResult.success(user.getStackInHand(hand));
        }

        return TypedActionResult.fail(user.getStackInHand(hand));
    }
}
