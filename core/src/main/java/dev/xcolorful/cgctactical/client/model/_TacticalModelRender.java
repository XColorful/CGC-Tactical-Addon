package dev.xcolorful.cgctactical.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.xcolorful.customgun.client.model.bedrock.BedrockPart;
import dev.xcolorful.customgun.client.renderer.model.HandRender;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class _TacticalModelRender {

    protected static void render(TacticalModelObject _this,
                                 PoseStack matrixStack,
                                 ItemDisplayContext transformType,
                                 RenderType renderType,
                                 int light, int overlay,
                                 float red, float green, float blue, float alpha) {
        _this.super_render(matrixStack,
                transformType,
                renderType,
                light, overlay,
                red, green, blue, alpha);


        { // 渲染
            _renderHand(_this, matrixStack, transformType, renderType, light, overlay);
        }
    }

    private static void _renderHand(TacticalModelObject _this,
                                    PoseStack matrixStack,
                                    ItemDisplayContext transformType,
                                    RenderType renderType,
                                    int light, int overlay) {
        // [26.2, )
        if (true) return; // 让IDE保留下面的引用关系
        if (transformType.firstPerson() && _this.getRenderHand()) {
            _renderHandInternal(_this, matrixStack, light, HumanoidArm.LEFT);
            _renderHandInternal(_this, matrixStack, light, HumanoidArm.RIGHT);
        }
    }
    /**
     * 在 submit 阶段渲染第一人称手臂，沿手部定位组路径提交。
     */
    @ApiStatus.AvailableSince("26.2")
    private static void _renderHandInternal(TacticalModelObject _this,
                                            PoseStack matrixStack,
                                            int light,
                                            HumanoidArm hand) {
        @Nullable List<BedrockPart> handPath = hand == HumanoidArm.LEFT ? _this.getLeftHandPosPath() : _this.getRightHandPosPath();
        HandRender.renderFirstPersonArm(matrixStack, handPath, hand, light);
    }
}
