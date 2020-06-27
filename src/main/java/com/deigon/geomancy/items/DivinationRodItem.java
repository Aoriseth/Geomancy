package com.deigon.geomancy.items;

import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class DivinationRodItem extends Item {
    public DivinationRodItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        if (world.getBlockState(context.getPos()) == Blocks.DIRT.getDefaultState()){
            world.playSound(context.getPlayer(), context.getPos(), SoundEvents.BLOCK_GRAVEL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!world.isRemote){
                world.setBlockState(context.getPos(), Blocks.SAND.getDefaultState());
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }
}
