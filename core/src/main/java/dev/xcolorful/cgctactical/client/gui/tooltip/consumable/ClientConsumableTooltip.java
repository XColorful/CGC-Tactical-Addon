package dev.xcolorful.cgctactical.client.gui.tooltip.consumable;

import dev.xcolorful.cgctactical.client.api.item.consumable.ConsumableTooltipMask;
import dev.xcolorful.cgctactical.client.api.resource.ClientResourceApi;
import dev.xcolorful.cgctactical.client.resource.instance.assets.ConsumableDisplayInstance;
import dev.xcolorful.cgctactical.core.api.item.IConsumable;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.gui.tooltip.consumable.ConsumableTooltip;
import dev.xcolorful.cgctactical.core.resource.instance.data.ConsumableIndexInstance;
import dev.xcolorful.customgun.client.api.gui.tooltip.BaseTooltipContext;
import dev.xcolorful.customgun.client.api.gui.tooltip.BaseTooltipView;
import dev.xcolorful.customgun.client.resource.assets.info.GunpackInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.util.EnumSet;
import java.util.List;

public class ClientConsumableTooltip implements ClientTooltipComponent {

    public final View view;
    private final Context context;

    public ClientConsumableTooltip(@NotNull ConsumableTooltip consumableTooltip) {
        this.view = new View();
        this.context = new Context(this.view, consumableTooltip);
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

        for (ConsumableTooltipMask mask : this.context.visibleParts) {
            mask.getTooltipPart().renderText(this.context,
                    currentX, currentY);

            currentY += mask.getTooltipPart().measureHeight(this.context);
        }
    }
    public void renderImage(int startX, int startY) {
        int currentX = startX;
        int currentY = startY;

        for (ConsumableTooltipMask mask : this.context.visibleParts) {
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
        public final @NotNull ConsumableTooltip consumableTooltip;
        public final @NotNull EnumSet<ConsumableTooltipMask> visibleParts;
        public @Nullable ConsumableIndexInstance consumableIndexInstance;
        public @Nullable ConsumableDisplayInstance consumableDisplayInstance;
        public @Nullable GunpackInfo gunpackInfo;
        public boolean showCategory = false;
        public boolean showPackInfo = false;
        public boolean showPojoLocation = false;
        public Context(@NotNull View view, @NotNull ConsumableTooltip consumableTooltip) {
            super(view);
            this.consumableTooltip = consumableTooltip;
            IConsumable iConsumable = consumableTooltip.iConsumable();
            ItemStack consumableItem = consumableTooltip.consumableItem();
            this.visibleParts = ConsumableTooltipMask.fromBitmap(iConsumable.getTooltipMask(consumableItem));
            this.consumableIndexInstance = ResourceApi.getConsumableIndexInstance(consumableTooltip.consumableLocation());
            this.consumableDisplayInstance = ClientResourceApi.getConsumableDisplayInstance(iConsumable.getConsumableDisplayLocation(consumableItem));
            this.gunpackInfo = dev.xcolorful.customgun.client.api.resource.ClientResourceApi.getGunpackInfo(consumableTooltip.consumableLocation().getNamespace());
            this.buildView();
        }
        @Override
        protected void buildView() {
            Font font = Minecraft.getInstance().font;
            for (ConsumableTooltipMask mask : this.visibleParts) {
                mask.getTooltipPart().build(this, font);
            }
        }
        @Override
        protected int calculateHeight(Font font) {
            int height = 0;
            for (ConsumableTooltipMask mask : this.visibleParts) {
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
    public void renderText(@NotNull Font font,
                           int startX, int startY,
                           @NotNull Matrix4f matrix4f,
                           @NotNull MultiBufferSource.BufferSource bufferSource) {
        { // 设置缓存
            this.context.textFont = font;
            this.context.textMatrix4f = matrix4f;
            this.context.textBufferSource = bufferSource;

            this.renderText(startX, startY);
        }
        this.context._clearTextCache();
    }

    @Override
    public void renderImage(@NotNull Font font,
                            int startX, int startY,
                            int width, int height,
                            @NotNull GuiGraphics guiGraphics) {
        { // 设置缓存
            this.context.imageFont = font;
            this.context.imageGraphic = guiGraphics;

            this.renderImage(startX, startY);
        }
        this.context._clearImageCache();
    }
}
