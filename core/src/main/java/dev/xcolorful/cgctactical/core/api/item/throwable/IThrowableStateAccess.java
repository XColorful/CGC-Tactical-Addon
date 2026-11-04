package dev.xcolorful.cgctactical.core.api.item.throwable;

import net.minecraft.world.item.ItemStack;

public interface IThrowableStateAccess {

    /**
     * 获取tooltip掩码 (服务端处理数据，客户端读取)
     */
    boolean hasTooltipMask(ItemStack throwableItem);
    int getTooltipMask(ItemStack throwableItem);
    void setTooltipMask(ItemStack throwableItem, int tooltipMask);
}
