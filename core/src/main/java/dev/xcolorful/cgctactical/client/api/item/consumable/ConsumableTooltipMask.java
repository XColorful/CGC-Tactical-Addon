package dev.xcolorful.cgctactical.client.api.item.consumable;

import dev.xcolorful.cgctactical.client.gui.tooltip.consumable.*;
import dev.xcolorful.customgun.core.api.resource.ResourceTag;

import java.util.EnumSet;

public enum ConsumableTooltipMask implements ResourceTag.MaskTag {
    DESCRIPTION(0, ConsumableDescriptionPart.INSTANCE),
    STATE_INFO(1, ConsumableStateInfoPart.INSTANCE),
    BASE_INFO(2, ConsumableBaseInfoPart.INSTANCE),
    ENCHANTMENT_INFO(3, ConsumableEnchantmentInfoPart.INSTANCE),
    GUIDE_TIP(4, ConsumableGuideTipPart.INSTANCE),
    DETAIL_INFO(5, ConsumableDetailInfoPart.INSTANCE);

    public final String maskName;
    private final int mask;
    /**
     * 从 {@link ClientConsumableTooltip} 提取而来
     */
    private final ConsumableTooltipPart tooltipPart;
    ConsumableTooltipMask(int ordinal, ConsumableTooltipPart tooltipPart) {
        this.maskName = this.name().toLowerCase();
        this.mask = 1 << ordinal;
        this.tooltipPart = tooltipPart;
    }
    @Override
    public String getTagName() {
        return this.maskName;
    }
    @Override
    public int getMask() {
        return this.mask;
    }

    public ConsumableTooltipPart getTooltipPart() {
        return this.tooltipPart;
    }

    private static final ConsumableTooltipMask[] VALUES = values();

    /**
     * Tooltip 显示掩码
     * <ul>
     *     <li>二进制位为{@code 1}：禁用对应的 Tooltip 部分</li>
     *     <li>二进制位为{@code 0}：启用对应的 Tooltip 部分</li>
     * </ul>
     * 传入{@code 0}表示全部启用
     */
    public static EnumSet<ConsumableTooltipMask> fromBitmap(int bitmap) {
        EnumSet<ConsumableTooltipMask> set = EnumSet.noneOf(ConsumableTooltipMask.class);
        for (ConsumableTooltipMask value : VALUES) {
            if ((bitmap & value.mask) == 0) {
                set.add(value);
            }
        }
        return set;
    }

    /**
     * 根据启用的 Tooltip 部分生成显示掩码
     * <ul>
     *     <li>集合中包含：对应二进制位为{@code 0}</li>
     *     <li>集合中不包含：对应二进制位为{@code 1}</li>
     * </ul>
     * @param set 启用的 Tooltip 部分
     * @return Tooltip 显示掩码
     */
    public static int toBitmap(EnumSet<ConsumableTooltipMask> set) {
        int bitmap = 0;
        for (ConsumableTooltipMask value : VALUES) {
            if (!set.contains(value)) {
                bitmap |= value.mask;
            }
        }
        return bitmap;
    }
}
