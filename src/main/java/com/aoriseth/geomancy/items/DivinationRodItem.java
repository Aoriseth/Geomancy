package com.aoriseth.geomancy.items;

import com.aoriseth.geomancy.Geomancy;
import com.aoriseth.geomancy.registry.GeomancyBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Optional;

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
            if (!(world.getBlockState(context.getBlockPos()) == GeomancyBlocks.DIVINATION_SAND.getDefaultState())){
                if (context.getSide() == Direction.UP){
                    Optional<ItemStack> sandStack = context.getPlayer().inventory.main.stream()
                            .filter((itemStack -> itemStack.getItem() == Items.SAND))
                            .findFirst();

                    if (sandStack.isPresent()){
                        world.setBlockState(context.getBlockPos().up(),GeomancyBlocks.DIVINATION_SAND.getDefaultState());
                        sandStack.get().decrement(1);
                    } else {
                      context.getPlayer().sendMessage(new LiteralText("Need sand to perform divination"),true);
                    }
                }
            }
            world.playSound(context.getPlayer(), context.getBlockPos(), SoundEvents.BLOCK_SAND_PLACE, SoundCategory.PLAYERS, 1f, 1f);
        }
        return ActionResult.SUCCESS;
    }
}
