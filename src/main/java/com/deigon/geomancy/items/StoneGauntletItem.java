package com.deigon.geomancy.items;

import com.deigon.geomancy.blocks.EssenceSpikeBlock;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.brigadier.LiteralMessage;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.TieredItem;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.PistonType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StoneGauntletItem extends TieredItem {

    enum GauntletActions{
        TRANSFORM,
        RAISE,
        LAUNCH,
        INSPECT
    }
    private int currentMode = 0;

    public StoneGauntletItem(IItemTier tierIn, Properties builder) {
        super(tierIn, builder);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        ItemStack item = context.getItem();

        if (world.getBlockState(context.getPos()).getBlock() instanceof EssenceSpikeBlock){
            if (!world.isRemote){
                item.setDamage(0);
            }
            world.playSound(context.getPlayer(), context.getPos(), SoundEvents.ITEM_TOTEM_USE, SoundCategory.PLAYERS, 1f, 1f);
            return ActionResultType.SUCCESS;
        }

        if (item.getDamage()+1 >= item.getMaxDamage()){
            world.playSound(context.getPlayer(), context.getPos(), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.PLAYERS, 1f, 1f);
            return ActionResultType.FAIL;
        }

        switch (GauntletActions.values()[currentMode]){
            case TRANSFORM:
                convertDirtToSand(context);
                useDurability(context);
                return ActionResultType.SUCCESS;
            case RAISE:
                raiseEarth(context);
                useDurability(context);
                return ActionResultType.SUCCESS;
            case INSPECT:
                inspectItemInMainHand(context);
                return ActionResultType.SUCCESS;
        }

        return ActionResultType.FAIL;
    }

    private void inspectItemInMainHand(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        ItemStack item = player.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
    }

    private void raiseEarth(ItemUseContext context) {
        World world = context.getWorld();
        if (!world.isRemote){
            doMove(world, context.getPos().down(), Direction.UP, true);
            doMove(world, context.getPos(), Direction.UP, true);
        }
        world.playSound(context.getPlayer(), context.getPos(), SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.PLAYERS, 1f, 1f);
    }

    private void useDurability(ItemUseContext context) {
        if (!context.getWorld().isRemote){
            context.getItem().damageItem(1, context.getPlayer(), (playerEntity)->{});
        }
    }

    private void convertDirtToSand(ItemUseContext context) {
        World world = context.getWorld();
        if (world.getBlockState(context.getPos()) == Blocks.DIRT.getDefaultState()){
            world.playSound(context.getPlayer(), context.getPos(), SoundEvents.BLOCK_GRAVEL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);

            if (!world.isRemote){
                world.setBlockState(context.getPos(), Blocks.SAND.getDefaultState());
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote){
            if (playerIn.isCrouching()){
                currentMode -= 1;
            } else {
                currentMode += 1;
            }
            if (currentMode >= GauntletActions.values().length){
                currentMode = GauntletActions.values().length-1;
            }
            if (currentMode < 0){
                currentMode = 0;
            }
            GauntletActions mode = GauntletActions.values()[currentMode];
            playerIn.sendMessage(TextComponentUtils.toTextComponent(new LiteralMessage("Changing to mode " + mode)));
        }
        return ActionResult.resultFail(playerIn.getHeldItem(handIn));
    }



    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        return true;
    }


    private boolean doMove(World worldIn, BlockPos pos, Direction directionIn, boolean extending) {
        BlockPos blockpos = pos.offset(directionIn);

        PistonBlockStructureHelper pistonblockstructurehelper = new PistonBlockStructureHelper(worldIn, pos, directionIn, extending);
        if (!pistonblockstructurehelper.canMove()) {
            return false;
        } else {
            Map<BlockPos, BlockState> map = Maps.newHashMap();
            List<BlockPos> list = pistonblockstructurehelper.getBlocksToMove();
            List<BlockState> list1 = Lists.newArrayList();

            for(int i = 0; i < list.size(); ++i) {
                BlockPos blockpos1 = list.get(i);
                BlockState blockstate = worldIn.getBlockState(blockpos1);
                list1.add(blockstate);
                map.put(blockpos1, blockstate);
            }

            List<BlockPos> list2 = pistonblockstructurehelper.getBlocksToDestroy();
            int k = list.size() + list2.size();
            BlockState[] ablockstate = new BlockState[k];
            Direction direction = extending ? directionIn : directionIn.getOpposite();

            for(int j = list2.size() - 1; j >= 0; --j) {
                BlockPos blockpos2 = list2.get(j);
                BlockState blockstate1 = worldIn.getBlockState(blockpos2);
                worldIn.setBlockState(blockpos2, Blocks.AIR.getDefaultState(), 18);
                --k;
                ablockstate[k] = blockstate1;
            }

            for(int l = list.size() - 1; l >= 0; --l) {
                BlockPos blockpos3 = list.get(l);
                BlockState blockstate5 = worldIn.getBlockState(blockpos3);
                blockpos3 = blockpos3.offset(direction);
                map.remove(blockpos3);
                worldIn.setBlockState(blockpos3, Blocks.MOVING_PISTON.getDefaultState().with(BlockStateProperties.FACING, directionIn), 68);
                worldIn.setTileEntity(blockpos3, MovingPistonBlock.createTilePiston(list1.get(l), directionIn, extending, false));
                --k;
                ablockstate[k] = blockstate5;
            }

            BlockState blockstate3 = Blocks.AIR.getDefaultState();

            for(BlockPos blockpos4 : map.keySet()) {
                worldIn.setBlockState(blockpos4, blockstate3, 82);
            }

            for(Map.Entry<BlockPos, BlockState> entry : map.entrySet()) {
                BlockPos blockpos5 = entry.getKey();
                BlockState blockstate2 = entry.getValue();
                blockstate2.updateDiagonalNeighbors(worldIn, blockpos5, 2);
                blockstate3.updateNeighbors(worldIn, blockpos5, 2);
                blockstate3.updateDiagonalNeighbors(worldIn, blockpos5, 2);
            }

            for(int i1 = list2.size() - 1; i1 >= 0; --i1) {
                BlockState blockstate7 = ablockstate[k++];
                BlockPos blockpos6 = list2.get(i1);
                blockstate7.updateDiagonalNeighbors(worldIn, blockpos6, 2);
                worldIn.notifyNeighborsOfStateChange(blockpos6, blockstate7.getBlock());
            }

            for(int j1 = list.size() - 1; j1 >= 0; --j1) {
                worldIn.notifyNeighborsOfStateChange(list.get(j1), ablockstate[k++].getBlock());
            }

            if (extending) {
                worldIn.notifyNeighborsOfStateChange(blockpos, Blocks.PISTON_HEAD);
            }

            return true;
        }
    }



}
