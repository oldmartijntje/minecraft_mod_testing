package com.oldmartijntje.tutorialmod.item.custom;

import com.oldmartijntje.tutorialmod.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
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

public class DrillItem extends Item {
    public DrillItem(Settings settings) {
        super(settings);
    }


    @Override
    public void onCraftByPlayer(ItemStack stack, World world, PlayerEntity player) {
        stack.setDamage(30);
        super.onCraftByPlayer(stack, world, player);
    }

    @Override
    public void onCraft(ItemStack stack, World world) {
        stack.setDamage(30);
        super.onCraft(stack, world);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        Block clickedBlock = world.getBlockState(context.getBlockPos()).getBlock();

        if (!world.isClient()) {
            if (clickedBlock == Blocks.BEDROCK) {
                world.setBlockState(context.getBlockPos(), Blocks.AIR.getDefaultState());
                context.getStack().damage(2, ((ServerWorld) world), ((ServerPlayerEntity) context.getPlayer()),
                        item -> context.getPlayer().sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND));

                world.playSound(null, context.getBlockPos(), SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.BLOCKS);
            } else if (clickedBlock == Blocks.TNT) {
                world.setBlockState(context.getBlockPos(), Blocks.AIR.getDefaultState());
                if (context.getStack().isDamaged()) {
                    context.getStack().damage(-1, ((ServerWorld) world), ((ServerPlayerEntity) context.getPlayer()),
                            item -> context.getPlayer().sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND));
                }
                world.playSound(null, context.getBlockPos(), SoundEvents.BLOCK_COMPOSTER_FILL, SoundCategory.BLOCKS);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.tutorialmod.drill"));
            tooltip.add(Text.translatable("tooltip.tutorialmod.drill.shift_down"));
            tooltip.add(Text.translatable("tooltip.tutorialmod.drill.shift_down.1"));
            tooltip.add(Text.translatable("tooltip.tutorialmod.drill.shift_down.2"));

        } else {
            tooltip.add(Text.translatable("tooltip.tutorialmod.drill"));
            tooltip.add(Text.translatable("tooltip.tutorialmod.drill.1"));
        }
        super.appendTooltip(stack, context, tooltip, options);
    }
}
