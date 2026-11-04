package dev.xcolorful.cgctactical.core.api.item.consumable;

import net.minecraft.world.item.ItemStack;

public interface IConsumableStateAccess {

    /**
     * 获取tooltip掩码 (服务端处理数据，客户端读取)
     */
    boolean hasTooltipMask(ItemStack consumableItem);
    int getTooltipMask(ItemStack consumableItem);
    void setTooltipMask(ItemStack consumableItem, int tooltipMask);
}
