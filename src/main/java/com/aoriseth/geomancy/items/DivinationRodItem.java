package com.aoriseth.geomancy.items;

import com.aoriseth.geomancy.Geomancy;
import com.aoriseth.geomancy.registry.GeomancyBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Optional;

import static com.aoriseth.geomancy.blocks.SandDivinationBlock.*;

public class DivinationRodItem extends Item {

    public DivinationRodItem() {
        super(new Settings()
                .group(Geomancy.itemGroup)
                .maxCount(1));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient){
            markDivinationSand(context);
            placeDivinationSand(context);
        }
        world.playSound(context.getPlayer(), context.getBlockPos(), SoundEvents.BLOCK_SAND_PLACE, SoundCategory.PLAYERS, 1f, 1f);
        return ActionResult.SUCCESS;
    }

    private void markDivinationSand(ItemUsageContext context) {
        BlockState blockState = context.getWorld().getBlockState(context.getBlockPos());
        if (blockState.getBlock() == GeomancyBlocks.DIVINATION_SAND){
            int inscriptionValue = RANDOM.nextBoolean() ? 1 : 2;
            IntProperty[] stages = {FIRST_INSCRIPTION, SECOND_INSCRIPTION, THIRD_INSCRIPTION, FOURTH_INSCRIPTION};

            for (IntProperty stage : stages) {
                if (blockState.get(stage) <= 0){
                    placeMarkAt(context, inscriptionValue, stage);
                    break;
                }
            }
        }
    }

    private boolean placeMarkAt(ItemUsageContext context, int inscription, IntProperty firstInscription) {
        return context.getWorld().setBlockState(context.getBlockPos(), context.getWorld().getBlockState(context.getBlockPos()).with(firstInscription, inscription));
    }

    private void placeDivinationSand(ItemUsageContext context) {
        World world = context.getWorld();

        // divination sand is not placeable on these blocks
        Block[] disallowedBlocks = {GeomancyBlocks.DIVINATION_SAND, Blocks.SNOW, Blocks.TORCH };

        if (Arrays.stream(disallowedBlocks).noneMatch((block -> block == world.getBlockState(context.getBlockPos()).getBlock()))){
            if (context.getSide() == Direction.UP){
                Optional<ItemStack> sandStack = context.getPlayer().inventory.main.stream()
                        .filter((itemStack -> itemStack.getItem() == Items.SAND))
                        .findFirst();

                if (sandStack.isPresent()){
                    world.setBlockState(context.getBlockPos().up(), GeomancyBlocks.DIVINATION_SAND.getDefaultState());
                    sandStack.get().decrement(1);
                } else {
                  context.getPlayer().sendMessage(new LiteralText("Need sand to perform divination"),true);
                }
            }
        }
    }
}
