package com.kuraion.morepaths.blocks;

import com.kuraion.morepaths.init.MorepathsModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class OtherPathBlock extends DirtPathBlock {

    private Block _pathedFrom;
    private boolean _isFallingBlock;

    public OtherPathBlock(Block pathedFrom) {
        super(getProperties(pathedFrom));
        _pathedFrom = pathedFrom;
        _isFallingBlock = pathedFrom instanceof FallingBlock;
    }

    private static Properties getProperties(Block pathedFrom) {
        BlockState state = pathedFrom.defaultBlockState();

        return Block.Properties.of().mapColor(pathedFrom.defaultMapColor()).strength(pathedFrom.defaultDestroyTime()).sound(pathedFrom.getSoundType(state));
    }

    @Override
    public BlockState updateShape( BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn,
                                            BlockPos currentPos, BlockPos facingPos) {
        if (checkIfFalling(worldIn, currentPos)) {
            worldIn.scheduleTick(currentPos, this, 1);
        }

        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        worldIn.setBlockAndUpdate(pos, pushEntitiesUp(state, _pathedFrom.defaultBlockState(), worldIn, pos));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return !this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos())
                ? Block.pushEntitiesUp(this.defaultBlockState(), _pathedFrom.defaultBlockState(),
                context.getLevel(), context.getClickedPos())
                : super.getStateForPlacement(context);
    }

    private boolean checkIfFalling(LevelAccessor worldIn, BlockPos currentPos) {
        if (!_isFallingBlock)
            return false;

        return isAir(worldIn.getBlockState(currentPos.below())) || FallingBlock.isFree(worldIn.getBlockState(currentPos.below())) && currentPos.getY() >= -64;
    }

    @SubscribeEvent()
    public static void doPlayerHarvestCheck(PlayerEvent.HarvestCheck harvestEvent) {
        Player p = harvestEvent.getEntity();

        BlockState block = harvestEvent.getTargetBlock();
        Block snowPath = MorepathsModBlocks.SNOW_PATH.get();

        if (block.getBlock() != snowPath)
            return;

        ItemStack held = p.getItemInHand(InteractionHand.MAIN_HAND);

        boolean isShovel = held.canPerformAction(ToolActions.SHOVEL_FLATTEN);

        if (!isShovel)
            return;

        harvestEvent.setCanHarvest(true);
    }

}