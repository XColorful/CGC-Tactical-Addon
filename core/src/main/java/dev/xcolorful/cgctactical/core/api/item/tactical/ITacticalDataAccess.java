package dev.xcolorful.cgctactical.core.api.item.tactical;

import dev.xcolorful.cgctactical.core.api.item.melee.MeleeAction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface ITacticalDataAccess {

    /**
     * 获取物品冷却
     */
    @Nullable Identifier getCooldownLocation(ItemStack tacticalItem);

    /**
     * 查询当前使用时长限制 (命名方式同 {@link dev.xcolorful.customgun.core.api.item.gun.IGunAmmoDataAccess#getMagAmmoLimit})
     */
    int getUseDurationLimit(ItemStack tacticalItem);

    default int getUseCooldown(ItemStack tacticalItem) {
        return 20;
    }

    int getDrawTime(ItemStack tacticalItem);
    int getPutAwayTime(ItemStack tacticalItem);

    // --------Deprecated--------

    @Deprecated default @Nullable Identifier getCoolDownId(ItemStack tacticalItem) {
        return getCooldownLocation(tacticalItem);
    }
    @Deprecated default int getMaxUsingTick(ItemStack tacticalItem) {
        return getUseDurationLimit(tacticalItem);
    }
    @Deprecated default int getAttackCoolDown(ItemStack tacticalItem, MeleeAction meleeAction, int combo) {
        return getUseCooldown(tacticalItem);
    }
}
