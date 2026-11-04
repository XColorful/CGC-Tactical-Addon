package dev.xcolorful.cgctactical.client.gui.tooltip.consumable;

import dev.xcolorful.cgctactical.client.api.item.consumable.ConsumableTooltipMask;
import dev.xcolorful.customgun.client.gui.tooltip.AbstractTooltipPart;
import net.minecraft.client.gui.Font;

/**
 * 渲染留空，等待实现
 */
public final class ConsumableStateInfoPart extends AbstractTooltipPart implements ConsumableTooltipPart {
    public static final ConsumableStateInfoPart INSTANCE = new ConsumableStateInfoPart();
    private ConsumableStateInfoPart() {}

    @Override
    public void build(ClientConsumableTooltip.Context context, Font font) {
    }

    @Override
    public int measureHeight(ClientConsumableTooltip.Context context) {
        if (!context.visibleParts.contains(ConsumableTooltipMask.STATE_INFO)) return 0;

        return 0;
    }

    @Override
    public void renderText(ClientConsumableTooltip.Context context,
                           int startX, int startY) {
        // mixin注入点
    }

    @Override
    public void renderImage(ClientConsumableTooltip.Context context,
                            int startX, int startY) {
        // mixin注入点
    }
}
