package dev.xcolorful.cgctactical.client.gui.tooltip.throwable;

import dev.xcolorful.cgctactical.client.api.item.throwable.ThrowableTooltipMask;
import dev.xcolorful.cgctactical.core.resource.data.index.ThrowableIndex;
import dev.xcolorful.cgctactical.core.resource.instance.data.ThrowableIndexInstance;
import dev.xcolorful.customgun.client.gui.tooltip.AbstractTooltipPart;
import dev.xcolorful.customgun.client.gui.tooltip.gun.GunDescriptionPart;
import dev.xcolorful.customgun.core.api.minecraft.Color64;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/*
描述内容，描述内容
，描述内容。
 */
public final class ThrowableDescriptionPart extends AbstractTooltipPart implements ThrowableTooltipPart {
    public static final ThrowableDescriptionPart INSTANCE = new ThrowableDescriptionPart();
    private ThrowableDescriptionPart() {}

    private static final Color64 _defaultDescriptionColor = Color64.fromChatFormatting(ChatFormatting.GRAY);

    @Override
    public void build(ClientThrowableTooltip.Context context, Font font) {
        @Nullable ThrowableIndexInstance throwableIndexInstance = context.throwableIndexInstance;
        if (throwableIndexInstance == null) return;

        List<FormattedCharSequence> desc; {
            ThrowableIndex throwableIndex = throwableIndexInstance.getPojo();
            String tooltipLang = throwableIndex.getTooltipLang();

            desc = font.split(Component.translatable(tooltipLang), GunDescriptionPart.getGunDescriptionLineWidth());
        }
        context.view.desc = desc;
        for (int i = 0; i < desc.size(); i++) {
            FormattedCharSequence sequence = desc.get(i);
            context.widenMaxWidth(font.width(sequence));
        }
    }

    @Override
    public int measureHeight(ClientThrowableTooltip.Context context) {
        if (!context.visibleParts.contains(ThrowableTooltipMask.DESCRIPTION)) return 0;

        @Nullable List<FormattedCharSequence> desc = context.view.desc;
        if (desc == null || desc.isEmpty()) return 0;

        return desc.size() * textLineHeight;
    }

    @Override
    public void renderText(ClientThrowableTooltip.Context context,
                           int startX, int startY) {
        int currentX = startX;
        int currentY = startY;

        @Nullable List<FormattedCharSequence> desc = context.view.desc;
        if (desc != null) {
            // 投掷物描述
            for (int i = 0; i < desc.size(); i++) {
                // 单行投掷物描述
                FormattedCharSequence sequence = desc.get(i);
                context.drawText(sequence,
                        currentX, currentY,
                        _defaultDescriptionColor.getRGB()
                );
                currentY += textLineHeight;
            }
        }
    }
}
