package dev.xcolorful.cgctactical.core.item.throwable;

import dev.xcolorful.cgctactical.core.api.item.IThrowable;
import dev.xcolorful.cgctactical.core.api.item.throwable.ThrowableDataAccessor;
import dev.xcolorful.cgctactical.core.api.minecraft.item.ItemType;
import dev.xcolorful.cgctactical.core.init.registry.ModItems;
import net.minecraft.world.item.Item;

public class ThrowableItem extends Item implements IThrowable, ThrowableDataAccessor {

    protected ThrowableItem(Properties properties) {
        super(properties);
    }
    public ThrowableItem() {
        this(ModItems.CUSTOM_ITEM_PROPERTY.apply(ItemType.THROWABLE.getRegistryLocation()));
    }
}
