package dev.xcolorful.cgctactical.client.gui.tooltip.throwable;

import dev.xcolorful.cgctactical.client.api.item.throwable.ThrowableTooltipMask;
import dev.xcolorful.customgun.client.gui.tooltip.AbstractTooltipPart;
import net.minecraft.client.gui.Font;

/**
 * 渲染留空，等待实现
 */
public final class ThrowableBaseInfoPart extends AbstractTooltipPart implements ThrowableTooltipPart {
    public static final ThrowableBaseInfoPart INSTANCE = new ThrowableBaseInfoPart();
    private ThrowableBaseInfoPart() {}

    @Override
    public void build(ClientThrowableTooltip.Context context, Font font) {
    }

    @Override
    public int measureHeight(ClientThrowableTooltip.Context context) {
        if (!context.visibleParts.contains(ThrowableTooltipMask.BASE_INFO)) return 0;

        return 0;
    }

    @Override
    public void renderText(ClientThrowableTooltip.Context context,
                           int startX, int startY) {
        // mixin注入点
    }

    @Override
    public void renderImage(ClientThrowableTooltip.Context context,
                            int startX, int startY) {
        // mixin注入点
    }
}
