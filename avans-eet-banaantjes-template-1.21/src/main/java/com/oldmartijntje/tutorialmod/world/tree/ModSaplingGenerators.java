package com.oldmartijntje.tutorialmod.world.tree;

import com.oldmartijntje.tutorialmod.AvansEetBanaantjes;
import com.oldmartijntje.tutorialmod.world.ModConfiguredFeatures;
import net.minecraft.block.SaplingGenerator;

import java.util.Optional;

public class ModSaplingGenerators {
    public static final SaplingGenerator DRIFTWOOD = new SaplingGenerator(AvansEetBanaantjes.MOD_ID + ":driftwood",
            Optional.empty(), Optional.of(ModConfiguredFeatures.DRIFTWOOD_KEY), Optional.empty());
}
