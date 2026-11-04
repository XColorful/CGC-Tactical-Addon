package dev.xcolorful.cgctactical.client.item.consumable;

import dev.xcolorful.cgctactical.core.api.item.IConsumable;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.resource.data.index.ConsumableIndex;
import dev.xcolorful.cgctactical.core.resource.instance.data.ConsumableIndexInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class _ConsumableItem {

    public static @Nullable Component getName(IConsumable iConsumable, @NotNull ItemStack consumableItem) {
        var consumableLocation = iConsumable.getConsumableLocation(consumableItem);
        @Nullable ConsumableIndexInstance consumableIndexInstance = ResourceApi.getConsumableIndexInstance(consumableLocation);
        if (consumableIndexInstance == null) return null;

        ConsumableIndex consumableIndex = consumableIndexInstance.getPojo();
        return Component.translatable(consumableIndex.getNameLang());
    }
}
