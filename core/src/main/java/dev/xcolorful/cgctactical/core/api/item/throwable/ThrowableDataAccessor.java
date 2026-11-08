package dev.xcolorful.cgctactical.core.api.item.throwable;

import dev.xcolorful.cgctactical.core.api.item.tactical.TacticalDataAccessor;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.resource.instance.data.ThrowableIndexInstance;
import dev.xcolorful.customgun.core.api.resource.ResourceTag;
import dev.xcolorful.customgun.core.util.NBTUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ThrowableDataAccessor extends TacticalDataAccessor, IThrowableDataAccess {

    // --------IThrowableDataAccess--------

    @Override
    default @NotNull Identifier getThrowableLocation(ItemStack throwableItem) {
        var throwableLocation = NBTUtils.getResourceLocation(throwableItem, ThrowableProperty.THROWABLE_LOCATION.getTagName());
        return throwableLocation != null ? throwableLocation : ResourceTag.NULL_LOCATION;
    }
    @Override
    default void setThrowableLocation(ItemStack throwableItem, Identifier throwableLocation) {
        NBTUtils.setResourceLocation(throwableItem, ThrowableProperty.THROWABLE_LOCATION.getTagName(), throwableLocation);
    }
    @Override
    default @NotNull Identifier getThrowableDisplayLocation(ItemStack throwableItem) {
        var throwableDisplayLocation = NBTUtils.getResourceLocation(throwableItem, ThrowableProperty.THROWABLE_DISPLAY_LOCATION.getTagName());
        if (throwableDisplayLocation != null) return throwableDisplayLocation;

        var throwableLocation = this.getThrowableLocation(throwableItem);
        @Nullable ThrowableIndexInstance throwableIndexInstance = ResourceApi.getThrowableIndexInstance(throwableLocation);
        if (throwableIndexInstance == null) return ResourceTag.NULL_LOCATION;

        return throwableIndexInstance.getPojo().getDisplayIndexLocation();
    }
    @Override
    default void setThrowableDisplayLocation(ItemStack throwableItem, Identifier throwableDisplayLocation) {
        NBTUtils.setResourceLocation(throwableItem, ThrowableProperty.THROWABLE_DISPLAY_LOCATION.getTagName(), throwableDisplayLocation);
    }

    // --------IThrowableStateAccess--------

    @Override
    default boolean hasTooltipMask(ItemStack throwableItem) {
        return NBTUtils.hasKey(throwableItem, ThrowableProperty.TOOLTIP_MASK.getTagName());
    }
    @Override
    default int getTooltipMask(ItemStack throwableItem) {
        return NBTUtils.getInt(throwableItem, ThrowableProperty.TOOLTIP_MASK.getTagName());
    }
    @Override
    default void setTooltipMask(ItemStack throwableItem, int tooltipMask) {
        NBTUtils.setInt(throwableItem, ThrowableProperty.TOOLTIP_MASK.getTagName(), tooltipMask);
    }

    // --------ITacticalDataAccess--------

    @Override
    default @Nullable Identifier getCooldownLocation(ItemStack throwableItem) {
        var throwableLocation = this.getThrowableLocation(throwableItem);
        @Nullable ThrowableIndexInstance throwableIndexInstance = ResourceApi.getThrowableIndexInstance(throwableLocation);
        if (throwableIndexInstance == null) return null;

        return throwableIndexInstance.getThrowableData().getCooldownCategoryLocation();
    }
    @Override
    default int getUseDurationLimit(ItemStack throwableItem) {
        var throwableLocation = this.getThrowableLocation(throwableItem);
        @Nullable ThrowableIndexInstance throwableIndexInstance = ResourceApi.getThrowableIndexInstance(throwableLocation);
        if (throwableIndexInstance == null) return 0;

        return throwableIndexInstance.getThrowableData().getPrepareTime();
    }
    @Override
    default int getPutAwayTime(ItemStack throwableItem) {
        var throwableLocation = this.getThrowableLocation(throwableItem);
        @Nullable ThrowableIndexInstance throwableIndexInstance = ResourceApi.getThrowableIndexInstance(throwableLocation);
        if (throwableIndexInstance == null) return 0;

        return (int) throwableIndexInstance.getThrowableData().getPutAwayTime();
    }
}
