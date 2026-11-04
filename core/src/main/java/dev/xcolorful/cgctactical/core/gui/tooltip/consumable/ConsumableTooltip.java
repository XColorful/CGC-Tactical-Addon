package dev.xcolorful.cgctactical.core.gui.tooltip.consumable;

import dev.xcolorful.cgctactical.core.api.item.IConsumable;
import dev.xcolorful.cgctactical.core.api.item.consumable.IConsumableGetter;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.resource.instance.data.ConsumableIndexInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public record ConsumableTooltip(IConsumable iConsumable, ItemStack consumableItem,
                                // --------Cache--------
                                ResourceLocation consumableLocation)
        implements TooltipComponent {

    public static @Nullable ConsumableTooltip fromItem(@Nullable ItemStack consumableItem) {
        @Nullable IConsumable iConsumable = IConsumableGetter.fromItemStack(consumableItem);
        if (iConsumable == null) return null;

        var consumableLocation = iConsumable.getConsumableLocation(consumableItem);

        @Nullable ConsumableIndexInstance consumableIndexInstance = ResourceApi.getConsumableIndexInstance(consumableLocation);
        if (consumableIndexInstance == null) return null;

        return new ConsumableTooltip(iConsumable, consumableItem,
                consumableLocation);
    }
}
