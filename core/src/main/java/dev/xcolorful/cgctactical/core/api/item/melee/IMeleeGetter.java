package dev.xcolorful.cgctactical.core.api.item.melee;

import dev.xcolorful.cgctactical.core.api.item.IMelee;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface IMeleeGetter {

    static @Nullable IMelee fromItemStack(@Nullable ItemStack meleeItem) {
        if (meleeItem == null) return null;
        return meleeItem.getItem() instanceof IMelee iMelee ? iMelee : null;
    }
    static @Nullable IMelee fromMainHand(@Nullable LivingEntity livingEntity) {
        if (livingEntity == null) return null;
        return livingEntity.getMainHandItem().getItem() instanceof IMelee iMelee ? iMelee : null;
    }

    // --------Deprecated--------

    @Deprecated static @Nullable IMelee of(@Nullable ItemStack meleeItem) {
        return fromItemStack(meleeItem);
    }
}
