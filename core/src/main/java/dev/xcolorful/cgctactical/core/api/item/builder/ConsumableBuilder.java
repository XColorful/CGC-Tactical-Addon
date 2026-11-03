package dev.xcolorful.cgctactical.core.api.item.builder;

import dev.xcolorful.cgctactical.core.api.item.IConsumable;
import dev.xcolorful.cgctactical.core.api.item.consumable.ConsumableProperty;
import dev.xcolorful.cgctactical.core.api.item.consumable.IConsumableGetter;
import dev.xcolorful.customgun.core.api.item.builder.ItemBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

public class ConsumableBuilder extends ItemBuilder<ConsumableBuilder> {

    private final IConsumable iConsumable;

    private ConsumableBuilder(IConsumable iConsumable, ItemStack consumableItem) {
        super(consumableItem);
        this.iConsumable = iConsumable;
    }
    public static ConsumableBuilder create(ItemLike consumable) {
        ItemStack consumableItem = new ItemStack(consumable);
        @Nullable IConsumable iConsumable = IConsumableGetter.fromItemStack(consumableItem);
        if (iConsumable != null) return new ConsumableBuilder(iConsumable, consumableItem);
        else throw new IllegalArgumentException("Item is not a IConsumable");
    }

    public <T> ConsumableBuilder setProperty(ConsumableProperty property, Class<T> type, T value) {
        property.set(this.iConsumable, this.itemStack, value);
        return this;
    }
}
