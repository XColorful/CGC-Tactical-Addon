package dev.xcolorful.cgctactical.core.api.minecraft.item;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.customgun.core.api.resource.ResourceTag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public enum ItemType implements ResourceTag.RegistryTag {
    // Item
    MELEE(ItemTypeTag.MELEE, null),
    THROWABLE(ItemTypeTag.THROWABLE, null),
    CONSUMABLE(ItemTypeTag.CONSUMABLE, null),
    ;

    public final String typeName;
    public final @Nullable String typeNameOld;
    public final String registryName;
    public final ResourceLocation registryLocation;
    ItemType(String name, @Nullable String nameOld) {
        this.typeName = name;
        this.typeNameOld = nameOld;
        this.registryName = String.format("%s:%s", CgcTactical.MOD_ID, this.typeName);
        this.registryLocation = CustomGun.getMcRegistry().createResourceLocation(this.registryName);
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

    private static final Map<String, ItemType> ITEM_TYPES = new HashMap<>();

    static {
        for (ItemType type : values()) {
            ITEM_TYPES.put(type.typeName, type);
            if (type.typeNameOld != null) ITEM_TYPES.put(type.typeNameOld, type);
            ITEM_TYPES.put(type.registryName, type);
        }
    }

    public static @Nullable ItemType fromString(String name) {
        return name != null ? ITEM_TYPES.get(name) : null;
    }

    @Override
    public String toString() {
        return this.typeName;
    }
}
