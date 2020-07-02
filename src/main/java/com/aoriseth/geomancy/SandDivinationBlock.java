package com.aoriseth.geomancy;

import net.minecraft.block.BlockState;
import net.minecraft.block.SnowBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class SandDivinationBlock extends SnowBlock {
    public SandDivinationBlock(Settings settings) {
        super(settings.strength(0.5f));
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // Do nothing
    }
}