package dev.xcolorful.cgctactical.core.api.item;

import dev.xcolorful.cgctactical.core.api.item.tactical.ITacticalDataAccess;
import dev.xcolorful.customgun.core.api.item.IAnimationItem;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public interface ITacticalItem extends ITacticalDataAccess,
        IAnimationItem {

    default boolean replaceVanillaAttack() {
        return false;
    }
    default boolean replaceVanillaUseItem() {
        return false;
    }
    default boolean replaceVanillaPickBlock() {
        return false;
    }

    /**
     * @return 是否取消物品损坏时行为
     */
    default boolean silentBreak() {
        return true;
    }

    // --------Deprecated--------

    @Deprecated default Identifier getId(ItemStack pojoItem) {
        return getPojoLocation(pojoItem);
    }
    @Deprecated default void setId(ItemStack pojoItem, Identifier pojoLocation) {
        setPojoLocation(pojoItem, pojoLocation);
    }
    @Deprecated default Identifier getDisplayId(ItemStack pojoItem) {
        return getPojoDisplayLocation(pojoItem);
    }
    @Deprecated default void setDisplayId(ItemStack pojoItem, Identifier displayLocation) {
        setPojoDisplayLocation(pojoItem, displayLocation);
    }

    @Deprecated default boolean shouldBlockAttack() {
        return replaceVanillaAttack();
    }
    @Deprecated default boolean shouldBlockUse() {
        return replaceVanillaUseItem();
    }
    @Deprecated default boolean shouldBlockPickBlock() {
        return replaceVanillaPickBlock();
    }

    @Deprecated default boolean blockOffhandRendering(ItemStack tacticalItem) {
        return true;
    }
}
