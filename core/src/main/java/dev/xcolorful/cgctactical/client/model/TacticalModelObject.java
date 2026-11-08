package dev.xcolorful.cgctactical.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.xcolorful.customgun.client.api.model.bedrock.IBedrockRenderer;
import dev.xcolorful.customgun.client.model.AnimatedModelObject;
import dev.xcolorful.customgun.client.model.bedrock.BedrockPart;
import dev.xcolorful.customgun.client.resource.assets.model.BedrockModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TacticalModelObject extends AnimatedModelObject {

    /**
     * 左手定位组路径（26.2 延迟渲染下手臂在 submit 阶段渲染）
     */
    @ApiStatus.AvailableSince("26.2")
    @Nullable List<BedrockPart> leftHandPosPath;
    /**
     * 右手定位组路径
     */
    @ApiStatus.AvailableSince("26.2")
    @Nullable List<BedrockPart> rightHandPosPath;

    /**
     * 第一人称特效是否可见
     */
    private boolean effectVisible = false;
    /**
     * 是否正在渲染实体
     */
    private boolean entityRendering = false;

    protected
    TacticalModelObject(@NotNull BedrockModel pojo) {
        super(pojo);
    }

    public static @Nullable TacticalModelObject fromPojo(BedrockModel pojo) {
        if (pojo == null) return null;
        TacticalModelObject instance = new TacticalModelObject(pojo);
        if (!instance.isPojoValid()) return null;
        else return instance;
    }

    @Override public boolean resetCache() {
        if (!super.resetCache()) return false;

        // functional
        _TacticalLoader.constructFunctionalRenderer(this);

        // 缓存其他定位组
        _TacticalLoader.constructOtherPath(this);

        return true;
    }

    // --------IModelObjectRender--------

    @Override
    public void render(PoseStack matrixStack,
                       ItemDisplayContext transformType,
                       RenderType renderType,
                       int light, int overlay,
                       float red, float green, float blue, float alpha) {
        _TacticalModelRender.render(this,
                matrixStack,
                transformType,
                renderType,
                light, overlay,
                red, green, blue, alpha);
    }
    @ApiStatus.Internal protected void super_render(PoseStack matrixStack,
                                                    ItemDisplayContext transformType,
                                                    RenderType renderType,
                                                    int light, int overlay,
                                                    float red, float green, float blue, float alpha) {
        super.render(matrixStack, transformType, renderType, light, overlay, red, green, blue, alpha);
    }

    protected @Nullable List<BedrockPart> getPath(@Nullable IBedrockRenderer renderer) {
        return super.getPath(renderer);
    }

    // --------Getter--------

    @ApiStatus.AvailableSince("26.2")
    public @Nullable List<BedrockPart> getLeftHandPosPath() {
        return this.leftHandPosPath;
    }
    @ApiStatus.AvailableSince("26.2")
    public @Nullable List<BedrockPart> getRightHandPosPath() {
        return this.rightHandPosPath;
    }
    public boolean getEffectVisible() {
        return this.effectVisible;
    }
    public boolean getEntityRendering() {
        return this.entityRendering;
    }

    // --------Setter--------

    public void setEffectVisible(boolean effectVisible) {
        this.effectVisible = effectVisible;
    }
    /**
     * 是否正在渲染实体
     * <br>
     * 26.2 起模型在 flush 阶段才读取该状态，须在 submit 阶段设好且不能随即还原
     */
    public void setEntityRendering(boolean entityRendering) {
        this.entityRendering = entityRendering;
    }

    // --------ModelObject--------

    @ApiStatus.Internal protected @Nullable IBedrockRenderer modelMap_get(String nodeName) {
        return super.modelMap_get(nodeName);
    }
    @ApiStatus.Internal protected Set<Map.Entry<String, IBedrockRenderer>> modelMap_entrySet() {
        return super.modelMap_entrySet();
    }
}
