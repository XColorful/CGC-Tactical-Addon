package dev.xcolorful.cgctactical.core.api.item.consumable;

import dev.xcolorful.cgctactical.core.api.item.tactical.TacticalDataAccessor;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.resource.instance.data.ConsumableIndexInstance;
import dev.xcolorful.customgun.core.api.resource.ResourceTag;
import dev.xcolorful.customgun.core.util.NBTUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ConsumableDataAccessor extends TacticalDataAccessor, IConsumableDataAccess {

    // --------IConsumableDataAccess--------

    @Override
    default @NotNull ResourceLocation getConsumableLocation(ItemStack consumableItem) {
        var consumableLocation = NBTUtils.getResourceLocation(consumableItem, ConsumableProperty.CONSUMABLE_LOCATION.getTagName());
        return consumableLocation != null ? consumableLocation : ResourceTag.NULL_LOCATION;
    }
    @Override
    default void setConsumableLocation(ItemStack consumableItem, ResourceLocation consumableLocation) {
        NBTUtils.setResourceLocation(consumableItem, ConsumableProperty.CONSUMABLE_LOCATION.getTagName(), consumableLocation);
    }
    @Override
    default @NotNull ResourceLocation getConsumableDisplayLocation(ItemStack consumableItem) {
        var consumableDisplayLocation = NBTUtils.getResourceLocation(consumableItem, ConsumableProperty.CONSUMABLE_DISPLAY_LOCATION.getTagName());
        if (consumableDisplayLocation != null) return consumableDisplayLocation;

        var consumableLocation = this.getConsumableLocation(consumableItem);
        @Nullable ConsumableIndexInstance consumableIndexInstance = ResourceApi.getConsumableIndexInstance(consumableLocation);
        if (consumableIndexInstance == null) return ResourceTag.NULL_LOCATION;

        return consumableIndexInstance.getPojo().getDisplayIndexLocation();
    }
    @Override
    default void setConsumableDisplayLocation(ItemStack consumableItem, ResourceLocation consumableDisplayLocation) {
        NBTUtils.setResourceLocation(consumableItem, ConsumableProperty.CONSUMABLE_DISPLAY_LOCATION.getTagName(), consumableDisplayLocation);
    }

    // --------IConsumableStateAccess--------

    @Override
    default boolean hasTooltipMask(ItemStack consumableItem) {
        return NBTUtils.hasKey(consumableItem, ConsumableProperty.TOOLTIP_MASK.getTagName());
    }
    @Override
    default int getTooltipMask(ItemStack consumableItem) {
        return NBTUtils.getInt(consumableItem, ConsumableProperty.TOOLTIP_MASK.getTagName());
    }
    @Override
    default void setTooltipMask(ItemStack consumableItem, int tooltipMask) {
        NBTUtils.setInt(consumableItem, ConsumableProperty.TOOLTIP_MASK.getTagName(), tooltipMask);
    }

    // --------ITacticalDataAccess--------

    @Override
    default @Nullable ResourceLocation getCooldownLocation(ItemStack consumableItem) {
        var consumableLocation = this.getConsumableLocation(consumableItem);
        @Nullable ConsumableIndexInstance consumableIndexInstance = ResourceApi.getConsumableIndexInstance(consumableLocation);
        if (consumableIndexInstance == null) return null;

        return consumableIndexInstance.getConsumableData().getCooldownCategoryLocation();
    }
    @Override
    default int getUseDurationLimit(ItemStack consumableItem) {
        var consumableLocation = this.getConsumableLocation(consumableItem);
        @Nullable ConsumableIndexInstance consumableIndexInstance = ResourceApi.getConsumableIndexInstance(consumableLocation);
        if (consumableIndexInstance == null) return 0;

        return consumableIndexInstance.getConsumableData().getUseDuration();
    }
    @Override
    default int getDrawTime(ItemStack consumableItem) {
        var consumableLocation = this.getConsumableLocation(consumableItem);
        @Nullable ConsumableIndexInstance consumableIndexInstance = ResourceApi.getConsumableIndexInstance(consumableLocation);
        if (consumableIndexInstance == null) return 0;

        return consumableIndexInstance.getConsumableData().getDrawTime();
    }
    @Override
    default int getPutAwayTime(ItemStack consumableItem) {
        var consumableLocation = this.getConsumableLocation(consumableItem);
        @Nullable ConsumableIndexInstance consumableIndexInstance = ResourceApi.getConsumableIndexInstance(consumableLocation);
        if (consumableIndexInstance == null) return 0;

        return consumableIndexInstance.getConsumableData().getPutAwayTime();
    }
}
