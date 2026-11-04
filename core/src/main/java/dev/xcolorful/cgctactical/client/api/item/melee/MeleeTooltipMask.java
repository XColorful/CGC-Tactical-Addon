package dev.xcolorful.cgctactical.client.api.item.melee;

import dev.xcolorful.cgctactical.client.gui.tooltip.melee.*;
import dev.xcolorful.customgun.core.api.resource.ResourceTag;

import java.util.EnumSet;

public enum MeleeTooltipMask implements ResourceTag.MaskTag {
    DESCRIPTION(0, MeleeDescriptionPart.INSTANCE),
    STATE_INFO(1, MeleeStateInfoPart.INSTANCE),
    BASE_INFO(2, MeleeBaseInfoPart.INSTANCE),
    ENCHANTMENT_INFO(3, MeleeEnchantmentInfoPart.INSTANCE),
    GUIDE_TIP(4, MeleeGuideTipPart.INSTANCE),
    DETAIL_INFO(5, MeleeDetailInfoPart.INSTANCE);

    public final String maskName;
    private final int mask;
    /**
     * 从 {@link ClientMeleeTooltip} 提取而来
     */
    private final MeleeTooltipPart tooltipPart;
    MeleeTooltipMask(int ordinal, MeleeTooltipPart tooltipPart) {
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

    public MeleeTooltipPart getTooltipPart() {
        return this.tooltipPart;
    }

    private static final MeleeTooltipMask[] VALUES = values();

    /**
     * Tooltip 显示掩码
     * <ul>
     *     <li>二进制位为{@code 1}：禁用对应的 Tooltip 部分</li>
     *     <li>二进制位为{@code 0}：启用对应的 Tooltip 部分</li>
     * </ul>
     * 传入{@code 0}表示全部启用
     */
    public static EnumSet<MeleeTooltipMask> fromBitmap(int bitmap) {
        EnumSet<MeleeTooltipMask> set = EnumSet.noneOf(MeleeTooltipMask.class);
        for (MeleeTooltipMask value : VALUES) {
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
    public static int toBitmap(EnumSet<MeleeTooltipMask> set) {
        int bitmap = 0;
        for (MeleeTooltipMask value : VALUES) {
            if (!set.contains(value)) {
                bitmap |= value.mask;
            }
        }
        return bitmap;
    }
}
