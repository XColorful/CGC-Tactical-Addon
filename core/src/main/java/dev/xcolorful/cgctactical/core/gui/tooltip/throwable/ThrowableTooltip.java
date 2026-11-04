package dev.xcolorful.cgctactical.core.gui.tooltip.throwable;

import dev.xcolorful.cgctactical.core.api.item.IThrowable;
import dev.xcolorful.cgctactical.core.api.item.throwable.IThrowableGetter;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.resource.instance.data.ThrowableIndexInstance;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public record ThrowableTooltip(IThrowable iThrowable, ItemStack throwableItem,
                               // --------Cache--------
                               Identifier throwableLocation)
        implements TooltipComponent {

    public static @Nullable ThrowableTooltip fromItem(@Nullable ItemStack throwableItem) {
        @Nullable IThrowable iThrowable = IThrowableGetter.fromItemStack(throwableItem);
        if (iThrowable == null) return null;

        var throwableLocation = iThrowable.getThrowableLocation(throwableItem);

        @Nullable ThrowableIndexInstance throwableIndexInstance = ResourceApi.getThrowableIndexInstance(throwableLocation);
        if (throwableIndexInstance == null) return null;

        return new ThrowableTooltip(iThrowable, throwableItem,
                throwableLocation);
    }
}
