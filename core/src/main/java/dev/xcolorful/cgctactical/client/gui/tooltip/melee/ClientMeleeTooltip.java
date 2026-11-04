package dev.xcolorful.cgctactical.client.gui.tooltip.melee;

import dev.xcolorful.cgctactical.client.api.item.melee.MeleeTooltipMask;
import dev.xcolorful.cgctactical.client.api.resource.ClientResourceApi;
import dev.xcolorful.cgctactical.client.resource.instance.assets.MeleeDisplayInstance;
import dev.xcolorful.cgctactical.core.api.item.IMelee;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.gui.tooltip.melee.MeleeTooltip;
import dev.xcolorful.cgctactical.core.resource.instance.data.MeleeIndexInstance;
import dev.xcolorful.customgun.client.api.gui.tooltip.BaseTooltipContext;
import dev.xcolorful.customgun.client.api.gui.tooltip.BaseTooltipView;
import dev.xcolorful.customgun.client.resource.assets.info.GunpackInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class ClientMeleeTooltip implements ClientTooltipComponent {

    public final View view;
    private final Context context;

    public ClientMeleeTooltip(@NotNull MeleeTooltip meleeTooltip) {
        this.view = new View();
        this.context = new Context(this.view, meleeTooltip);
    }

    // --------ClientTooltipComponent--------

    @Override
    public int getHeight(@NotNull Font font) {
        return this.context.getHeight(font);
    }
    @Override
    public int getWidth(@NotNull Font font) {
        return this.context.getMaxWidth();
    }

    public void renderText(int startX, int startY) {
        int currentX = startX;
        int currentY = startY;

        for (MeleeTooltipMask mask : this.context.visibleParts) {
            mask.getTooltipPart().renderText(this.context,
                    currentX, currentY);

            currentY += mask.getTooltipPart().measureHeight(this.context);
        }
    }
    public void renderImage(int startX, int startY) {
        int currentX = startX;
        int currentY = startY;

        for (MeleeTooltipMask mask : this.context.visibleParts) {
            mask.getTooltipPart().renderImage(this.context,
                    currentX, currentY);

            currentY += mask.getTooltipPart().measureHeight(this.context);
        }
    }

    // --------record--------

    @ApiStatus.Internal
    public static final class View extends BaseTooltipView {
        public @Nullable List<FormattedCharSequence> desc;
        public View() {
        }
    }
    @ApiStatus.Internal
    public static final class Context extends BaseTooltipContext<View> {
        public final @NotNull MeleeTooltip meleeTooltip;
        public final @NotNull EnumSet<MeleeTooltipMask> visibleParts;
        public @Nullable MeleeIndexInstance meleeIndexInstance;
        public @Nullable MeleeDisplayInstance meleeDisplayInstance;
        public @Nullable GunpackInfo gunpackInfo;
        public boolean showCategory = false;
        public boolean showPackInfo = false;
        public boolean showPojoLocation = false;
        public Context(@NotNull View view, @NotNull MeleeTooltip meleeTooltip) {
            super(view);
            this.meleeTooltip = meleeTooltip;
            IMelee iMelee = meleeTooltip.iMelee();
            ItemStack meleeItem = meleeTooltip.meleeItem();
            this.visibleParts = MeleeTooltipMask.fromBitmap(iMelee.getTooltipMask(meleeItem));
            this.meleeIndexInstance = ResourceApi.getMeleeIndexInstance(meleeTooltip.meleeLocation());
            this.meleeDisplayInstance = ClientResourceApi.getMeleeDisplayInstance(iMelee.getMeleeDisplayLocation(meleeItem));
            this.gunpackInfo = dev.xcolorful.customgun.client.api.resource.ClientResourceApi.getGunpackInfo(meleeTooltip.meleeLocation().getNamespace());
            this.buildView();
        }
        @Override
        protected void buildView() {
            Font font = Minecraft.getInstance().font;
            for (MeleeTooltipMask mask : this.visibleParts) {
                mask.getTooltipPart().build(this, font);
            }
        }
        @Override
        protected int calculateHeight(Font font) {
            int height = 0;
            for (MeleeTooltipMask mask : this.visibleParts) {
                height += mask.getTooltipPart().measureHeight(this);
            }
            return height;
        }
    }

    // --------Compat--------
    // 跨版本适配层
    // 其他类可直接Ctrl CV

//    @Override
    public int getHeight() {
        return this.getHeight(Minecraft.getInstance().font);
    }

    @Override
    public void extractText(@NotNull GuiGraphicsExtractor guiGraphics,
                           @NotNull Font font,
                           int startX, int startY) {
        { // 设置缓存
            this.context.textFont = font;
            this.context.textGraphic = guiGraphics;
//            this.context.textMatrix4f = matrix4f;
//            this.context.textBufferSource = bufferSource;

            this.renderText(startX, startY);
        }
        this.context._clearTextCache();
    }

    @Override
    public void extractImage(@NotNull Font font,
                            int startX, int startY,
                            int width, int height,
                            @NotNull GuiGraphicsExtractor guiGraphics) {
        { // 设置缓存
            this.context.imageFont = font;
            this.context.imageGraphic = guiGraphics;

            this.renderImage(startX, startY);
        }
        this.context._clearImageCache();
    }
}
