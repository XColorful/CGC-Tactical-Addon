package dev.xcolorful.cgctactical.core.api.item.throwable;

import dev.xcolorful.customgun.core.api.resource.ResourceTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public enum ThrowableProperty implements ResourceTag {
    // IThrowableDataAccess
    THROWABLE_LOCATION(ThrowablePropertyTag.THROWABLE_LOCATION,
            IThrowableDataAccess::getThrowableLocation,
            IThrowableDataAccess::setThrowableLocation),
    THROWABLE_DISPLAY_LOCATION(ThrowablePropertyTag.THROWABLE_DISPLAY_LOCATION,
            IThrowableDataAccess::getThrowableDisplayLocation,
            IThrowableDataAccess::setThrowableDisplayLocation),

    // IThrowableStateAccess
    TOOLTIP_MASK(ThrowablePropertyTag.TOOLTIP_MASK,
            IThrowableDataAccess::getTooltipMask,
            IThrowableDataAccess::setTooltipMask);

    public final String propertyName;
    private final BiFunction<IThrowableDataAccess, ItemStack, ?> getter;
    private final TriConsumer<IThrowableDataAccess, ItemStack, ?> setter;
    <T> ThrowableProperty(String name, @Nullable BiFunction<IThrowableDataAccess, ItemStack, T> getter, @Nullable TriConsumer<IThrowableDataAccess, ItemStack, T> setter) {
        this.propertyName = name;
        this.getter = getter;
        this.setter = setter;
    }

    @Override public String getTagName() {
        return this.propertyName;
    }

    private static final Map<String, ThrowableProperty> PROPERTY_TYPE = new HashMap<>();

    static {
        for (ThrowableProperty property : values()) {
            PROPERTY_TYPE.put(property.propertyName, property);
        }
    }

    public static @Nullable ThrowableProperty fromString(String name) {
        return name != null ? PROPERTY_TYPE.get(name) : null;
    }

    @Override
    public String toString() {
        return this.propertyName;
    }

    /**
     * {@link _IThrowablePropertyAccess}
     */
    @SuppressWarnings("unchecked")
    public <T> T get(IThrowableDataAccess access, ItemStack stack) {
        if (this.getter == null) {
            throw new UnsupportedOperationException("Property '" + this.propertyName + "' does not support read operations.");
        }
        return (T) this.getter.apply(access, stack);
    }
    /**
     * 设置投掷物属性
     * <p>
     * <b>注意：</b>若通过脚本引擎间接调用此方法，请务必保证传递的 {@code value} 类型与该属性期望的 Java 类型完全一致
     */
    @SuppressWarnings("unchecked")
    public <T> void set(IThrowableDataAccess access, ItemStack stack, T value) {
        if (this.setter == null) {
            throw new UnsupportedOperationException("Property '" + this.propertyName + "' does not support write operations.");
        }
        ((TriConsumer<IThrowableDataAccess, ItemStack, T>) this.setter).accept(access, stack, value);
    }
}
