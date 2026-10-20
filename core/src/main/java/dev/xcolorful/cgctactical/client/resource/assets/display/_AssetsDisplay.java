package dev.xcolorful.cgctactical.client.resource.assets.display;

import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.customgun.client.api.resource.assets.AssetsFolderType;
import dev.xcolorful.customgun.core.api.minecraft.IMcRegistry;
import dev.xcolorful.customgun.core.api.resource.FileExtensionType;
import dev.xcolorful.customgun.core.api.resource.ResourceTag;
import dev.xcolorful.customgun.core.resource.ResourcePojo;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class _AssetsDisplay<T extends _AssetsDisplay<T>> extends ResourcePojo<T> {

    private @Nullable ResourceLocation modelLocation;
//    private @Nullable Transformation itemTransforms; // TODO
    private @Nullable ResourceLocation textureLocation;
    private @Nullable ResourceLocation slotTextureLocation;

    private @Nullable ResourceLocation animationLocation;
    private @Nullable ResourceLocation scriptLocation;

    private float[] displayOffset;
    private Map<String, ResourceLocation> sounds;
    private Object thirdPersonAnimation; // TODO

    @Override
    protected void validatePojo() {
        if (ENABLE_BACK_COMPATIBILITY) this.applyBackCompatibility();

        boolean n1 = (this.modelLocation == null | this.animationLocation == null | this.scriptLocation == null | this.textureLocation == null);
        boolean n2 = (this.displayOffset == null | this.sounds == null);
        if (n1 | n2) {
            this.setValid(false);
            return;
        }

        this.setValid(true);
    }

    // --------Getter & Setter--------

    public final @Nullable ResourceLocation getModelLocation() {
        return modelLocation;
    }
    public final @Nullable ResourceLocation getTextureLocation() {
        return textureLocation;
    }
    public final @Nullable ResourceLocation getSlotTextureLocation() {
        return slotTextureLocation;
    }
    public final @Nullable ResourceLocation getAnimationLocation() {
        return animationLocation;
    }
    public final @Nullable ResourceLocation getScriptLocation() {
        return scriptLocation;
    }
    public final float[] getDisplayOffset() {
        return displayOffset;
    }
    public final Map<String, ResourceLocation> getSounds() {
        return sounds;
    }
    public final @Nullable Object getThirdPersonAnimation() {
        return thirdPersonAnimation;
    }

    public final void setModelLocation(ResourceLocation modelLocation) {
        this.modelLocation = modelLocation;
    }
    public final void setTextureLocation(ResourceLocation textureLocation) {
        this.textureLocation = textureLocation;
    }
    public final void setSlotTextureLocation(ResourceLocation slotTextureLocation) {
        this.slotTextureLocation = slotTextureLocation;
    }
    public final void setAnimationLocation(ResourceLocation animationLocation) {
        this.animationLocation = animationLocation;
    }
    public final void setScriptLocation(ResourceLocation scriptLocation) {
        this.scriptLocation = scriptLocation;
    }
    public final void setDisplayOffset(float[] displayOffset) {
        this.displayOffset = displayOffset;
    }
    public final void setSounds(Map<String, ResourceLocation> sounds) {
        this.sounds = sounds;
    }

    // --------Back Compatibility--------

    @SuppressWarnings("unchecked")
    @Override
    public T applyBackCompatibility() {
        this.textureLocation = applyTextureLocationBackCompatibility(this.textureLocation);
        this.slotTextureLocation = applyTextureLocationBackCompatibility(this.slotTextureLocation);
        this.slotTextureLocation = this.slotTextureLocation == null ? ResourceTag.NULL_LOCATION : this.slotTextureLocation;

        this.displayOffset = this.displayOffset == null ? new float[]{0f, 0f, 0f} : this.displayOffset;
        this.sounds = this.sounds == null ? new HashMap<>() : this.sounds;
        return (T) this;
    }

    private static final IMcRegistry mcRegistry = CustomGun.getMcRegistry();
    private static final String textureLocationPrefix = AssetsFolderType.TEXTURES.getFolderPathPrefix();
    private static final String textureLocationSuffix = FileExtensionType.PNG.getExtensionNameWithDot();
    public static @Nullable ResourceLocation applyTextureLocationBackCompatibility(@Nullable ResourceLocation textureLocation) {
        if (textureLocation == null) return null;

        String rlPath = textureLocation.getPath();

        if (!rlPath.startsWith(textureLocationPrefix)) {
            rlPath = textureLocationPrefix + rlPath;
            if (!rlPath.endsWith(textureLocationSuffix)) {
                rlPath = rlPath + textureLocationSuffix;
            }
            return mcRegistry.createResourceLocation(textureLocation.getNamespace() + ":" + rlPath);
        } else {
            // 如果美术知道要加前缀，就不检查 (可自定其他后缀?)
        }

        return textureLocation;
    }
}
