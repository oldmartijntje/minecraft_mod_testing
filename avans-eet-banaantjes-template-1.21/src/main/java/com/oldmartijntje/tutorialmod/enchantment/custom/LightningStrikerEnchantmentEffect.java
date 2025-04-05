package com.oldmartijntje.tutorialmod.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public record LightningStrikerEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<LightningStrikerEnchantmentEffect> CODEC = MapCodec.unit(LightningStrikerEnchantmentEffect::new);

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        // i improved this myself, the tutorial has if statements for every level xD
        Random random = new Random();
        // Adjust the spread factor as needed – here it's set proportional to the level.
        int spread = level / 4;
        for (int i = 0; i < level; i++) {
            // Generate offsets from -spread to +spread (inclusive)
            int offsetX = random.nextInt(spread * 2 + 1) - spread;
            int offsetZ = random.nextInt(spread * 2 + 1) - spread;
            // Spawn the lightning at the calculated position based on the user's current block position.
            BlockPos spawnPos = user.getBlockPos().add(offsetX, 0, offsetZ);
            EntityType.LIGHTNING_BOLT.spawn(world, spawnPos, SpawnReason.TRIGGERED);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
