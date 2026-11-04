package dev.xcolorful.cgctactical.client.item.melee;

import dev.xcolorful.cgctactical.core.api.item.IMelee;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.resource.data.index.MeleeIndex;
import dev.xcolorful.cgctactical.core.resource.instance.data.MeleeIndexInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class _MeleeItem {

    public static @Nullable Component getName(IMelee iMelee, @NotNull ItemStack meleeItem) {
        var meleeLocation = iMelee.getMeleeLocation(meleeItem);
        @Nullable MeleeIndexInstance meleeIndexInstance = ResourceApi.getMeleeIndexInstance(meleeLocation);
        if (meleeIndexInstance == null) return null;

        MeleeIndex meleeIndex = meleeIndexInstance.getPojo();
        return Component.translatable(meleeIndex.getNameLang());
    }
}
