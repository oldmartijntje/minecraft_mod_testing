package com.oldmartijntje.tutorialmod.trim;

import com.oldmartijntje.tutorialmod.AvansEetBanaantjes;
import com.oldmartijntje.tutorialmod.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class ModTrimMaterials {
    public static final RegistryKey<ArmorTrimMaterial> PINK_GARNET = RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
            Identifier.of(AvansEetBanaantjes.MOD_ID, "pink_garnet"));

    public static void bootstrap(Registerable<ArmorTrimMaterial> registerable) {
        register(registerable, PINK_GARNET, Registries.ITEM.getEntry(ModItems.PINK_GARNET),
                Style.EMPTY.withColor(TextColor.parse("#ff00bf").getOrThrow()), 1.0f);
    }

    private static void register(Registerable<ArmorTrimMaterial> registrable, RegistryKey<ArmorTrimMaterial> armorTrimKey,
                                 RegistryEntry<Item> item, Style style, float itemModelIndex) {
        ArmorTrimMaterial trimMaterial = new ArmorTrimMaterial(armorTrimKey.getValue().getPath(), item, itemModelIndex, Map.of(),
                Text.translatable(Util.createTranslationKey("trim_material", armorTrimKey.getValue())).fillStyle(style));

        registrable.register(armorTrimKey, trimMaterial);
    }
}
