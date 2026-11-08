package dev.xcolorful.cgctactical.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.xcolorful.cgctactical.client.animation.statemachine.ConsumableAnimStateContext;
import dev.xcolorful.cgctactical.client.api.resource.ClientResourceApi;
import dev.xcolorful.cgctactical.client.model.TacticalModelObject;
import dev.xcolorful.cgctactical.client.renderer.item.melee.TacticalRendererAddon;
import dev.xcolorful.cgctactical.client.resource.instance.assets.ConsumableDisplayInstance;
import dev.xcolorful.cgctactical.core.api.item.IConsumable;
import dev.xcolorful.cgctactical.core.api.item.consumable.IConsumableGetter;
import dev.xcolorful.customgun.client.animation.statemachine.LuaAnimStateMachine;
import dev.xcolorful.customgun.client.api.animation.statemachine.GunAnimationState;
import dev.xcolorful.customgun.client.api.event.render.BeforeRenderHandEvent;
import dev.xcolorful.customgun.client.api.renderer.KeepingItemRenderer;
import dev.xcolorful.customgun.client.model.bedrock.SlotModel;
import dev.xcolorful.customgun.client.renderer.item.AnimateGeoItemRenderer;
import dev.xcolorful.customgun.client.util.ClientRenderHelper;
import dev.xcolorful.customgun.client.util.ClientRenderUtils;
import dev.xcolorful.customgun.core.api.resource.ResourceTag;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConsumableItemRenderer extends AnimateGeoItemRenderer<TacticalModelObject, ConsumableAnimStateContext> {
    private static final SlotModel SLOT_CONSUMABLE_MODEL = new SlotModel();

    public ConsumableItemRenderer() {
        super();
    }

    // --------AnimateGeoItemRenderer--------

    @Override
    public void doExtraTransforms(PoseStack poseStack, TacticalModelObject modelObject, ItemStack consumableItem) {
        super.doExtraTransforms(poseStack, modelObject, consumableItem);
        TacticalRendererAddon.applyJumpingSway(modelObject, ClientRenderUtils.getRenderFrameTime());
    }

    // --------IAnimateGeoItemRenderer--------

    @Override public ConsumableAnimStateContext initContext(ItemStack consumableItem, Player player, float partialTick) {
        ConsumableAnimStateContext context = new ConsumableAnimStateContext();
        this.updateContext(context, consumableItem, player, partialTick);
        return context;
    }

    @Override public void updateContext(ConsumableAnimStateContext context, ItemStack consumableItem, Player player, float partialTick) {
        context.setCurrentItem(consumableItem);
        context.setUsing(player.isUsingItem());
        context.setUsingTick(player.getTicksUsingItem());
        context.setPartialTicks(partialTick);
    }

    // ----Getter----

    @Override public @Nullable TacticalModelObject getModel(ItemStack consumableItem) {
        @Nullable ConsumableDisplayInstance consumableDisplayInstance = ClientResourceApi.getConsumableDisplayInstance(consumableItem);
        if (consumableDisplayInstance == null) return null;

        return consumableDisplayInstance.getModelObject();
    }
    @Override public @Nullable LuaAnimStateMachine<ConsumableAnimStateContext> getStateMachine(ItemStack consumableItem) {
        @Nullable ConsumableDisplayInstance consumableDisplayInstance = ClientResourceApi.getConsumableDisplayInstance(consumableItem);
        if (consumableDisplayInstance == null) return null;

        return consumableDisplayInstance.getAnimStateMachine();
    }
    @Override public @Nullable Identifier getTextureLocation(ItemStack consumableItem) {
        @Nullable ConsumableDisplayInstance consumableDisplayInstance = ClientResourceApi.getConsumableDisplayInstance(consumableItem);
        if (consumableDisplayInstance == null) return null;

        return consumableDisplayInstance.getTextureLocation();
    }
    @Override public long getPutAwayTime(ItemStack consumableItem) {
        @Nullable IConsumable iConsumable = IConsumableGetter.fromItemStack(consumableItem);
        if (iConsumable == null) return 0;

        return (long) iConsumable.getPutAwayTime(consumableItem) * 50L;
    }

    // --------IAnimateGeoItemRendererState--------

    @Override
    public void tryExit(ItemStack consumableItem, long putAwayTime) {
        @Nullable LuaAnimStateMachine<ConsumableAnimStateContext> stateMachine = this.getStateMachine(consumableItem);
        if (stateMachine == null) return;

        stateMachine.processContextIfExist(context -> {
            context.setPutAwayTime(putAwayTime / 1000F);
            context.setCurrentItem(consumableItem);
        });

        if (!stateMachine.isInitialized()) return;

        stateMachine.trigger(GunAnimationState.INPUT_PUT_AWAY.getConstantName());
        KeepingItemRenderer.cgc$getRenderer().cgc$keep(consumableItem, putAwayTime);
        stateMachine.exit();
        stateMachine.setExitingTime(putAwayTime + 50);
    }

    // --------AnimateGeoItemRendererOperator--------

    @Override
    public void applyItemInHandCameraAnimation(BeforeRenderHandEvent event, ItemStack consumableItem, LocalPlayer player) {
        super.applyItemInHandCameraAnimation(event, consumableItem, player);

        @Nullable TacticalModelObject modelObject = this.getModel(consumableItem);
        if (modelObject == null) return;

        // 摄像机动画数据已消费完毕，清理以避免影响其他渲染
        modelObject.cleanCameraAnimationTransform();
    }

    // --------IBlockEntityWithoutLevelRenderer--------

    @Override
    public void renderByItem(@NotNull ItemStack consumableItem,
                             ItemDisplayContext transformType,
                             @NotNull PoseStack poseStack,
                             @NotNull MultiBufferSource bufferSource,
                             int light, int overlay) {
        // 第一人称就不渲染了，交给别的地方
        if (transformType.firstPerson()) return;

        @Nullable ConsumableDisplayInstance consumableDisplayInstance = ClientResourceApi.getConsumableDisplayInstance(consumableItem);
        if (consumableDisplayInstance == null) {
            poseStack.pushPose(); {
                // 没有这个 consumableDisplayLocation，渲染黑紫材质以提醒
                _renderSlotTexture(poseStack, transformType, bufferSource, light, overlay, ClientRenderUtils.getMissingTextureLocation());
            }
            poseStack.popPose();
            return;
        }

        @Nullable TacticalModelObject modelObject = consumableDisplayInstance.getModelObject();
        @Nullable var modelTextureLocation = consumableDisplayInstance.getTextureLocation();
        @Nullable var slotTextureLocation = consumableDisplayInstance.getSlotTextureLocation();

        poseStack.pushPose(); {
            if (transformType == ItemDisplayContext.GUI
                    && slotTextureLocation != null
                    && !ResourceTag.NULL_LOCATION.equals(slotTextureLocation)
            ) {
                // GUI 特殊渲染
                _renderSlotTexture(poseStack, transformType, bufferSource, light, overlay, slotTextureLocation);
            } else if (modelObject == null || modelTextureLocation == null) {
                // 没有模型或材质，渲染黑紫材质以提醒
                _renderSlotTexture(poseStack, transformType, bufferSource, light, overlay, ClientRenderUtils.getMissingTextureLocation());
            } else {
                // 非第一人称展示变换的额外基准位置偏移（方块单位），仅整体平移
                float[] displayOffset = consumableDisplayInstance.getDisplayOffset();
                poseStack.translate(displayOffset[0], displayOffset[1], displayOffset[2]);

                // 从渲染原点 (0, 24, 0) 移动到模型原点 (0, 0, 0)
                poseStack.translate(0.5, 1.5f, 0.5);
                // 基岩版模型是上下颠倒的，需要翻转过来
                ClientRenderHelper.rotate(poseStack, Axis.ZP.rotationDegrees(180f));

                RenderType renderType = ClientRenderUtils.RenderType_.entityCutout(modelTextureLocation);
                ClientRenderHelper.FirstPersonArmHelper.setFirstPersonArmCollector_(bufferSource);
                try {
                    modelObject.render(poseStack, transformType, renderType, light, overlay);
                } finally {
                    ClientRenderHelper.FirstPersonArmHelper.setFirstPersonArmCollector_(null);
                }
            }
        }
        poseStack.popPose();
    }

    private static void _renderSlotTexture(PoseStack poseStack,
                                           ItemDisplayContext transformType,
                                           MultiBufferSource bufferSource,
                                           int packedLight, int packedOverlay,
                                           Identifier texture) {
        poseStack.translate(0.5, 1.5, 0.5);
        ClientRenderHelper.rotate(poseStack, Axis.ZN.rotationDegrees(180));

        if (transformType == ItemDisplayContext.FIXED) {
            /*
            slot 贴图在 GUI 下正面朝向 +Z
            展示框的 FIXED 姿态相对 GUI 多转了 180° 的 Y，不补偿的话会从背面看到贴图，表现为水平镜像
             */
            ClientRenderHelper.rotate(poseStack, Axis.YP.rotationDegrees(180));
        }

        {
            VertexConsumer buffer = bufferSource.getBuffer(ClientRenderUtils.RenderType_.entityTranslucent(texture));
            SLOT_CONSUMABLE_MODEL.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
}
