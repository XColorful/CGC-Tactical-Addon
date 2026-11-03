package dev.xcolorful.cgctactical.core.api.minecraft.tab;

import dev.xcolorful.cgctactical.core.init.registry.ModCreativeTabs;
import dev.xcolorful.customgun.core.api.init.registry.IRegistryObject;
import dev.xcolorful.customgun.core.api.resource.ResourceTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import static dev.xcolorful.cgctactical.core.init.registry.ModCreativeTabs.*;

/**
 * 需要在 {@link ModCreativeTabs} 之后访问
 * 弱枚举类型，即仍然使用 {@link ResourceLocation} 以及 {@link TabGroup#getRegistryLocation()}
 */
public enum TabGroup implements ResourceTag.RegistryTag {
    MELEE(MELEE_TAB),
    THROWABLE(THROWABLE_TAB),
    CONSUMABLE(CONSUMABLE_TAB);

    public final String typeName;
    public final String registryName;
    public final ResourceLocation registryLocation;
    public final IRegistryObject<CreativeModeTab> registryObject;
    TabGroup(IRegistryObject<CreativeModeTab> tabRegistryObject) {
        this.registryObject = tabRegistryObject;
        this.registryLocation = this.registryObject.getRegistryName();
        this.typeName = this.registryLocation.getPath();
        this.registryName = this.registryLocation.toString();
    }
    @Override public String getTagName() {
        return this.typeName;
    }
    @Override public String getRegistryName() {
        return this.registryName;
    }
    @Override public ResourceLocation getRegistryLocation() {
        return this.registryLocation;
    }

    private static final Map<String, TabGroup> TAB_GROUPS = new HashMap<>();
    private static final Map<ResourceLocation, TabGroup> TAB_GROUP_LOCATIONS = new HashMap<>();

    static {
        for (TabGroup group : values()) {
            TAB_GROUPS.put(group.typeName, group);
            TAB_GROUPS.put(group.registryName, group);
            TAB_GROUP_LOCATIONS.put(group.registryLocation, group);
        }
    }

    public static @Nullable TabGroup fromString(String name) {
        return name != null ? TAB_GROUPS.get(name) : null;
    }
    public static @Nullable TabGroup fromLocation(ResourceLocation location) {
        return location != null ? TAB_GROUP_LOCATIONS.get(location) : null;
    }

    @Override
    public String toString() {
        return this.registryName;
    }
}
