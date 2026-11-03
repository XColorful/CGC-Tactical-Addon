package dev.xcolorful.cgctactical.core.api.item.melee;

import dev.xcolorful.customgun.core.api.resource.ResourceTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public enum MeleeProperty implements ResourceTag {
    // IMeleeDataAccess
    MANAGER_GROUP(MeleePropertyTag.MANAGER_GROUP,
            IMeleeDataAccess::getManagerGroupTag,
            IMeleeDataAccess::setManagerGroupTag),
    MELEE_LOCATION(MeleePropertyTag.MELEE_LOCATION,
            IMeleeDataAccess::getMeleeLocation,
            IMeleeDataAccess::setMeleeLocation),
    MELEE_DISPLAY_LOCATION(MeleePropertyTag.MELEE_DISPLAY_LOCATION,
            IMeleeDataAccess::getMeleeDisplayLocation,
            IMeleeDataAccess::setMeleeDisplayLocation);

    public final String propertyName;
    private final BiFunction<IMeleeDataAccess, ItemStack, ?> getter;
    private final TriConsumer<IMeleeDataAccess, ItemStack, ?> setter;
    <T> MeleeProperty(String name, @Nullable BiFunction<IMeleeDataAccess, ItemStack, T> getter, @Nullable TriConsumer<IMeleeDataAccess, ItemStack, T> setter) {
        this.propertyName = name;
        this.getter = getter;
        this.setter = setter;
    }

    @Override public String getTagName() {
        return this.propertyName;
    }

    private static final Map<String, MeleeProperty> PROPERTY_TYPE = new HashMap<>();

    static {
        for (MeleeProperty property : values()) {
            PROPERTY_TYPE.put(property.propertyName, property);
        }
    }

    public static @Nullable MeleeProperty fromString(String name) {
        return name != null ? PROPERTY_TYPE.get(name) : null;
    }

    @Override
    public String toString() {
        return this.propertyName;
    }

    /**
     * {@link _IMeleePropertyAccess}
     */
    @SuppressWarnings("unchecked")
    public <T> T get(IMeleeDataAccess access, ItemStack stack) {
        if (this.getter == null) {
            throw new UnsupportedOperationException("Property '" + this.propertyName + "' does not support read operations.");
        }
        return (T) this.getter.apply(access, stack);
    }
    /**
     * 设置近战属性
     * <p>
     * <b>注意：</b>若通过脚本引擎间接调用此方法，请务必保证传递的 {@code value} 类型与该属性期望的 Java 类型完全一致
     */
    @SuppressWarnings("unchecked")
    public <T> void set(IMeleeDataAccess access, ItemStack stack, T value) {
        if (this.setter == null) {
            throw new UnsupportedOperationException("Property '" + this.propertyName + "' does not support write operations.");
        }
        ((TriConsumer<IMeleeDataAccess, ItemStack, T>) this.setter).accept(access, stack, value);
    }
}
