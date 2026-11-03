package dev.xcolorful.cgctactical.core.item.consumable;

import dev.xcolorful.cgctactical.core.api.item.IConsumable;
import dev.xcolorful.cgctactical.core.api.item.consumable.ConsumableDataAccessor;
import dev.xcolorful.cgctactical.core.api.minecraft.item.ItemType;
import dev.xcolorful.cgctactical.core.init.registry.ModItems;
import net.minecraft.world.item.Item;

public class ConsumableItem extends Item implements IConsumable, ConsumableDataAccessor {

    protected ConsumableItem(Properties properties) {
        super(properties);
    }
    public ConsumableItem() {
        this(ModItems.CUSTOM_ITEM_PROPERTY.apply(ItemType.CONSUMABLE.getRegistryLocation()));
    }
}
