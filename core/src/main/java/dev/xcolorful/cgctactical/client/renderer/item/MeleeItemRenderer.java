package dev.xcolorful.cgctactical.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.xcolorful.cgctactical.client.animation.statemachine.MeleeAnimStateContext;
import dev.xcolorful.cgctactical.client.api.resource.ClientResourceApi;
import dev.xcolorful.cgctactical.client.model.TacticalModelObject;
import dev.xcolorful.cgctactical.client.renderer.item.melee.TacticalRendererAddon;
import dev.xcolorful.cgctactical.client.resource.instance.assets.MeleeDisplayInstance;
import dev.xcolorful.cgctactical.core.api.item.IMelee;
import dev.xcolorful.cgctactical.core.api.item.melee.IMeleeGetter;
import dev.xcolorful.customgun.client.animation.statemachine.LuaAnimStateMachine;
import dev.xcolorful.customgun.client.api.animation.statemachine.GunAnimationState;
import dev.xcolorful.customgun.client.api.event.IRenderHandEvent;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MeleeItemRenderer extends AnimateGeoItemRenderer<TacticalModelObject, MeleeAnimStateContext> {
    private static final SlotModel SLOT_MELEE_MODEL = new SlotModel();

    public MeleeItemRenderer() {
        super();
    }

    // --------AnimateGeoItemRenderer--------

    @Override
    public void doExtraTransforms(PoseStack poseStack, TacticalModelObject modelObject, ItemStack meleeItem) {
        super.doExtraTransforms(poseStack, modelObject, meleeItem);
        TacticalRendererAddon.applyJumpingSway(modelObject, ClientRenderUtils.getRenderFrameTime());
    }

    // --------IAnimateGeoItemRenderer--------

    @Override public MeleeAnimStateContext initContext(ItemStack meleeItem, Player player, float partialTick) {
        MeleeAnimStateContext context = new MeleeAnimStateContext();
        this.updateContext(context, meleeItem, player, partialTick);
        return context;
    }

    @Override public void updateContext(MeleeAnimStateContext context, ItemStack meleeItem, Player player, float partialTick) {
        context.setPartialTicks(partialTick);
        context.setCurrentMeleeItem(meleeItem);
    }

    // ----Getter----

    @Override public @Nullable TacticalModelObject getModel(ItemStack meleeItem) {
        @Nullable MeleeDisplayInstance meleeDisplayInstance = ClientResourceApi.getMeleeDisplayInstance(meleeItem);
        if (meleeDisplayInstance == null) return null;

        return meleeDisplayInstance.getModelObject();
    }
    @Override public @Nullable LuaAnimStateMachine<MeleeAnimStateContext> getStateMachine(ItemStack meleeItem) {
        @Nullable MeleeDisplayInstance meleeDisplayInstance = ClientResourceApi.getMeleeDisplayInstance(meleeItem);
        if (meleeDisplayInstance == null) return null;

        return meleeDisplayInstance.getAnimStateMachine();
    }
    @Override public @Nullable ResourceLocation getTextureLocation(ItemStack meleeItem) {
        @Nullable MeleeDisplayInstance meleeDisplayInstance = ClientResourceApi.getMeleeDisplayInstance(meleeItem);
        if (meleeDisplayInstance == null) return null;

        return meleeDisplayInstance.getTextureLocation();
    }
    @Override public long getPutAwayTime(ItemStack meleeItem) {
        @Nullable IMelee iMelee = IMeleeGetter.fromItemStack(meleeItem);
        if (iMelee == null) return 0;

        return (long) iMelee.getPutAwayTime(meleeItem) * 50L;
    }

    // --------IAnimateGeoItemRendererState--------

    @Override
    public void tryExit(ItemStack meleeItem, long putAwayTime) {
        @Nullable LuaAnimStateMachine<MeleeAnimStateContext> stateMachine = this.getStateMachine(meleeItem);
        if (stateMachine == null) return;

        stateMachine.processContextIfExist(context -> {
            context.setPutAwayTime(putAwayTime / 1000F);
            context.setCurrentMeleeItem(meleeItem);
        });

        if (!stateMachine.isInitialized()) return;

        stateMachine.trigger(GunAnimationState.INPUT_PUT_AWAY.getConstantName());
        KeepingItemRenderer.cgc$getRenderer().cgc$keep(meleeItem, putAwayTime);
        stateMachine.exit();
        stateMachine.setExitingTime(putAwayTime + 50);
    }

    // --------AnimateGeoItemRendererOperator--------

    @Override
    public void applyItemInHandCameraAnimation(BeforeRenderHandEvent event, ItemStack meleeItem, LocalPlayer player) {
        super.applyItemInHandCameraAnimation(event, meleeItem, player);

        @Nullable TacticalModelObject modelObject = this.getModel(meleeItem);
        if (modelObject == null) return;

        // 摄像机动画数据已消费完毕，清理以避免影响其他渲染
        modelObject.cleanCameraAnimationTransform();
    }

    @Override
    public void renderFirstPerson(PoseStack poseStack,
                                  IRenderHandEvent event,
                                  ItemDisplayContext ctx,
                                  int light, float partialTick,
                                  LocalPlayer player, ItemStack meleeItem) {
        @Nullable TacticalModelObject modelObject = this.getModel(meleeItem);
        if (modelObject != null) modelObject.setEffectVisible(true);
        try {
            super.renderFirstPerson(poseStack, event, ctx, light, partialTick, player, meleeItem);
        } finally {
            if (modelObject != null) modelObject.setEffectVisible(false);
        }
    }

    // --------IBlockEntityWithoutLevelRenderer--------

    @Override
    public void renderByItem(@NotNull ItemStack meleeItem,
                             ItemDisplayContext transformType,
                             @NotNull PoseStack poseStack,
                             @NotNull MultiBufferSource bufferSource,
                             int light, int overlay) {
        // 第一人称就不渲染了，交给别的地方
        if (transformType.firstPerson()) return;

        @Nullable MeleeDisplayInstance meleeDisplayInstance = ClientResourceApi.getMeleeDisplayInstance(meleeItem);
        if (meleeDisplayInstance == null) {
            poseStack.pushPose(); {
                // 没有这个 meleeDisplayLocation，渲染黑紫材质以提醒
                _renderSlotTexture(poseStack, transformType, bufferSource, light, overlay, ClientRenderUtils.getMissingTextureLocation());
            }
            poseStack.popPose();
            return;
        }

        @Nullable TacticalModelObject modelObject = meleeDisplayInstance.getModelObject();
        @Nullable var modelTextureLocation = meleeDisplayInstance.getTextureLocation();
        @Nullable var slotTextureLocation = meleeDisplayInstance.getSlotTextureLocation();

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
                float[] displayOffset = meleeDisplayInstance.getDisplayOffset();
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
                                           ResourceLocation texture) {
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
            SLOT_MELEE_MODEL.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
}
