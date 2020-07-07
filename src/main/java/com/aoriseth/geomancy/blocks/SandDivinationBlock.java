package com.aoriseth.geomancy.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class SandDivinationBlock extends Block{
    public static final IntProperty FIRST_INSCRIPTION = IntProperty.of("first", 0, 2);
    public static final IntProperty SECOND_INSCRIPTION = IntProperty.of("second", 0, 2);
    public static final IntProperty THIRD_INSCRIPTION = IntProperty.of("third", 0, 2);
    public static final IntProperty FOURTH_INSCRIPTION = IntProperty.of("fourth", 0, 2);
    protected static final VoxelShape[] LAYERS_TO_SHAPE = new VoxelShape[]{VoxelShapes.empty(), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D)};

    public SandDivinationBlock() {
        super(
                FabricBlockSettings
                        .of(Material.SOIL)
                        .sounds(BlockSoundGroup.SAND)
                        .strength(0.5f)
                        .dropsNothing()
                        .noCollision()
                        .nonOpaque()
        );
        setDefaultState(
                getStateManager()
                        .getDefaultState()
                        .with(FIRST_INSCRIPTION, 0)
                        .with(SECOND_INSCRIPTION, 0)
                        .with(THIRD_INSCRIPTION, 0)
                        .with(FOURTH_INSCRIPTION, 0)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder
                .add(FIRST_INSCRIPTION)
                .add(SECOND_INSCRIPTION)
                .add(THIRD_INSCRIPTION)
                .add(FOURTH_INSCRIPTION);
    }

    @Override
    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return LAYERS_TO_SHAPE[1];
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return LAYERS_TO_SHAPE[1];
    }
}