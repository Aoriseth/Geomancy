package com.aoriseth.geomancy.items;

import com.aoriseth.geomancy.Geomancy;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.StructureFeature;

public class DowsingRodItem extends Item {

    private BlockPos lastLocation = null;

    public DowsingRodItem() {
        super(new Item.Settings()
                .group(Geomancy.itemGroup)
                .maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            lastLocation = findStructure(world, user);

            if (lastLocation == null) {
                user.sendMessage(new LiteralText("Failed to find treasure"), true);
                return TypedActionResult.success(user.getStackInHand(hand));
            }
            return TypedActionResult.success(user.getStackInHand(hand));
        } else {
            if (lastLocation == null) {
                return TypedActionResult.success(user.getStackInHand(hand));
            }
            showLeyLines(world, user, ParticleTypes.END_ROD);
        }
        return TypedActionResult.fail(user.getStackInHand(hand));
    }

    private void showLeyLines(World world, PlayerEntity user, DefaultParticleType particle) {
        for (int i = -20; i <= 20; i++) {
            for (int j = -1; j <= 1; j++) {
                world.addParticle(particle, lastLocation.getX() + j, user.getY() + 1.5, user.getZ() + i, 0, -0.1, 0);
                world.addParticle(particle, user.getX() + i, user.getY() + 1.5, lastLocation.getZ() + j, 0, -0.1, 0);
            }
        }
    }

    private BlockPos findStructure(World world, PlayerEntity user) {
        StructureFeature[] leyLineLocations = new StructureFeature[]{StructureFeature.DESERT_PYRAMID, StructureFeature.END_CITY, StructureFeature.FORTRESS, StructureFeature.IGLOO, StructureFeature.MANSION, StructureFeature.MINESHAFT, StructureFeature.BURIED_TREASURE};
        BlockPos structure = null;

        for (StructureFeature location : leyLineLocations) {
            structure = world.getServer().getWorld(World.OVERWORLD).locateStructure(location, user.getBlockPos(), 100, false);
        }
        return structure;
    }
}
