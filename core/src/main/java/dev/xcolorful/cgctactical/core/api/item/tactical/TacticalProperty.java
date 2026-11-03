package dev.xcolorful.cgctactical.core.api.item.tactical;

import dev.xcolorful.customgun.core.api.resource.ResourceTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public enum TacticalProperty implements ResourceTag {
    // ITacticalDataAccess
    COOLDOWN_LOCATION(TacticalPropertyTag.COOLDOWN_LOCATION,
            ITacticalDataAccess::getCooldownLocation,
            null),
    USE_DURATION_LIMIT(TacticalPropertyTag.USE_DURATION_LIMIT,
            ITacticalDataAccess::getUseDurationLimit,
            null),
    USE_COOLDOWN(TacticalPropertyTag.USE_COOLDOWN,
            ITacticalDataAccess::getUseCooldown,
            null),
    DRAW_TIME(TacticalPropertyTag.DRAW_TIME,
            ITacticalDataAccess::getDrawTime,
            null),
    PUT_AWAY_TIME(TacticalPropertyTag.PUT_AWAY_TIME,
            ITacticalDataAccess::getPutAwayTime,
            null);

    public final String propertyName;
    private final BiFunction<ITacticalDataAccess, ItemStack, ?> getter;
    private final TriConsumer<ITacticalDataAccess, ItemStack, ?> setter;
    <T> TacticalProperty(String name, @Nullable BiFunction<ITacticalDataAccess, ItemStack, T> getter, @Nullable TriConsumer<ITacticalDataAccess, ItemStack, T> setter) {
        this.propertyName = name;
        this.getter = getter;
        this.setter = setter;
    }

    @Override public String getTagName() {
        return this.propertyName;
    }

    private static final Map<String, TacticalProperty> PROPERTY_TYPE = new HashMap<>();

    static {
        for (TacticalProperty property : values()) {
            PROPERTY_TYPE.put(property.propertyName, property);
        }
    }

    public static @Nullable TacticalProperty fromString(String name) {
        return name != null ? PROPERTY_TYPE.get(name) : null;
    }

    @Override
    public String toString() {
        return this.propertyName;
    }

    /**
     * {@link _ITacticalPropertyAccess}
     */
    @SuppressWarnings("unchecked")
    public <T> T get(ITacticalDataAccess access, ItemStack stack) {
        if (this.getter == null) {
            throw new UnsupportedOperationException("Property '" + this.propertyName + "' does not support read operations.");
        }
        return (T) this.getter.apply(access, stack);
    }
    /**
     * 设置战术物品属性
     * <p>
     * <b>注意：</b>若通过脚本引擎间接调用此方法，请务必保证传递的 {@code value} 类型与该属性期望的 Java 类型完全一致
     */
    @SuppressWarnings("unchecked")
    public <T> void set(ITacticalDataAccess access, ItemStack stack, T value) {
        if (this.setter == null) {
            throw new UnsupportedOperationException("Property '" + this.propertyName + "' does not support write operations.");
        }
        ((TriConsumer<ITacticalDataAccess, ItemStack, T>) this.setter).accept(access, stack, value);
    }
}
