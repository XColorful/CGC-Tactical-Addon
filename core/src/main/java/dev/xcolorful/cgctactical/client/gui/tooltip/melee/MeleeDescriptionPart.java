package dev.xcolorful.cgctactical.client.gui.tooltip.melee;

import dev.xcolorful.cgctactical.client.api.item.melee.MeleeTooltipMask;
import dev.xcolorful.cgctactical.core.resource.data.index.MeleeIndex;
import dev.xcolorful.cgctactical.core.resource.instance.data.MeleeIndexInstance;
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
public final class MeleeDescriptionPart extends AbstractTooltipPart implements MeleeTooltipPart {
    public static final MeleeDescriptionPart INSTANCE = new MeleeDescriptionPart();
    private MeleeDescriptionPart() {}

    private static final Color64 _defaultDescriptionColor = Color64.fromChatFormatting(ChatFormatting.GRAY);

    @Override
    public void build(ClientMeleeTooltip.Context context, Font font) {
        @Nullable MeleeIndexInstance meleeIndexInstance = context.meleeIndexInstance;
        if (meleeIndexInstance == null) return;

        List<FormattedCharSequence> desc; {
            MeleeIndex meleeIndex = meleeIndexInstance.getPojo();
            String tooltipLang = meleeIndex.getTooltipLang();

            desc = font.split(Component.translatable(tooltipLang), GunDescriptionPart.getGunDescriptionLineWidth());
        }
        context.view.desc = desc;
        for (int i = 0; i < desc.size(); i++) {
            FormattedCharSequence sequence = desc.get(i);
            context.widenMaxWidth(font.width(sequence));
        }
    }

    @Override
    public int measureHeight(ClientMeleeTooltip.Context context) {
        if (!context.visibleParts.contains(MeleeTooltipMask.DESCRIPTION)) return 0;

        @Nullable List<FormattedCharSequence> desc = context.view.desc;
        if (desc == null || desc.isEmpty()) return 0;

        return desc.size() * textLineHeight;
    }

    @Override
    public void renderText(ClientMeleeTooltip.Context context,
                           int startX, int startY) {
        int currentX = startX;
        int currentY = startY;

        @Nullable List<FormattedCharSequence> desc = context.view.desc;
        if (desc != null) {
            // 近战描述
            for (int i = 0; i < desc.size(); i++) {
                // 单行近战描述
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
