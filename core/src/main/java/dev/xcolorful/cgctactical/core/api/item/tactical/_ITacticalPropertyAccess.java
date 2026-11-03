package dev.xcolorful.cgctactical.core.api.item.tactical;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * 专供第三方脚本（如 KubeJS）调用的属性访问接口，模组内部严禁使用 (会循环调用)
 * <p>
 * 推荐脚本先缓存 {@link ITacticalItemGetter#fromItemStack} 和 {@link TacticalProperty#fromString}，避免反复调用便利方法
 * <p>
 * 由于 JavaScript 的数字默认为 Double，直接传入会导致 Java 泛型捕获失败并抛出 ClassCastException
 * <p>
 * 在 KubeJS 侧设置整型属性时，<b>必须</b>使用 <code>java(value).asInt()</code> 显式指定类型
 */
public interface _ITacticalPropertyAccess {

    @Deprecated(forRemoval = false)
    default <T> T getProperty(ITacticalDataAccess tacticalDataAccess, ItemStack tacticalItem, TacticalProperty property) {
        if (property == null) return null;
        return property.get(tacticalDataAccess, tacticalItem);
    }
    @Deprecated(forRemoval = false)
    default <T> void setProperty(ITacticalDataAccess tacticalDataAccess, ItemStack tacticalItem, TacticalProperty property, T value) {
        if (property == null) return;
        property.set(tacticalDataAccess, tacticalItem, value);
    }

    @Deprecated(forRemoval = false)
    default <T> T getProperty(ItemStack tacticalItem, String property) {
        @Nullable ITacticalDataAccess tacticalDataAccess = ITacticalItemGetter.fromItemStack(tacticalItem);
        if (tacticalDataAccess == null) return null;
        return getProperty(tacticalDataAccess, tacticalItem, TacticalProperty.fromString(property));
    }
    @Deprecated(forRemoval = false)
    default <T> T getProperty(LivingEntity livingEntity, String property) {
        @Nullable ITacticalDataAccess tacticalDataAccess = ITacticalItemGetter.fromMainHand(livingEntity);
        if (tacticalDataAccess == null) return null;
        return getProperty(livingEntity.getMainHandItem(), property);
    }
    @Deprecated(forRemoval = false)
    default <T> void setProperty(ItemStack tacticalItem, String property, T value) {
        @Nullable ITacticalDataAccess tacticalDataAccess = ITacticalItemGetter.fromItemStack(tacticalItem);
        if (tacticalDataAccess == null) return;
        setProperty(tacticalDataAccess, tacticalItem, TacticalProperty.fromString(property), value);
    }
    @Deprecated(forRemoval = false)
    default <T> void setProperty(LivingEntity livingEntity, String property, T value) {
        @Nullable ITacticalDataAccess tacticalDataAccess = ITacticalItemGetter.fromMainHand(livingEntity);
        if (tacticalDataAccess == null) return;
        setProperty(livingEntity.getMainHandItem(), property, value);
    }

    // --------Deprecated--------

    @Deprecated default <T> T modifyProperty(ItemStack tacticalItem, String property, T value) {
        setProperty(tacticalItem, property, value);
        return getProperty(tacticalItem, property);
    }
    @Deprecated default <T> T modifyProperty(LivingEntity livingEntity, String property, T value) {
        setProperty(livingEntity, property, value);
        return getProperty(livingEntity, property);
    }
}
