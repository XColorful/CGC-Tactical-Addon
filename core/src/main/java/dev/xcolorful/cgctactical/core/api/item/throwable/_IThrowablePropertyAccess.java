package dev.xcolorful.cgctactical.core.api.item.throwable;

import dev.xcolorful.cgctactical.core.api.item.tactical._ITacticalPropertyAccess;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * 专供第三方脚本（如 KubeJS）调用的属性访问接口，模组内部严禁使用 (会循环调用)
 * <p>
 * 推荐脚本先缓存 {@link IThrowableGetter#fromItemStack} 和 {@link ThrowableProperty#fromString}，避免反复调用便利方法
 * <p>
 * 由于 JavaScript 的数字默认为 Double，直接传入会导致 Java 泛型捕获失败并抛出 ClassCastException
 * <p>
 * 在 KubeJS 侧设置整型属性时，<b>必须</b>使用 <code>java(value).asInt()</code> 显式指定类型
 */
public interface _IThrowablePropertyAccess extends _ITacticalPropertyAccess {

    @Deprecated(forRemoval = false)
    default <T> T getProperty(IThrowableDataAccess throwableDataAccess, ItemStack throwableItem, ThrowableProperty property) {
        if (property == null) return null;
        return property.get(throwableDataAccess, throwableItem);
    }
    @Deprecated(forRemoval = false)
    default <T> void setProperty(IThrowableDataAccess throwableDataAccess, ItemStack throwableItem, ThrowableProperty property, T value) {
        if (property == null) return;
        property.set(throwableDataAccess, throwableItem, value);
    }

    @Override
    @Deprecated(forRemoval = false)
    default <T> T getProperty(ItemStack throwableItem, String property) {
        @Nullable IThrowableDataAccess throwableDataAccess = IThrowableGetter.fromItemStack(throwableItem);
        if (throwableDataAccess == null) return null;

        @Nullable ThrowableProperty throwableProperty = ThrowableProperty.fromString(property);
        if (throwableProperty != null) return getProperty(throwableDataAccess, throwableItem, throwableProperty);
        return _ITacticalPropertyAccess.super.getProperty(throwableItem, property);
    }
    @Override
    @Deprecated(forRemoval = false)
    default <T> void setProperty(ItemStack throwableItem, String property, T value) {
        @Nullable IThrowableDataAccess throwableDataAccess = IThrowableGetter.fromItemStack(throwableItem);
        if (throwableDataAccess == null) return;

        @Nullable ThrowableProperty throwableProperty = ThrowableProperty.fromString(property);
        if (throwableProperty != null) {
            setProperty(throwableDataAccess, throwableItem, throwableProperty, value);
            return;
        }
        _ITacticalPropertyAccess.super.setProperty(throwableItem, property, value);
    }
}
