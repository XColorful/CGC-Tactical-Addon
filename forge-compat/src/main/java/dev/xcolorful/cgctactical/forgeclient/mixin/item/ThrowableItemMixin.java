package dev.xcolorful.cgctactical.forgeclient.mixin.item;

import dev.xcolorful.cgctactical.core.item.throwable.ThrowableItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ThrowableItem.class)
public abstract class ThrowableItemMixin extends Item {

    public ThrowableItemMixin(Properties properties) {
        super(properties);
    }

    // --------IClientItemExtensions-------

    // --------IAnimateGeoItem--------

    // --------IItemBEWLR--------
}
