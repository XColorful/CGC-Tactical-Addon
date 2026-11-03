package dev.xcolorful.cgctactical.core.api.item.tactical;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface TacticalDataAccessor extends ITacticalDataAccess {

    @Override
    default @Nullable Identifier getCooldownLocation(ItemStack tacticalItem) {
        return null;
    }

    @Override
    default int getUseDurationLimit(ItemStack tacticalItem) {
        return 0;
    }

    @Override
    default int getDrawTime(ItemStack tacticalItem) {
        return 0;
    }

    @Override
    default int getPutAwayTime(ItemStack tacticalItem) {
        return 0;
    }
}
