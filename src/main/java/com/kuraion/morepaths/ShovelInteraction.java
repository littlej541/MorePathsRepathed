package com.kuraion.morepaths;

import com.kuraion.morepaths.init.MorepathsModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ShovelInteraction {
    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickBlock event) {

        Player p = event.getEntity();

        ItemStack held = p.getMainHandItem();

        boolean isShovel = held.canPerformAction(ToolActions.SHOVEL_FLATTEN);

        if(!isShovel)
            return;

        Direction facing = event.getFace();

        if(facing == null)
            return;

        BlockPos pos = event.getPos();

        boolean playerCanEditBlock = p.mayUseItemAt(pos.offset(facing.getNormal()), facing, held);
        if(!playerCanEditBlock)
            return;

        Level world = event.getLevel();

        Material blockAbove = world.getBlockState(pos.above()).getMaterial();
        if(blockAbove != Material.AIR)
            return;

        Block targetBlock = world.getBlockState(pos).getBlock();

        Block pathBlock = MorepathsModBlocks.getPathForBlock(targetBlock);

        if(pathBlock == null)
            return;

        world.playSound(p, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);

        if(!world.isClientSide()) {
            world.setBlock(event.getPos(), pathBlock.defaultBlockState(), 11);
            world.gameEvent(GameEvent.BLOCK_CHANGE, event.getPos(), GameEvent.Context.of(p, pathBlock.defaultBlockState()));
            held.hurtAndBreak(1, p, (pl) -> { pl.broadcastBreakEvent(EquipmentSlot.MAINHAND); });
        }

        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);

    }
}
