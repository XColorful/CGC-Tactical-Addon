package dev.xcolorful.cgctactical.forge.mixin.item;

import dev.xcolorful.cgctactical.core.api.item.IThrowable;
import dev.xcolorful.cgctactical.core.api.item.throwable.IThrowableGetter;
import dev.xcolorful.cgctactical.core.item.throwable.ThrowableItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ThrowableItem.class)
public abstract class ThrowableItemMixin extends Item {

    public ThrowableItemMixin(Item.Properties properties) {
        super(properties);
    }

    // --------IForgeItem--------

    @Override
    public int getMaxStackSize(ItemStack throwableItem) {
        @Nullable IThrowable iThrowable = IThrowableGetter.fromItemStack(throwableItem);
        if (iThrowable == null) return super.getMaxStackSize(throwableItem);

        return iThrowable.getThrowableMaxStackSize(throwableItem);
    }
}
