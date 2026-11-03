package dev.xcolorful.cgctactical.core.api.item.builder;

import dev.xcolorful.cgctactical.core.api.item.IThrowable;
import dev.xcolorful.cgctactical.core.api.item.throwable.IThrowableGetter;
import dev.xcolorful.cgctactical.core.api.item.throwable.ThrowableProperty;
import dev.xcolorful.customgun.core.api.item.builder.ItemBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

public class ThrowableBuilder extends ItemBuilder<ThrowableBuilder> {

    private final IThrowable iThrowable;

    private ThrowableBuilder(IThrowable iThrowable, ItemStack throwableItem) {
        super(throwableItem);
        this.iThrowable = iThrowable;
    }
    public static ThrowableBuilder create(ItemLike throwable) {
        ItemStack throwableItem = new ItemStack(throwable);
        @Nullable IThrowable iThrowable = IThrowableGetter.fromItemStack(throwableItem);
        if (iThrowable != null) return new ThrowableBuilder(iThrowable, throwableItem);
        else throw new IllegalArgumentException("Item is not a IThrowable");
    }

    public <T> ThrowableBuilder setProperty(ThrowableProperty property, Class<T> type, T value) {
        property.set(this.iThrowable, this.itemStack, value);
        return this;
    }
}
