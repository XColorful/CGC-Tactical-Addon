package dev.xcolorful.cgctactical.client.api.minecraft.input;

import dev.xcolorful.cgctactical.core.api.minecraft.input.CustomInputKeyTag;
import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.customgun.client.api.minecraft.input.ICustomInputKey;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public enum CustomInputKey implements ICustomInputKey {
    // combatant
    PRIMARY_ACTION(CustomInputKeyTag.PRIMARY_ACTION),
    SECONDARY_ACTION(CustomInputKeyTag.SECONDARY_ACTION);

    public final String tagName;
    public final String categoryName;
    public final String registryName;
    public final ResourceLocation registryLocation;
    public final Component categoryLang;
    CustomInputKey(String name) {
        this(CustomInputKeyTag.PREFIX, name);
    }
    CustomInputKey(String prefix, String name) {
        this.tagName = name;
        this.categoryName = name;
        this.registryLocation = CustomGun.getMcRegistry().createResourceLocation(prefix + name);
        this.registryName = registryLocation.toString();
        this.categoryLang = Component.translatable(this.registryLocation.getPath());
    }
    @Override public String getTagName() {
        return this.tagName;
    }
    @Override public String getCategoryName() {
        return this.categoryName;
    }
    @Override public String getRegistryName() {
        return this.registryName;
    }
    @Override public ResourceLocation getRegistryLocation() {
        return this.registryLocation;
    }

    @Override
    public Component getCategoryLang() {
        return this.categoryLang;
    }

    private static final Map<String, ICustomInputKey> INPUT_KEYS = new HashMap<>();
    @ApiStatus.Internal
    public static void registerInputKey(ICustomInputKey key) {
        INPUT_KEYS.put(key.getTagName(), key);
        INPUT_KEYS.put(key.getCategoryName(), key);
        INPUT_KEYS.put(key.getRegistryName(), key);
    }

    static {
        for (ICustomInputKey key : values()) {
            registerInputKey(key);
        }
    }

    public static @Nullable ICustomInputKey fromString(String name) {
        return name != null ? INPUT_KEYS.get(name) : null;
    }

    @Override
    public String toString() {
        return this.categoryName;
    }
}
