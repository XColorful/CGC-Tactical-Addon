package dev.xcolorful.cgctactical.core.api.item.tactical;

import dev.xcolorful.cgctactical.core.api.item.ITacticalItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface ITacticalItemGetter {

    static @Nullable ITacticalItem fromItemStack(@Nullable ItemStack tacticalItem) {
        if (tacticalItem == null) return null;
        return tacticalItem.getItem() instanceof ITacticalItem iTacticalItem ? iTacticalItem : null;
    }
    static @Nullable ITacticalItem fromMainHand(@Nullable LivingEntity livingEntity) {
        if (livingEntity == null) return null;
        return livingEntity.getMainHandItem().getItem() instanceof ITacticalItem iTacticalItem ? iTacticalItem : null;
    }
}
