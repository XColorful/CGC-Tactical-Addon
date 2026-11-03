package dev.xcolorful.cgctactical.core.api.item.consumable;

import dev.xcolorful.cgctactical.core.api.item.tactical._ITacticalPropertyAccess;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * 专供第三方脚本（如 KubeJS）调用的属性访问接口，模组内部严禁使用 (会循环调用)
 * <p>
 * 推荐脚本先缓存 {@link IConsumableGetter#fromItemStack} 和 {@link ConsumableProperty#fromString}，避免反复调用便利方法
 * <p>
 * 由于 JavaScript 的数字默认为 Double，直接传入会导致 Java 泛型捕获失败并抛出 ClassCastException
 * <p>
 * 在 KubeJS 侧设置整型属性时，<b>必须</b>使用 <code>java(value).asInt()</code> 显式指定类型
 */
public interface _IConsumablePropertyAccess extends _ITacticalPropertyAccess {

    @Deprecated(forRemoval = false)
    default <T> T getProperty(IConsumableDataAccess consumableDataAccess, ItemStack consumableItem, ConsumableProperty property) {
        if (property == null) return null;
        return property.get(consumableDataAccess, consumableItem);
    }
    @Deprecated(forRemoval = false)
    default <T> void setProperty(IConsumableDataAccess consumableDataAccess, ItemStack consumableItem, ConsumableProperty property, T value) {
        if (property == null) return;
        property.set(consumableDataAccess, consumableItem, value);
    }

    @Override
    @Deprecated(forRemoval = false)
    default <T> T getProperty(ItemStack consumableItem, String property) {
        @Nullable IConsumableDataAccess consumableDataAccess = IConsumableGetter.fromItemStack(consumableItem);
        if (consumableDataAccess == null) return null;

        @Nullable ConsumableProperty consumableProperty = ConsumableProperty.fromString(property);
        if (consumableProperty != null) return getProperty(consumableDataAccess, consumableItem, consumableProperty);
        return _ITacticalPropertyAccess.super.getProperty(consumableItem, property);
    }
    @Override
    @Deprecated(forRemoval = false)
    default <T> void setProperty(ItemStack consumableItem, String property, T value) {
        @Nullable IConsumableDataAccess consumableDataAccess = IConsumableGetter.fromItemStack(consumableItem);
        if (consumableDataAccess == null) return;

        @Nullable ConsumableProperty consumableProperty = ConsumableProperty.fromString(property);
        if (consumableProperty != null) {
            setProperty(consumableDataAccess, consumableItem, consumableProperty, value);
            return;
        }
        _ITacticalPropertyAccess.super.setProperty(consumableItem, property, value);
    }
}
