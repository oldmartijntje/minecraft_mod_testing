package com.oldmartijntje.tutorialmod.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public record LimboEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<LimboEnchantmentEffect> CODEC = MapCodec.unit(LimboEnchantmentEffect::new);

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        if (user == null) {
            return;
        }
        RegistryKey<World> currentDim = user.getWorld().getRegistryKey();
        RegistryKey<World> targetDim = World.NETHER;
        if (currentDim == targetDim) return;
        MinecraftServer server = world.getServer();
        ServerWorld targetWorld = server.getWorld(targetDim);
        if (targetWorld == null) return;
        BlockPos userPos = user.getBlockPos();
        int x = Math.round((float) userPos.getX() / 8);
        int y = userPos.getY();
        if (y < 0) {
            y += 64;
        }
        if (y > 128) {
            y -= 96;
        }
        int z = Math.round((float) userPos.getZ() / 8);;
        float yaw = user.getYaw();
        float pitch = user.getPitch();
        BlockPos targetPos = new BlockPos(x, y, z);
        targetWorld.breakBlock(targetPos, true);
        targetWorld.breakBlock(targetPos.up(), true);
        targetWorld.setBlockState(targetPos.down(1), Blocks.MAGMA_BLOCK.getDefaultState());
        user.teleport(targetWorld, x, y, z,  Collections.emptySet(), yaw, pitch);
        System.out.println("sent entity Limbo at ["+ x + ", " + y + ", " + z + "] go there with '/tp @s " + x + " " + y + " " + z + "'");

    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
