package com.oldmartijntje.tutorialmod.potion;

import com.oldmartijntje.tutorialmod.AvansEetBanaantjes;
import com.oldmartijntje.tutorialmod.effect.ModEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModPotions {
    public static final RegistryEntry<Potion> SLIMEY_POTION = registerPotion("slimey_potion",
            new Potion(new StatusEffectInstance(ModEffects.SLIMEY, 1200, 0)));
    public static final RegistryEntry<Potion> SPLEEF_POTION_WOOD = registerPotion("spleef_potion_wood",
            new Potion(new StatusEffectInstance(ModEffects.SPLEEF, 1200, 0)));
    public static final RegistryEntry<Potion> SPLEEF_POTION_STONE = registerPotion("spleef_potion_stone",
            new Potion(new StatusEffectInstance(ModEffects.SPLEEF, 1200, 3)));
    public static final RegistryEntry<Potion> SPLEEF_POTION_IRON = registerPotion("spleef_potion_iron",
            new Potion(new StatusEffectInstance(ModEffects.SPLEEF, 1200, 7)));
    public static final RegistryEntry<Potion> SPLEEF_POTION_DIAMOND = registerPotion("spleef_potion_diamond",
            new Potion(new StatusEffectInstance(ModEffects.SPLEEF, 1200, 11)));
    public static final RegistryEntry<Potion> SPLEEF_POTION_NETHERITE = registerPotion("spleef_potion_netherite",
            new Potion(new StatusEffectInstance(ModEffects.SPLEEF, 1200, 100)));

    private static RegistryEntry<Potion> registerPotion(String name, Potion potion) {
        return Registry.registerReference(Registries.POTION, Identifier.of(AvansEetBanaantjes.MOD_ID, name), potion);
    }

    public static void registerPotions() {
        AvansEetBanaantjes.LOGGER.info("Registering Mod Potions for "+ AvansEetBanaantjes.MOD_ID);
    }
}
