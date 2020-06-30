package com.deigon.geomancy.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class SunStoneBlock extends Block {
    public SunStoneBlock() {
        super(Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1f));
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 15;
    }
}
