package com.oldmartijntje.tutorialmod.item;

import com.oldmartijntje.tutorialmod.AvansEetBanaantjes;
import com.oldmartijntje.tutorialmod.item.custom.ChiselItem;
import com.oldmartijntje.tutorialmod.item.custom.DrillItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModItems {
    public static final Item PINK_GARNET = registerItem("pink_garnet", new Item(new Item.Settings()));
    public static final Item RAW_PINK_GARNET = registerItem("raw_pink_garnet", new Item(new Item.Settings()));

    public static final Item CHISEL = registerItem("chisel", new ChiselItem(new Item.Settings().maxDamage(32)));
    public static final Item DRILL = registerItem("drill", new DrillItem(new Item.Settings().maxDamage(32)));
    public static final Item CAULIFLOWER = registerItem("cauliflower", new Item(new Item.Settings().food(ModFoodComponent.CAULIFLOWER)){
        // this apparently is an anonymous class
        // here you can expend the class

        @Override
        public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
            tooltip.add(Text.translatable("tooltip.tutorialmod.cauliflower"));
            super.appendTooltip(stack, context, tooltip, options);
        }
    });
    public static final Item FALLEN_STAR = registerItem("fallen_star", new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(AvansEetBanaantjes.MOD_ID, name), item);
    }
    public static void registerModItems() {
        AvansEetBanaantjes.LOGGER.info("registering ModItems for " + AvansEetBanaantjes.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(PINK_GARNET);
            entries.add(RAW_PINK_GARNET);
        });
    }
 }
