package com.oldmartijntje.tutorialmod.item.custom;

import com.oldmartijntje.tutorialmod.block.ModBlocks;
import com.oldmartijntje.tutorialmod.component.ModDataComponentTypes;
import com.oldmartijntje.tutorialmod.sound.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

public class ChiselItem extends Item {
    private static final Map<Block, Block> CHISEL_MAP =
            Map.of(
                    Blocks.STONE, Blocks.STONE_BRICKS,
                    Blocks.END_STONE, Blocks.END_STONE_BRICKS,
                    Blocks.TORCH, Blocks.REDSTONE_TORCH,
                    Blocks.CRAFTING_TABLE, Blocks.CRAFTER,
                    ModBlocks.PINK_GARNET_BLOCK, ModBlocks.MAGIC_BLOCK
            );


    public ChiselItem(Settings settings) {
        super(settings);
    }

    private static <K, V> K getKeyByValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        Block clickedBlock = world.getBlockState(context.getBlockPos()).getBlock();

        if (!world.isClient()) {
            if (CHISEL_MAP.containsKey(clickedBlock)) {
                world.setBlockState(context.getBlockPos(), CHISEL_MAP.get(clickedBlock).getDefaultState());
                context.getStack().damage(1, ((ServerWorld) world), ((ServerPlayerEntity) context.getPlayer()),
                        item -> context.getPlayer().sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND));

                world.playSound(null, context.getBlockPos(), SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS);
                context.getStack().set(ModDataComponentTypes.COORDINATES, context.getBlockPos());
            } else if (CHISEL_MAP.containsValue(clickedBlock)) {
                world.setBlockState(context.getBlockPos(), getKeyByValue(CHISEL_MAP, clickedBlock).getDefaultState());
                context.getStack().damage(1, ((ServerWorld) world), ((ServerPlayerEntity) context.getPlayer()),
                        item -> context.getPlayer().sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND));

                world.playSound(null, context.getBlockPos(), ModSounds.CHISEL_USE, SoundCategory.BLOCKS);
                context.getStack().set(ModDataComponentTypes.COORDINATES, context.getBlockPos());
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.tutorialmod.chisel.shift_down"));
            tooltip.add(Text.translatable("tooltip.tutorialmod.chisel.shift_down.1"));
        } else {
            tooltip.add(Text.translatable("tooltip.tutorialmod.chisel"));
        }
        if (stack.get(ModDataComponentTypes.COORDINATES) != null) {
            tooltip.add(Text.literal("Last Block Changed at " + stack.get(ModDataComponentTypes.COORDINATES)));
        }
        super.appendTooltip(stack, context, tooltip, options);
    }
}
