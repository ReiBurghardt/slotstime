package com.birdthyme.slotstime.blocks.custom;

import com.birdthyme.slotstime.blocks.SlotsBlocks;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

public class SlotMachineRenderer implements BlockEntityRenderer<SlotMachineEntity> {
//THANKS Tutorials-By-Kaupenjoe Forge-Tutorial-1.20.X
    public SlotMachineRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(SlotMachineEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        BlockState blockState = pBlockEntity.getBlockState();
        boolean isGambling = blockState.getValue(SlotMachine.IS_GAMBLING);
        if(SlotMachine.getHalf(blockState) && isGambling){
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

            if (pBlockEntity.ItemList.isEmpty()) {
                pBlockEntity.ItemList.add(new ItemStack(Items.COAL));
                pBlockEntity.ItemList.add(new ItemStack(Items.AIR));
                pBlockEntity.ItemList.add(new ItemStack(Items.AIR));
            }

            ItemStack itemStack0 = pBlockEntity.ItemList.get(0);
            ItemStack itemStack1 = pBlockEntity.ItemList.get(1);
            ItemStack itemStack2 = pBlockEntity.ItemList.get(2);



            pPoseStack.pushPose();
            pPoseStack.translate(0.5f, 0.2f, 0.5f);
            pPoseStack.scale(0.125f, 0.125f, 0.125f);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(SlotMachine.getDirection(blockState)));
//2
            itemRenderer.renderStatic(itemStack1, ItemDisplayContext.FIXED, 14680272,
                    OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, pBlockEntity.getLevel(), 1);
//1
            pPoseStack.translate(-1.5f, 0.0f, 0f);
            itemRenderer.renderStatic(itemStack0, ItemDisplayContext.FIXED, 14680272,
                    OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, pBlockEntity.getLevel(), 1);
//3
            pPoseStack.translate(3f, 0.0f, 0f);
            itemRenderer.renderStatic(itemStack2, ItemDisplayContext.FIXED, 14680272,
                    OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, pBlockEntity.getLevel(), 1);
            pPoseStack.popPose();
        }




    }

}
