package com.oldmartijntje.tutorialmod;

import com.oldmartijntje.tutorialmod.block.ModBlocks;
import com.oldmartijntje.tutorialmod.component.ModDataComponentTypes;
import com.oldmartijntje.tutorialmod.effect.ModEffects;
import com.oldmartijntje.tutorialmod.enchantment.ModEnchantmentEffects;
import com.oldmartijntje.tutorialmod.enchantment.ModEnchantments;
import com.oldmartijntje.tutorialmod.item.ModItemGroups;
import com.oldmartijntje.tutorialmod.item.ModItems;
import com.oldmartijntje.tutorialmod.potion.ModPotions;
import com.oldmartijntje.tutorialmod.util.HammerUsageEvent;
import com.oldmartijntje.tutorialmod.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AvansEetBanaantjes implements ModInitializer {
	public static final String MOD_ID = "tutorialmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		ModEffects.registerEffects();
		ModPotions.registerPotions();

		ModEnchantmentEffects.registerEnchantmentEffects();
		ModWorldGeneration.generateModWorldGen();

		ModDataComponentTypes.registerDataComponentTypes();

		FuelRegistry.INSTANCE.add(ModItems.FALLEN_STAR, 600);

		PlayerBlockBreakEvents.BEFORE.register(new HammerUsageEvent());

		AttackEntityCallback.EVENT.register((playerEntity, world, hand, entity, entityHitResult) -> {
			if(entity instanceof SheepEntity sheepEntity && !world.isClient()) {
				if (playerEntity.getMainHandStack().getItem() == Items.END_ROD) {
					playerEntity.sendMessage(Text.literal("Bababooi!"));
					playerEntity.getMainHandStack().decrement(1);
					sheepEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 600, 6));
				}
			}
			return ActionResult.PASS;
		});

		FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
			builder.registerPotionRecipe(Potions.AWKWARD, Items.SLIME_BALL, ModPotions.SLIMEY_POTION);
			builder.registerPotionRecipe(Potions.AWKWARD, Items.WOODEN_SHOVEL, ModPotions.SPLEEF_POTION_WOOD);
			builder.registerPotionRecipe(Potions.AWKWARD, Items.STONE_SHOVEL, ModPotions.SPLEEF_POTION_STONE);
			builder.registerPotionRecipe(Potions.AWKWARD, Items.IRON_SHOVEL, ModPotions.SPLEEF_POTION_IRON);
			builder.registerPotionRecipe(Potions.AWKWARD, Items.DIAMOND_SHOVEL, ModPotions.SPLEEF_POTION_DIAMOND);
			builder.registerPotionRecipe(Potions.AWKWARD, Items.NETHERITE_SHOVEL, ModPotions.SPLEEF_POTION_NETHERITE);
		});

		CompostingChanceRegistry.INSTANCE.add(ModItems.CAULIFLOWER, 0.5f);
		CompostingChanceRegistry.INSTANCE.add(ModItems.CAULIFLOWER_SEEDS, 0.25f);
		CompostingChanceRegistry.INSTANCE.add(ModItems.HONEY_BERRIES, 0.15f);
	}
}