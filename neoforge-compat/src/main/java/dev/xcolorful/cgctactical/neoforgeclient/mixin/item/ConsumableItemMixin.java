package dev.xcolorful.cgctactical.neoforgeclient.mixin.item;

import dev.xcolorful.cgctactical.core.item.consumable.ConsumableItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ConsumableItem.class)
public abstract class ConsumableItemMixin extends Item {

    public ConsumableItemMixin(Properties properties) {
        super(properties);
    }

    // --------IClientItemExtensions-------

    // --------IAnimateGeoItem--------

    // --------IItemBEWLR--------
}
