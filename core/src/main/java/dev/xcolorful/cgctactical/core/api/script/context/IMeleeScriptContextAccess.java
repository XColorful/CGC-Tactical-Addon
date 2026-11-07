package dev.xcolorful.cgctactical.core.api.script.context;

import dev.xcolorful.cgctactical.core.api.item.IMelee;
import net.minecraft.world.item.ItemStack;

public interface IMeleeScriptContextAccess {

    IMelee getIMelee();
    ItemStack getMeleeItem();
}
