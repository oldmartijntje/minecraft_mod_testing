package com.oldmartijntje.tutorialmod.world;

import com.oldmartijntje.tutorialmod.AvansEetBanaantjes;
import com.oldmartijntje.tutorialmod.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.List;

public class ModConfiguredFeatures {
    // Configured Feature -> Place Feature -> Biome Modifications
    public static final RegistryKey<ConfiguredFeature<?, ?>> PINK_GARNET_ORE_KEY = registerKey("pink_garnet_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> NETHER_PINK_GARNET_ORE_KEY = registerKey("nether_pink_garnet_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> END_PINK_GARNET_ORE_KEY = registerKey("end_pink_garnet_ore");

    public static final RegistryKey<ConfiguredFeature<?, ?>> DRIFTWOOD_KEY = registerKey("driftwood");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherReplaceables = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
        RuleTest endReplaceables = new BlockMatchRuleTest(Blocks.END_STONE);

        List<OreFeatureConfig.Target> overworldPinkGarnetOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.PINK_GARNET_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.PINK_GARNET_DEEPSLATE_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> netherPinkGarnetOres =
                List.of(OreFeatureConfig.createTarget(netherReplaceables, ModBlocks.PINK_GARNET_NETHER_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> endPinkGarnetOres =
                List.of(OreFeatureConfig.createTarget(endReplaceables, ModBlocks.PINK_GARNET_END_ORE.getDefaultState()));

        register(context, PINK_GARNET_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldPinkGarnetOres, 12));
        register(context, NETHER_PINK_GARNET_ORE_KEY, Feature.ORE, new OreFeatureConfig(netherPinkGarnetOres, 9));
        register(context, END_PINK_GARNET_ORE_KEY, Feature.ORE, new OreFeatureConfig(endPinkGarnetOres, 9));

        register(context, DRIFTWOOD_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(ModBlocks.DRIFTWOOD_LOG),
                new StraightTrunkPlacer(5, 6, 3),

                BlockStateProvider.of(ModBlocks.DRIFTWOOD_LEAVES),
                new BlobFoliagePlacer(ConstantIntProvider.create(4), ConstantIntProvider.create(1), 3),

                new TwoLayersFeatureSize(1, 0, 2)).dirtProvider(BlockStateProvider.of(Blocks.SAND)).build());
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(AvansEetBanaantjes.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
