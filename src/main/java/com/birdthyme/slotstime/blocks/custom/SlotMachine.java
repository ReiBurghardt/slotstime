package com.birdthyme.slotstime.blocks.custom;

import com.birdthyme.slotstime.Config;
import com.birdthyme.slotstime.blocks.SlotsBlocks;
import com.birdthyme.slotstime.items.SlotsItems;
import com.birdthyme.slotstime.other.GamblingStat;
import com.birdthyme.slotstime.tags.SlotsTags;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.common.data.SoundDefinition;
import org.openjdk.nashorn.internal.objects.Global;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SlotMachine extends BaseEntityBlock{
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty IS_GAMBLING = BooleanProperty.create("is_gambling");

    public Player player;


    public float ironLoot = 0.1f;
    public float goldLoot = 0.05f;
    public float diamondLoot = 0.01f;
    public float netheriteLoot = 0.001f;
    public float cLoot = 0.0001f;

    public int gamblingRange = 100000;



    public float[] lootChances = {ironLoot, goldLoot, diamondLoot, netheriteLoot, cLoot};


    public SlotMachine(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER).setValue(FACING, Direction.NORTH).setValue(IS_GAMBLING, false));
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }


    public static float getDirection(BlockState pState){
        String facingValue = pState.getValue(FACING).toString();
        return switch (facingValue) {
            case "north" -> 0f;
            case "east" -> 270f;
            case "south" -> 180f;
            case "west" -> 90f;
            default -> 0f;
        };
    }

    public static boolean getHalf(BlockState pState){
        String half = pState.getValue(HALF).toString();
        if(half == "upper"){
            return true;
        }
        return false;
    }


    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        DoubleBlockHalf doubleblockhalf = pState.getValue(HALF);
        if (pFacing.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (pFacing == Direction.UP)) {
            return pFacingState.is(this) && pFacingState.getValue(HALF) != doubleblockhalf ? pState.setValue(FACING, pFacingState.getValue(FACING)) : Blocks.AIR.defaultBlockState();
        } else {
            return doubleblockhalf == DoubleBlockHalf.LOWER && pFacing == Direction.DOWN && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        }
    }


    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        return blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(pContext) ? this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(HALF, DoubleBlockHalf.LOWER) : null;
    }

    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        BlockPos blockpos = pPos.above();
        pLevel.setBlock(blockpos, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }


    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        if (pState.getValue(HALF) != DoubleBlockHalf.UPPER) {
            return super.canSurvive(pState, pLevel, pPos);
        } else {
            BlockState blockstate = pLevel.getBlockState(pPos.below());
            if (pState.getBlock() != this) return super.canSurvive(pState, pLevel, pPos); //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
            return blockstate.is(this) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER;
        }
    }


    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pLevel.isClientSide && pPlayer.isCreative()) {
            preventCreativeDropFromOtherPart(pLevel, pPos, pState, pPlayer);
        }
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    public void playerDestroy(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState, @Nullable BlockEntity pTe, ItemStack pStack) {
        super.playerDestroy(pLevel, pPlayer, pPos, Blocks.AIR.defaultBlockState(), pTe, pStack);
    }

    protected static void preventCreativeDropFromOtherPart(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        DoubleBlockHalf doubleblockhalf = pState.getValue(HALF);
        if (doubleblockhalf == DoubleBlockHalf.UPPER) {
            BlockPos blockpos = pPos.below();
            BlockState blockstate = pLevel.getBlockState(blockpos);
            if (blockstate.is(pState.getBlock()) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
                BlockState blockstate1 = Blocks.AIR.defaultBlockState();
                pLevel.setBlock(pPos, blockstate1, 18);
                pLevel.setBlock(blockpos, blockstate1, 19);
                pLevel.levelEvent(pPlayer, 2001, blockpos, Block.getId(blockstate));
            }
        }
        else if(doubleblockhalf == DoubleBlockHalf.LOWER) {
            BlockPos blockpos = pPos.above();
            BlockState blockstate = pLevel.getBlockState(blockpos);
            if (blockstate.is(pState.getBlock()) && blockstate.getValue(HALF) == DoubleBlockHalf.UPPER) {
                BlockState blockstate1 = Blocks.AIR.defaultBlockState();
                pLevel.setBlock(pPos, blockstate1, 18);
                pLevel.setBlock(blockpos, blockstate1, 19);
                pLevel.levelEvent(pPlayer, 2001, blockpos, Block.getId(blockstate));
            }
        }
    }


    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HALF, FACING, IS_GAMBLING);
    }


    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }


    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SlotMachineEntity(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack handItem = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);
        BlockState isGamblingState = pState.setValue(IS_GAMBLING, true);

        if(handItem.is(SlotsTags.Items.tag("gambling_coins")) && !pState.getValue(IS_GAMBLING) && pState.getValue(HALF) == DoubleBlockHalf.UPPER){
            if(pLevel.isClientSide){
                pLevel.playLocalSound(pPos, SoundType.AMETHYST.getHitSound(), SoundSource.BLOCKS, 1f, 1f, false);
            }
            if(!pLevel.isClientSide) {
                BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
                if(blockEntity instanceof SlotMachineEntity){
                    SlotMachineEntity slotMachineEntity = (SlotMachineEntity)blockEntity;
                    slotMachineEntity.slotNumbers = slotMath(pPlayer.getItemInHand(InteractionHand.MAIN_HAND));
                    slotMachineEntity.playerUUID = pPlayer.getUUID();
                }
                int itemCount = handItem.getCount();
                handItem.setCount(itemCount - 1);

                pLevel.setBlock(pPos, isGamblingState, 3);

                if (pState.getValue(HALF) == DoubleBlockHalf.UPPER) {
                    BlockState otherState = pLevel.getBlockState(pPos.below()).setValue(IS_GAMBLING, true);
                    pLevel.setBlock(pPos.below(), otherState, 3);
                } else {
                    BlockState otherState = pLevel.getBlockState(pPos.above()).setValue(IS_GAMBLING, true);
                    pLevel.setBlock(pPos.above(), otherState, 3);
                }
            }
            pPlayer.awardStat(GamblingStat.GAMBLED.get());
            return InteractionResult.CONSUME;

        }
        else{
            return InteractionResult.FAIL;
        }

    }


    public int[] slotMath(ItemStack coin){
        float ironLuck = Config.ironCoinLuck;
        float goldLuck = Config.goldCoinLuck;
        float diamondLuck = Config.diamondCoinLuck;
        float netheriteLuck = Config.netheriteCoinLuck;
        float cLuck = Config.cccccccCoinLuck;

        float coinLuck = 0;

        float[] lootRanges = new float[5];

        if(coin.is(SlotsItems.IRONCOIN.get())) {coinLuck = ironLuck;}
        else if (coin.is(SlotsItems.GOLDCOIN.get())) {coinLuck = goldLuck;}
        else if (coin.is(SlotsItems.DIAMONDCOIN.get())) {coinLuck = diamondLuck;}
        else if (coin.is(SlotsItems.NETHERITECOIN.get())) {coinLuck = netheriteLuck;}
        else if (coin.is(SlotsItems.CCCCCCCCOIN.get())) {coinLuck = cLuck;}
        else {coinLuck = 0;}


        for(int loop = 0; loop <= lootChances.length-1; loop++){
            lootRanges[loop] = Math.round(gamblingRange * lootLuck(lootChances[loop], coinLuck));
        }




        return randomNumbers(lootRanges);
    }


    public float lootLuck(float loot, float luck){
        float newChance = loot * luck;
        float resultChance = loot + (newChance/10);
        if(resultChance >= 1.5){
            return 0f;
        } else if (resultChance >= 1.0) {
            return 0.7f;
        }

        return resultChance;
    }


    public int[] randomNumbers(float[] lootRanges){
        Random rand = new Random();
        int[] gamblingNumbers = new int[3];

        for(int loop = 0; loop <= 2; loop++){
            int randNumber = rand.nextInt(gamblingRange);
            if (randNumber <= lootRanges[0]){
                gamblingNumbers[loop] = 1;
            }
            else if (randNumber <= lootRanges[0] + lootRanges[1] && randNumber > lootRanges[0]) {
                gamblingNumbers[loop] = 2;
            }
            else if (randNumber <= lootRanges[1] + lootRanges[2] && randNumber > lootRanges[1]) {
                gamblingNumbers[loop] = 3;
            }
            else if (randNumber <= lootRanges[2] + lootRanges[3] && randNumber > lootRanges[2]) {
                gamblingNumbers[loop] = 4;
            }
            else if (randNumber <= lootRanges[3] + lootRanges[4] && randNumber > lootRanges[3]) {
                gamblingNumbers[loop] = 5;
            }
        }

        return gamblingNumbers;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pState.getValue(IS_GAMBLING) && pState.getValue(HALF) == DoubleBlockHalf.UPPER ? createTickerHelper(pBlockEntityType, SlotsBlocks.SLOTMACHINE_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1, pBlockEntity)) : null;
    }

}
