package com.deigon.geomancy.init;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.TieredItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StoneGauntletItem extends TieredItem {

    public StoneGauntletItem(IItemTier tierIn, Properties builder) {
        super(tierIn, builder);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        ItemStack item = context.getItem();

        if (item.getDamage()+1 < item.getMaxDamage()){
            if (world.getBlockState(context.getPos()) == Blocks.DIRT.getDefaultState()){
                world.playSound(context.getPlayer(), context.getPos(), SoundEvents.BLOCK_GRAVEL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);

                if (!world.isRemote){
                    item.damageItem(1, context.getPlayer(), (playerEntity)->{});
                    world.setBlockState(context.getPos(), Blocks.SAND.getDefaultState());
                }
                return ActionResultType.SUCCESS;
            }
        } else {
            world.playSound(context.getPlayer(), context.getPos(), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.PLAYERS, 1f, 1f);
        }
        return ActionResultType.FAIL;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        return true;
    }


}
