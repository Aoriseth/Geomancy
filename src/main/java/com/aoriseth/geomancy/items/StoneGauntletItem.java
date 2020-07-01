package com.aoriseth.geomancy.items;

import com.aoriseth.geomancy.Geomancy;
import com.aoriseth.geomancy.registry.GeomancyBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class StoneGauntletItem extends ToolItem {

    enum GauntletActions{
        TRANSFORM,
        RAISE,
        LAUNCH,
        INSPECT
    }

    private int currentMode = 0;

    public StoneGauntletItem() {
        super(
                ToolMaterials.STONE,
                new Item.Settings()
                        .group(Geomancy.itemGroup)
                        .maxDamage(20)
                        .rarity(Rarity.UNCOMMON));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        ItemStack item = context.getStack();

        if (world.getBlockState(context.getBlockPos()) == GeomancyBlocks.ESSENCE_SPIKE.getDefaultState()){
            if (!world.isClient){
                item.setDamage(0);
            }
            world.playSound(context.getPlayer(), context.getBlockPos(), SoundEvents.ITEM_LODESTONE_COMPASS_LOCK, SoundCategory.PLAYERS, 1f, 1f);
            return ActionResult.SUCCESS;
        }

        if (item.getDamage()+1 >= item.getMaxDamage()){
            world.playSound(context.getPlayer(), context.getBlockPos(), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.PLAYERS, 1f, 1f);
            return ActionResult.FAIL;
        }

        switch (GauntletActions.values()[currentMode]){
            case TRANSFORM:
                convertDirtToSand(context);
                convertIronOreToMagnetite(context);
                useDurability(context);
                return ActionResult.SUCCESS;
            case RAISE:
                raiseEarth(context);
                useDurability(context);
                return ActionResult.SUCCESS;
            case INSPECT:
                inspectItemInMainHand(context);
                return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;
    }

    private void inspectItemInMainHand(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        ItemStack stackInHand = player.getStackInHand(Hand.MAIN_HAND);
        player.sendMessage((stackInHand.getItem().getName()), true);
    }

    private void raiseEarth(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient){
            // Move block
        }
        world.playSound(context.getPlayer(), context.getBlockPos(), SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.PLAYERS, 1f, 1f);
    }

    private void useDurability(ItemUsageContext context) {
        if (!context.getWorld().isClient){
            context.getStack().damage(1, context.getPlayer(), (playerEntity)->{});
        }
    }

    private void convertIronOreToMagnetite(ItemUsageContext context) {
        World world = context.getWorld();

        if (world.getBlockState(context.getBlockPos()) == Blocks.IRON_ORE.getDefaultState()){
            world.playSound(context.getPlayer(), context.getBlockPos(), SoundEvents.BLOCK_METAL_PLACE, SoundCategory.PLAYERS, 1f, 1f);

            if (!world.isClient){
                world.setBlockState(context.getBlockPos(), GeomancyBlocks.MAGNETITE_ORE.getDefaultState());
            }
        }

    }

    private void convertDirtToSand(ItemUsageContext context) {
        World world = context.getWorld();

        if (world.getBlockState(context.getBlockPos())== Blocks.DIRT.getDefaultState()){
            world.playSound(context.getPlayer(), context.getBlockPos(), SoundEvents.BLOCK_GRAVEL_PLACE, SoundCategory.PLAYERS, 1f, 1f);

            if (!world.isClient){
                world.setBlockState(context.getBlockPos(), Blocks.SAND.getDefaultState());
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient){
            if (user.isInSneakingPose()){
                currentMode -= 1;
            }else {
                currentMode +=1;
            }

            if (currentMode>= GauntletActions.values().length){
                currentMode = 0;
            }
            if (currentMode < 0){
                currentMode = GauntletActions.values().length-1;
            }
            GauntletActions mode = GauntletActions.values()[currentMode];
            user.sendMessage(new LiteralText("Changing to mode " + mode), true);
        }
        return TypedActionResult.fail(user.getStackInHand(hand));
    }
}
