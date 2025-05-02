package com.oldmartijntje.tutorialmod.world.gen;

public class ModWorldGeneration {
    public static void generateModWorldGen() {
        // order might matter.
        ModOreGeneration.generateOres();

        ModTreeGeneration.generateTrees();
        ModBushGeneration.generateBushes();
    }
}
