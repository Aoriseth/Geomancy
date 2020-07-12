package com.aoriseth.geomancy.blocks;

import com.aoriseth.geomancy.registry.GeomancyBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;

public class BurrowTunnelBlockEntity extends BlockEntity {

    public static final String LOCATION_X = "location-x";
    public static final String LOCATION_Y = "location-y";
    public static final String LOCATION_Z = "location-z";

    private BlockPos homeLocation;

    public BurrowTunnelBlockEntity() {
        super(GeomancyBlockEntities.BURROW_TUNNEL_ENTITY);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);

        tag.putDouble(LOCATION_X, homeLocation.getX());
        tag.putDouble(LOCATION_Y, homeLocation.getY());
        tag.putDouble(LOCATION_Z, homeLocation.getZ());

        return tag;
    }

    public BlockPos getTargetLocation() {
        return homeLocation;
    }

    public void setTargetLocation(BlockPos homeLocation) {
        this.homeLocation = homeLocation;
        markDirty();
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        homeLocation = new BlockPos(tag.getDouble(LOCATION_X), tag.getDouble(LOCATION_Y), tag.getDouble(LOCATION_Z));
    }
}
