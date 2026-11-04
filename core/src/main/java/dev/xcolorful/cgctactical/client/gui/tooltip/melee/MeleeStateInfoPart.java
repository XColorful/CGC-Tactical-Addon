package dev.xcolorful.cgctactical.client.gui.tooltip.melee;

import dev.xcolorful.cgctactical.client.api.item.melee.MeleeTooltipMask;
import dev.xcolorful.customgun.client.gui.tooltip.AbstractTooltipPart;
import net.minecraft.client.gui.Font;

/**
 * 渲染留空，等待实现
 */
public final class MeleeStateInfoPart extends AbstractTooltipPart implements MeleeTooltipPart {
    public static final MeleeStateInfoPart INSTANCE = new MeleeStateInfoPart();
    private MeleeStateInfoPart() {}

    @Override
    public void build(ClientMeleeTooltip.Context context, Font font) {
    }

    @Override
    public int measureHeight(ClientMeleeTooltip.Context context) {
        if (!context.visibleParts.contains(MeleeTooltipMask.STATE_INFO)) return 0;

        return 0;
    }

    @Override
    public void renderText(ClientMeleeTooltip.Context context,
                           int startX, int startY) {
        // mixin注入点
    }

    @Override
    public void renderImage(ClientMeleeTooltip.Context context,
                            int startX, int startY) {
        // mixin注入点
    }
}
