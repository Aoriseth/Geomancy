package com.aoriseth.geomancy.items;

import com.aoriseth.geomancy.Geomancy;
import com.aoriseth.geomancy.blocks.BurrowTunnelBlockEntity;
import com.aoriseth.geomancy.registry.GeomancyBlockEntities;
import com.aoriseth.geomancy.registry.GeomancyBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class StoneGauntletItem extends ToolItem {

    enum GauntletActions{
        TRANSFORM,
        RAISE,
        LAUNCH,
        INSPECT,
        BURROW
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

        if (world.getBlockState(context.getBlockPos()) == GeomancyBlocks.BURROW_TUNNEL_BLOCK.getDefaultState()){
            if (!world.isClient){
                removeBurrowLocation(world, context.getBlockPos());
            }
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
                inspectBlockClicked(context);
                return ActionResult.SUCCESS;
            case BURROW:
                createTunnelToSpawn(context);
                return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;
    }

    private void removeBurrowLocation(World world, BlockPos blockPos) {
        BurrowTunnelBlockEntity blockEntity = GeomancyBlockEntities.BURROW_TUNNEL_ENTITY.get(world, blockPos);
        BlockPos targetLocation = blockEntity.getTargetLocation();

        // remove burrow entities

        // remove burrow blocks
        world.setBlockState(targetLocation, Blocks.AIR.getDefaultState());
        world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
    }

    private void createTunnelToSpawn(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        if (!world.isClient){
            ServerPlayerEntity player = (ServerPlayerEntity) context.getPlayer();
            BlockPos burrowTarget = player.getSpawnPointPosition().east().east().down();

            createBurrowLocation(world, blockPos, burrowTarget);
            createBurrowLocation(world, burrowTarget, blockPos);
        }
        world.playSound(context.getPlayer(), blockPos, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1f, 1f);
    }

    private void createBurrowLocation(World world, BlockPos blockPos, BlockPos targetPos) {
        // Open ground at blockPos
        BlockState blockState = world.getBlockState(blockPos);
        world.setBlockState(blockPos.up().north(), blockState);
        world.setBlockState(blockPos, Blocks.AIR.getDefaultState());

        // Create burrow block at original block pos
        BlockState burrowBlock = GeomancyBlocks.BURROW_TUNNEL_BLOCK.getDefaultState();
        world.setBlockState(blockPos, burrowBlock);

        // set burrow block target to targetPos
        BurrowTunnelBlockEntity blockEntity = GeomancyBlockEntities.BURROW_TUNNEL_ENTITY.get(world, blockPos);
        blockEntity.setTargetLocation(targetPos);
    }

    private void inspectBlockClicked(ItemUsageContext context) {
        context.getPlayer().sendMessage(new LiteralText(Registry.BLOCK.getId(context.getWorld().getBlockState(context.getBlockPos()).getBlock()).toString()),true);
    }

    private void inspectItemInMainHand(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        ItemStack stackInHand = player.getStackInHand(Hand.MAIN_HAND);
        player.sendMessage(new LiteralText(Registry.ITEM.getId(stackInHand.getItem()).toString()), true);
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
