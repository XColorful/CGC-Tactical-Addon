package dev.xcolorful.cgctactical.core.api.item.consumable;

import dev.xcolorful.customgun.core.api.resource.ResourceTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public enum ConsumableProperty implements ResourceTag {
    // IConsumableDataAccess
    CONSUMABLE_LOCATION(ConsumablePropertyTag.CONSUMABLE_LOCATION,
            IConsumableDataAccess::getConsumableLocation,
            IConsumableDataAccess::setConsumableLocation),
    CONSUMABLE_DISPLAY_LOCATION(ConsumablePropertyTag.CONSUMABLE_DISPLAY_LOCATION,
            IConsumableDataAccess::getConsumableDisplayLocation,
            IConsumableDataAccess::setConsumableDisplayLocation),

    // IConsumableStateAccess
    TOOLTIP_MASK(ConsumablePropertyTag.TOOLTIP_MASK,
            IConsumableDataAccess::getTooltipMask,
            IConsumableDataAccess::setTooltipMask);

    public final String propertyName;
    private final BiFunction<IConsumableDataAccess, ItemStack, ?> getter;
    private final TriConsumer<IConsumableDataAccess, ItemStack, ?> setter;
    <T> ConsumableProperty(String name, @Nullable BiFunction<IConsumableDataAccess, ItemStack, T> getter, @Nullable TriConsumer<IConsumableDataAccess, ItemStack, T> setter) {
        this.propertyName = name;
        this.getter = getter;
        this.setter = setter;
    }

    @Override public String getTagName() {
        return this.propertyName;
    }

    private static final Map<String, ConsumableProperty> PROPERTY_TYPE = new HashMap<>();

    static {
        for (ConsumableProperty property : values()) {
            PROPERTY_TYPE.put(property.propertyName, property);
        }
    }

    public static @Nullable ConsumableProperty fromString(String name) {
        return name != null ? PROPERTY_TYPE.get(name) : null;
    }

    @Override
    public String toString() {
        return this.propertyName;
    }

    /**
     * {@link _IConsumablePropertyAccess}
     */
    @SuppressWarnings("unchecked")
    public <T> T get(IConsumableDataAccess access, ItemStack stack) {
        if (this.getter == null) {
            throw new UnsupportedOperationException("Property '" + this.propertyName + "' does not support read operations.");
        }
        return (T) this.getter.apply(access, stack);
    }
    /**
     * 设置消耗品属性
     * <p>
     * <b>注意：</b>若通过脚本引擎间接调用此方法，请务必保证传递的 {@code value} 类型与该属性期望的 Java 类型完全一致
     */
    @SuppressWarnings("unchecked")
    public <T> void set(IConsumableDataAccess access, ItemStack stack, T value) {
        if (this.setter == null) {
            throw new UnsupportedOperationException("Property '" + this.propertyName + "' does not support write operations.");
        }
        ((TriConsumer<IConsumableDataAccess, ItemStack, T>) this.setter).accept(access, stack, value);
    }
}
