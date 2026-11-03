package dev.xcolorful.cgctactical.core.api.item.melee;

import dev.xcolorful.cgctactical.core.api.item.tactical._ITacticalPropertyAccess;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * 专供第三方脚本（如 KubeJS）调用的属性访问接口，模组内部严禁使用 (会循环调用)
 * <p>
 * 推荐脚本先缓存 {@link IMeleeGetter#fromItemStack} 和 {@link MeleeProperty#fromString}，避免反复调用便利方法
 * <p>
 * 由于 JavaScript 的数字默认为 Double，直接传入会导致 Java 泛型捕获失败并抛出 ClassCastException
 * <p>
 * 在 KubeJS 侧设置整型属性时，<b>必须</b>使用 <code>java(value).asInt()</code> 显式指定类型
 */
public interface _IMeleePropertyAccess extends _ITacticalPropertyAccess {

    @Deprecated(forRemoval = false)
    default <T> T getProperty(IMeleeDataAccess meleeDataAccess, ItemStack meleeItem, MeleeProperty property) {
        if (property == null) return null;
        return property.get(meleeDataAccess, meleeItem);
    }
    @Deprecated(forRemoval = false)
    default <T> void setProperty(IMeleeDataAccess meleeDataAccess, ItemStack meleeItem, MeleeProperty property, T value) {
        if (property == null) return;
        property.set(meleeDataAccess, meleeItem, value);
    }

    @Override
    @Deprecated(forRemoval = false)
    default <T> T getProperty(ItemStack meleeItem, String property) {
        @Nullable IMeleeDataAccess meleeDataAccess = IMeleeGetter.fromItemStack(meleeItem);
        if (meleeDataAccess == null) return null;

        @Nullable MeleeProperty meleeProperty = MeleeProperty.fromString(property);
        if (meleeProperty != null) return getProperty(meleeDataAccess, meleeItem, meleeProperty);
        return _ITacticalPropertyAccess.super.getProperty(meleeItem, property);
    }
    @Override
    @Deprecated(forRemoval = false)
    default <T> void setProperty(ItemStack meleeItem, String property, T value) {
        @Nullable IMeleeDataAccess meleeDataAccess = IMeleeGetter.fromItemStack(meleeItem);
        if (meleeDataAccess == null) return;

        @Nullable MeleeProperty meleeProperty = MeleeProperty.fromString(property);
        if (meleeProperty != null) {
            setProperty(meleeDataAccess, meleeItem, meleeProperty, value);
            return;
        }
        _ITacticalPropertyAccess.super.setProperty(meleeItem, property, value);
    }
}
