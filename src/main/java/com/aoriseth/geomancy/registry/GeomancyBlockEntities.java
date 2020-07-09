package com.aoriseth.geomancy.registry;

import com.aoriseth.geomancy.blocks.BurrowTunnelBlockEntity;
import com.mojang.datafixers.types.Type;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

public class GeomancyBlockEntities {

    public static final BlockEntityType<BurrowTunnelBlockEntity> BURROW_TUNNEL_ENTITY = create("burrow_tunnel_entity",BlockEntityType.Builder.create(BurrowTunnelBlockEntity::new, GeomancyBlocks.BURROW_TUNNEL_BLOCK));


    private static <T extends BlockEntity> BlockEntityType<T> create(String name, BlockEntityType.Builder<T> builder) {
        Type<?> type = Util.method_29187(TypeReferences.BLOCK_ENTITY, name);
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, name, builder.build(type));
    }

    public static void init() {
        // Initialize registry
    }
}
