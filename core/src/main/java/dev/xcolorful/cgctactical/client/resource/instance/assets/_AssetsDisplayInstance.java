package dev.xcolorful.cgctactical.client.resource.instance.assets;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.client.resource.assets.display._AssetsDisplay;
import dev.xcolorful.customgun.client.api.resource.ClientResourceApi;
import dev.xcolorful.customgun.client.resource.assets.animation.BedrockAnimation;
import dev.xcolorful.customgun.client.resource.assets.model.BedrockModel;
import dev.xcolorful.customgun.client.resource.assets.script.AssetsScript;
import dev.xcolorful.customgun.core.resource.instance.PojoInstance;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * 经过处理和校验的物品显示数据
 */
public abstract class _AssetsDisplayInstance<T extends _AssetsDisplay<T>> extends PojoInstance<T> {

    private @Nullable BedrockModel modelCache;
    private @Nullable BedrockAnimation animationCache;
    private @Nullable AssetsScript scriptCache;

    protected _AssetsDisplayInstance(@NotNull T pojo) {
        super(pojo);
    }

    @Override public boolean resetCache() {
        var pojo = this.getPojo();

        var modelLocation = pojo.getModelLocation();
        if (modelLocation != null) {
            this.modelCache = ClientResourceApi.getBedrockModel(modelLocation);
            if (this.modelCache == null) CgcTactical.LOGGER.debug("{}: BedrockModel {} not found", this.getClass().getSimpleName(), modelLocation);
        }

        var animationLocation = pojo.getAnimationLocation();
        if (animationLocation != null) {
            this.animationCache = ClientResourceApi.getBedrockAnimation(animationLocation);
            if (this.animationCache == null) CgcTactical.LOGGER.debug("{}: BedrockAnimation {} not found", this.getClass().getSimpleName(), animationLocation);
        }

        var scriptLocation = pojo.getScriptLocation();
        if (scriptLocation != null) {
            this.scriptCache = ClientResourceApi.getAssetsScript(scriptLocation);
            if (this.scriptCache == null) CgcTactical.LOGGER.debug("{}: AssetsScript {} not found", this.getClass().getSimpleName(), scriptLocation);
        }

        return true;
    }
    private static final int ERR_MODEL = 1;
    private static final int ERR_ANIMATION = 1 << 1;
    private static final int ERR_SCRIPT = 1 << 2;
    @Override protected boolean isPojoValid() {
        if (!super.isPojoValid()) return false;

        int errorMask = 0;
        // _AssetsDisplay
        errorMask |= this.modelCache == null ? ERR_MODEL : 0;
        errorMask |= this.animationCache == null ? ERR_ANIMATION : 0;
        errorMask |= this.scriptCache == null ? ERR_SCRIPT : 0;
        if (errorMask != 0) {
            this.logAllErrors(errorMask);
            return false;
        }

        return true;
    }
    @Override protected void logAllErrors(int errorMask) {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName()).append(": ").append(this.getPojo().getClass().getSimpleName()).append(" is invalid because:");
        if ((errorMask & ERR_MODEL) != 0) sb.append("\n\t- BedrockModel not found");
        if ((errorMask & ERR_ANIMATION) != 0) sb.append("\n\t- BedrockAnimation not found");
        if ((errorMask & ERR_SCRIPT) != 0) sb.append("\n\t- AssetsScript not found");
        CgcTactical.LOGGER.debug(sb.toString());
    }

    // --------Getter--------

    public @Nullable BedrockModel getModel() {
        return this.modelCache;
    }
    public @Nullable BedrockAnimation getAnimation() {
        return this.animationCache;
    }
    public @Nullable AssetsScript getScript() {
        return this.scriptCache;
    }
    public @Nullable ResourceLocation getTextureLocation() {
        return this.getPojo().getTextureLocation();
    }
    public @Nullable ResourceLocation getSlotTextureLocation() {
        return this.getPojo().getSlotTextureLocation();
    }
    public float[] getDisplayOffset() {
        return this.getPojo().getDisplayOffset();
    }
    public Map<String, ResourceLocation> getSounds() {
        return this.getPojo().getSounds();
    }
    public @Nullable Object getThirdPersonAnimation() {
        return this.getPojo().getThirdPersonAnimation();
    }
}
