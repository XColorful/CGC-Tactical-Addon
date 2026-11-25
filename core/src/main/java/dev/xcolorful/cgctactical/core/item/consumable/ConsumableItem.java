package dev.xcolorful.cgctactical.core.item.consumable;

import dev.xcolorful.cgctactical.core.api.item.IConsumable;
import dev.xcolorful.cgctactical.core.api.item.consumable.ConsumableDataAccessor;
import dev.xcolorful.cgctactical.core.api.minecraft.item.ItemType;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.gui.tooltip.consumable.ConsumableTooltip;
import dev.xcolorful.cgctactical.core.init.registry.ModItems;
import dev.xcolorful.cgctactical.core.resource.data.index.ConsumableIndex;
import dev.xcolorful.cgctactical.core.resource.instance.data.ConsumableIndexInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ConsumableItem extends Item implements IConsumable, ConsumableDataAccessor {

    protected ConsumableItem(Properties properties) {
        super(properties);
    }
    public ConsumableItem() {
        this(ModItems.CUSTOM_ITEM_PROPERTY.apply(ItemType.CONSUMABLE.getRegistryLocation()));
    }

    // --------Item--------

    @Override
    public @NotNull Component getName(@NotNull ItemStack consumableItem) {
        var consumableLocation = this.getConsumableLocation(consumableItem);
        @Nullable ConsumableIndexInstance consumableIndexInstance = ResourceApi.getConsumableIndexInstance(consumableLocation);
        if (consumableIndexInstance == null) return super.getName(consumableItem);

        ConsumableIndex consumableIndex = consumableIndexInstance.getPojo();
        return Component.translatable(consumableIndex.getNameLang());
    }

    /**
     * 获取供客户端使用的 Tooltip 信息
     */
    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack consumableItem) {
        return Optional.ofNullable(ConsumableTooltip.fromItem(consumableItem));
    }
}
