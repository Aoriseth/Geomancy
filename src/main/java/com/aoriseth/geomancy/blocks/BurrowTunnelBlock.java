package com.aoriseth.geomancy.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BurrowTunnelBlock extends BlockWithEntity {

    public BurrowTunnelBlock() {
        super(
                FabricBlockSettings
                        .of(Material.SOIL)
                        .sounds(BlockSoundGroup.SAND)
                        .strength(3f)
        );
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, Entity entity) {
        if (!world.isClient){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof BurrowTunnelBlockEntity){
                BlockPos homeLocation = ((BurrowTunnelBlockEntity) blockEntity).getHomeLocation();
                if (homeLocation != null){
                    entity.requestTeleport(homeLocation.getX(), homeLocation.getY(), homeLocation.getZ());
                }
            }
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new BurrowTunnelBlockEntity();
    }


}