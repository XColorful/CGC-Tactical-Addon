package dev.xcolorful.cgctactical.client.model;

import dev.xcolorful.customgun.client.api.model.bedrock.IBedrockRenderer;
import dev.xcolorful.customgun.client.model.AnimatedModelObject;
import dev.xcolorful.customgun.client.resource.assets.model.BedrockModel;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

public class TacticalModelObject extends AnimatedModelObject {

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

        return true;
    }

    // --------Getter--------

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
    public void setEntityRendering(boolean entityRendering) {
        this.entityRendering = entityRendering;
    }

    // --------ModelObject--------

    @ApiStatus.Internal protected Set<Map.Entry<String, IBedrockRenderer>> modelMap_entrySet() {
        return super.modelMap_entrySet();
    }
}
