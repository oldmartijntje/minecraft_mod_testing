package com.oldmartijntje.tutorialmod.datagen;

import com.oldmartijntje.tutorialmod.block.ModBlocks;
import com.oldmartijntje.tutorialmod.block.custom.CauliflowerCropBlock;
import com.oldmartijntje.tutorialmod.block.custom.HoneyBerryBushBlock;
import com.oldmartijntje.tutorialmod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {

    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);

        addDrop(ModBlocks.MAGIC_BLOCK);
        addDrop(ModBlocks.RAW_PINK_GARNET_BLOCK);
        addDrop(ModBlocks.PINK_GARNET_BLOCK);

        addDrop(ModBlocks.PINK_GARNET_ORE, multipleOreDrops(ModBlocks.PINK_GARNET_ORE, ModItems.RAW_PINK_GARNET, 1, 4));
        addDrop(ModBlocks.PINK_GARNET_ORE, multipleOreDrops(ModBlocks.PINK_GARNET_ORE, ModItems.RAW_PINK_GARNET, 3, 7));

        addDrop(ModBlocks.PINK_GARNET_STAIRS);
        addDrop(ModBlocks.PINK_GARNET_SLAB, slabDrops(ModBlocks.PINK_GARNET_SLAB));

        addDrop(ModBlocks.PINK_GARNET_BUTTON);
        addDrop(ModBlocks.PINK_GARNET_PRESSURE_PLATE);

        addDrop(ModBlocks.PINK_GARNET_FENCE);
        addDrop(ModBlocks.PINK_GARNET_FENCE_GATE);
        addDrop(ModBlocks.PINK_GARNET_WALL);

        addDrop(ModBlocks.PINK_GARNET_DOOR, doorDrops(ModBlocks.PINK_GARNET_DOOR));
        addDrop(ModBlocks.PINK_GARNET_TRAPDOOR);

        BlockStatePropertyLootCondition.Builder builder2 = BlockStatePropertyLootCondition.builder(ModBlocks.CAULIFLOWER_CROP)
                .properties(StatePredicate.Builder.create().exactMatch(CauliflowerCropBlock.AGE, CauliflowerCropBlock.MAX_AGE));
        this.addDrop(ModBlocks.CAULIFLOWER_CROP, this.cropDrops(ModBlocks.CAULIFLOWER_CROP, ModItems.CAULIFLOWER, ModItems.CAULIFLOWER_SEEDS, builder2));

        this.addDrop(ModBlocks.HONEY_BERRY_BUSH,
                block -> this.applyExplosionDecay( block, LootTable.builder().pool(LootPool.builder()
                        .conditionally(BlockStatePropertyLootCondition.builder(ModBlocks.HONEY_BERRY_BUSH)
                        .properties(StatePredicate.Builder.create().exactMatch(HoneyBerryBushBlock.AGE, 3)))
                        .with(ItemEntry.builder(ModItems.HONEY_BERRIES))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0F, 3.0F)))
                        .apply(ApplyBonusLootFunction.uniformBonusCount(impl.getOrThrow(Enchantments.FORTUNE))))
                        .pool(LootPool.builder().conditionally(BlockStatePropertyLootCondition.builder(ModBlocks.HONEY_BERRY_BUSH)
                        .properties(StatePredicate.Builder.create().exactMatch(HoneyBerryBushBlock.AGE, 2)))
                        .with(ItemEntry.builder(ModItems.HONEY_BERRIES))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 2.0F)))
                        .apply(ApplyBonusLootFunction.uniformBonusCount(impl.getOrThrow(Enchantments.FORTUNE))))));

    }

    public LootTable.Builder multipleOreDrops(Block drop, Item item, float minDrops, float maxDrops) {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        return this.dropsWithSilkTouch(drop, this.applyExplosionDecay(drop, ((LeafEntry.Builder<?>)
                ItemEntry.builder(item).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(minDrops, maxDrops))))
                .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))));
    }
}
