package dev.xcolorful.cgctactical.core.gui.tooltip.melee;

import dev.xcolorful.cgctactical.core.api.item.IMelee;
import dev.xcolorful.cgctactical.core.api.item.melee.IMeleeGetter;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.resource.instance.data.MeleeIndexInstance;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public record MeleeTooltip(IMelee iMelee, ItemStack meleeItem,
                           // --------Cache--------
                           Identifier meleeLocation)
        implements TooltipComponent {

    public static @Nullable MeleeTooltip fromItem(@Nullable ItemStack meleeItem) {
        @Nullable IMelee iMelee = IMeleeGetter.fromItemStack(meleeItem);
        if (iMelee == null) return null;

        var meleeLocation = iMelee.getMeleeLocation(meleeItem);

        @Nullable MeleeIndexInstance meleeIndexInstance = ResourceApi.getMeleeIndexInstance(meleeLocation);
        if (meleeIndexInstance == null) return null;

        return new MeleeTooltip(iMelee, meleeItem,
                meleeLocation);
    }
}
