package dev.xcolorful.cgctactical.client.gui.tooltip.consumable;

import dev.xcolorful.cgctactical.client.api.item.consumable.ConsumableTooltipMask;
import dev.xcolorful.cgctactical.core.resource.data.index.ConsumableIndex;
import dev.xcolorful.cgctactical.core.resource.instance.data.ConsumableIndexInstance;
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
public final class ConsumableDescriptionPart extends AbstractTooltipPart implements ConsumableTooltipPart {
    public static final ConsumableDescriptionPart INSTANCE = new ConsumableDescriptionPart();
    private ConsumableDescriptionPart() {}

    private static final Color64 _defaultDescriptionColor = Color64.fromChatFormatting(ChatFormatting.GRAY);

    @Override
    public void build(ClientConsumableTooltip.Context context, Font font) {
        @Nullable ConsumableIndexInstance consumableIndexInstance = context.consumableIndexInstance;
        if (consumableIndexInstance == null) return;

        List<FormattedCharSequence> desc; {
            ConsumableIndex consumableIndex = consumableIndexInstance.getPojo();
            String tooltipLang = consumableIndex.getTooltipLang();

            desc = font.split(Component.translatable(tooltipLang), GunDescriptionPart.getGunDescriptionLineWidth());
        }
        context.view.desc = desc;
        for (int i = 0; i < desc.size(); i++) {
            FormattedCharSequence sequence = desc.get(i);
            context.widenMaxWidth(font.width(sequence));
        }
    }

    @Override
    public int measureHeight(ClientConsumableTooltip.Context context) {
        if (!context.visibleParts.contains(ConsumableTooltipMask.DESCRIPTION)) return 0;

        @Nullable List<FormattedCharSequence> desc = context.view.desc;
        if (desc == null || desc.isEmpty()) return 0;

        return desc.size() * textLineHeight;
    }

    @Override
    public void renderText(ClientConsumableTooltip.Context context,
                           int startX, int startY) {
        int currentX = startX;
        int currentY = startY;

        @Nullable List<FormattedCharSequence> desc = context.view.desc;
        if (desc != null) {
            // 消耗品描述
            for (int i = 0; i < desc.size(); i++) {
                // 单行消耗品描述
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
