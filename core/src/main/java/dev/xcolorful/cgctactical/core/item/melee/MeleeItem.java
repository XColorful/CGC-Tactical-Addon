package dev.xcolorful.cgctactical.core.item.melee;

import dev.xcolorful.cgctactical.core.api.item.IMelee;
import dev.xcolorful.cgctactical.core.api.item.melee.MeleeDataAccessor;
import dev.xcolorful.cgctactical.core.api.minecraft.item.ItemType;
import dev.xcolorful.cgctactical.core.init.registry.ModItems;
import net.minecraft.world.item.Item;

public class MeleeItem extends Item implements IMelee, MeleeDataAccessor {

    protected MeleeItem(Properties properties) {
        super(properties);
    }
    public MeleeItem() {
        this(ModItems.CUSTOM_ITEM_PROPERTY.apply(ItemType.MELEE.getRegistryLocation()));
    }
}
