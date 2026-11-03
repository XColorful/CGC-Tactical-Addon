package dev.xcolorful.cgctactical.neoforgeclient.mixin.item;

import dev.xcolorful.cgctactical.core.item.melee.MeleeItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MeleeItem.class)
public abstract class MeleeItemMixin extends Item {

    public MeleeItemMixin(Properties properties) {
        super(properties);
    }

    // --------IClientItemExtensions-------

    // --------IAnimateGeoItem--------

    // --------IItemBEWLR--------
}
