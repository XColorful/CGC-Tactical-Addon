package dev.xcolorful.cgctactical.core.item.consumable;

import dev.xcolorful.cgctactical.client.item.consumable._ConsumableItem;
import dev.xcolorful.cgctactical.core.api.item.IConsumable;
import dev.xcolorful.cgctactical.core.api.item.consumable.ConsumableDataAccessor;
import dev.xcolorful.cgctactical.core.api.minecraft.item.ItemType;
import dev.xcolorful.cgctactical.core.gui.tooltip.consumable.ConsumableTooltip;
import dev.xcolorful.cgctactical.core.init.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ConsumableItem extends Item implements IConsumable, ConsumableDataAccessor {

    protected ConsumableItem(Properties properties) {
        super(properties);
    }
    public ConsumableItem() {
        this(ModItems.CUSTOM_ITEM_PROPERTY.apply(ItemType.CONSUMABLE.getRegistryLocation()));
    }

    // --------Item--------

    /**
     * 获取供客户端使用的 Tooltip 信息
     */
    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack consumableItem) {
        return Optional.ofNullable(ConsumableTooltip.fromItem(consumableItem));
    }

    // --------Client--------

    @Override
    public @NotNull Component getName(@NotNull ItemStack consumableItem) {
        var name = _ConsumableItem.getName(this, consumableItem);
        return name != null ? name : super.getName(consumableItem);
    }
}
