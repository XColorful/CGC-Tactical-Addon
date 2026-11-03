package dev.xcolorful.cgctactical.core.api.item.melee;

import dev.xcolorful.cgctactical.core.api.item.IMelee;
import dev.xcolorful.cgctactical.core.api.item.tactical.ITacticalDataAccess;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.resource.data.data.melee.combat._MeleeMovement;
import dev.xcolorful.cgctactical.core.resource.data.index.MeleeIndex;
import dev.xcolorful.cgctactical.core.resource.instance.data.MeleeIndexInstance;
import dev.xcolorful.customgun.core.api.resource.ResourceTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IMeleeDataAccess extends ITacticalDataAccess,
        IMeleeStateAccess,
        _IMeleePropertyAccess {

    @Nullable String getManagerGroupTag(ItemStack meleeItem);
    void setManagerGroupTag(ItemStack meleeItem, String managerGroupTag);

    /**
     * 获取近战资源位置，如不存在则返回 {@link ResourceTag#NULL_LOCATION}
     */
    @NotNull Identifier getMeleeLocation(ItemStack meleeItem);
    void setMeleeLocation(ItemStack meleeItem, Identifier meleeLocation);
    /**
     * 获取NBT指定的MeleeDisplay，其次查找{@link MeleeIndexInstance}，如无则返回 {@link ResourceTag#NULL_LOCATION}
     */
    @NotNull Identifier getMeleeDisplayLocation(ItemStack meleeItem);
    void setMeleeDisplayLocation(ItemStack meleeItem, Identifier meleeDisplayLocation);

    int getMeleeCooldown(ItemStack meleeItem, MeleeAction meleeAction, int combo);
    int getMeleeDelay(ItemStack meleeItem, MeleeAction meleeAction, int combo);
    @Nullable _MeleeMovement getMeleeMovement(ItemStack meleeItem, MeleeAction meleeAction);

    // --------Deprecated--------

    /**
     * 标准起手 (定式)，{@link IMelee} 等接口不定义该便利方法
     */
    @Deprecated(forRemoval = true) default @Nullable MeleeIndex getMeleeIndex(ItemStack meleeItem) {
        var meleeLocation = this.getMeleeLocation(meleeItem);
        @Nullable MeleeIndexInstance meleeIndexInstance = ResourceApi.getMeleeIndexInstance(meleeLocation);
        if (meleeIndexInstance == null) return null;

        return meleeIndexInstance.getPojo();
    }

    @SuppressWarnings("deprecation")
    @Override
    @Deprecated default int getAttackCoolDown(ItemStack meleeItem, MeleeAction meleeAction, int combo) {
        return getMeleeCooldown(meleeItem, meleeAction, combo);
    }
    @Deprecated default int getAttackDelay(Player livingCombatant, ItemStack meleeItem, MeleeAction meleeAction, int combo) {
        return getMeleeDelay(meleeItem, meleeAction, combo);
    }
    @Deprecated default @Nullable _MeleeMovement getAttachMovement(Player livingCombatant, ItemStack meleeItem, MeleeAction meleeAction) {
        return getMeleeMovement(meleeItem, meleeAction);
    }
}
