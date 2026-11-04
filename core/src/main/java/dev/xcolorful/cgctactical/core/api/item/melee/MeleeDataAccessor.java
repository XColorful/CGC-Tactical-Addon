package dev.xcolorful.cgctactical.core.api.item.melee;

import dev.xcolorful.cgctactical.core.api.item.tactical.TacticalDataAccessor;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.resource.data.data.MeleeData;import dev.xcolorful.cgctactical.core.resource.data.data.melee._CombatData;
import dev.xcolorful.cgctactical.core.resource.data.data.melee.combat._MeleeAttackInfo;
import dev.xcolorful.cgctactical.core.resource.data.data.melee.combat._MeleeMovement;
import dev.xcolorful.cgctactical.core.resource.instance.data.MeleeIndexInstance;
import dev.xcolorful.customgun.core.api.resource.ResourceTag;
import dev.xcolorful.customgun.core.util.NBTUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface MeleeDataAccessor extends TacticalDataAccessor, IMeleeDataAccess {

    // --------IMeleeDataAccess--------

    @Override
    default @Nullable String getManagerGroupTag(ItemStack meleeItem) {
        return NBTUtils.getString(meleeItem, MeleeProperty.MANAGER_GROUP.getTagName());
    }
    @Override
    default void setManagerGroupTag(ItemStack meleeItem, String managerGroupTag) {
        NBTUtils.setString(meleeItem, MeleeProperty.MANAGER_GROUP.getTagName(), managerGroupTag);
    }

    @Override
    default @NotNull ResourceLocation getMeleeLocation(ItemStack meleeItem) {
        var meleeLocation = NBTUtils.getResourceLocation(meleeItem, MeleeProperty.MELEE_LOCATION.getTagName());
        return meleeLocation != null ? meleeLocation : ResourceTag.NULL_LOCATION;
    }
    @Override
    default void setMeleeLocation(ItemStack meleeItem, ResourceLocation meleeLocation) {
        NBTUtils.setResourceLocation(meleeItem, MeleeProperty.MELEE_LOCATION.getTagName(), meleeLocation);
    }
    @Override
    default @NotNull ResourceLocation getMeleeDisplayLocation(ItemStack meleeItem) {
        var meleeDisplayLocation = NBTUtils.getResourceLocation(meleeItem, MeleeProperty.MELEE_DISPLAY_LOCATION.getTagName());
        if (meleeDisplayLocation != null) return meleeDisplayLocation;

        var meleeLocation = this.getMeleeLocation(meleeItem);
        @Nullable MeleeIndexInstance meleeIndexInstance = ResourceApi.getMeleeIndexInstance(meleeLocation);
        if (meleeIndexInstance == null) return ResourceTag.NULL_LOCATION;

        return meleeIndexInstance.getPojo().getDisplayIndexLocation();
    }
    @Override
    default void setMeleeDisplayLocation(ItemStack meleeItem, ResourceLocation meleeDisplayLocation) {
        NBTUtils.setResourceLocation(meleeItem, MeleeProperty.MELEE_DISPLAY_LOCATION.getTagName(), meleeDisplayLocation);
    }

    @ApiStatus.Internal
    static @Nullable _MeleeAttackInfo getMeleeAttackInfo(_CombatData combatData, MeleeAction meleeAction, int combo) {
        if (combo < 0) return null;

        @Nullable List<_MeleeAttackInfo> meleeAttackInfos = switch (meleeAction) {
            case LEFT_ATTACK -> combatData.getLeftAttacks();
            case RIGHT_ATTACK -> combatData.getRightAttacks();
            // 增加类型使此处强制不通过
        };
        if (meleeAttackInfos == null) return null;

        return meleeAttackInfos.get(combo % meleeAttackInfos.size());
    }
    @Override
    default int getMeleeCooldown(ItemStack meleeItem, MeleeAction meleeAction, int combo) {
        var meleeLocation = this.getMeleeLocation(meleeItem);
        @Nullable MeleeIndexInstance meleeIndexInstance = ResourceApi.getMeleeIndexInstance(meleeLocation);
        if (meleeIndexInstance == null) return 0;

        MeleeData meleeData = meleeIndexInstance.getMeleeData();
        _CombatData combatData = meleeData.getAttackData();
        @Nullable _MeleeAttackInfo meleeAttackInfo = getMeleeAttackInfo(combatData, meleeAction, combo);
        return meleeAttackInfo != null ? meleeAttackInfo.getCooldown() : 0;
    }
    @Override
    default int getMeleeDelay(ItemStack meleeItem, MeleeAction meleeAction, int combo) {
        var meleeLocation = this.getMeleeLocation(meleeItem);
        @Nullable MeleeIndexInstance meleeIndexInstance = ResourceApi.getMeleeIndexInstance(meleeLocation);
        if (meleeIndexInstance == null) return 0;

        MeleeData meleeData = meleeIndexInstance.getMeleeData();
        _CombatData combatData = meleeData.getAttackData();
        @Nullable _MeleeAttackInfo meleeAttackInfo = getMeleeAttackInfo(combatData, meleeAction, combo);
        return meleeAttackInfo != null ? meleeAttackInfo.getDelay() : 0;
    }
    @Override
    default @Nullable _MeleeMovement getMeleeMovement(ItemStack meleeItem, MeleeAction meleeAction) {
        var meleeLocation = this.getMeleeLocation(meleeItem);
        @Nullable MeleeIndexInstance meleeIndexInstance = ResourceApi.getMeleeIndexInstance(meleeLocation);
        if (meleeIndexInstance == null) return null;

        MeleeData meleeData = meleeIndexInstance.getMeleeData();
        _CombatData combatData = meleeData.getAttackData();
        @Nullable _MeleeAttackInfo meleeAttackInfo = getMeleeAttackInfo(combatData, meleeAction, 0);
        return meleeAttackInfo != null ? meleeAttackInfo.getMovement() : null;
    }

    // --------IMeleeStateAccess--------

    @Override
    default boolean hasTooltipMask(ItemStack meleeItem) {
        return NBTUtils.hasKey(meleeItem, MeleeProperty.TOOLTIP_MASK.getTagName());
    }
    @Override
    default int getTooltipMask(ItemStack meleeItem) {
        return NBTUtils.getInt(meleeItem, MeleeProperty.TOOLTIP_MASK.getTagName());
    }
    @Override
    default void setTooltipMask(ItemStack meleeItem, int tooltipMask) {
        NBTUtils.setInt(meleeItem, MeleeProperty.TOOLTIP_MASK.getTagName(), tooltipMask);
    }

    // --------ITacticalDataAccess--------

    @Override
    default int getDrawTime(ItemStack meleeItem) {
        var meleeLocation = this.getMeleeLocation(meleeItem);
        @Nullable MeleeIndexInstance meleeIndexInstance = ResourceApi.getMeleeIndexInstance(meleeLocation);
        if (meleeIndexInstance == null) return 0;

        return meleeIndexInstance.getMeleeData().getDrawTime();
    }
    @Override
    default int getPutAwayTime(ItemStack meleeItem) {
        var meleeLocation = this.getMeleeLocation(meleeItem);
        @Nullable MeleeIndexInstance meleeIndexInstance = ResourceApi.getMeleeIndexInstance(meleeLocation);
        if (meleeIndexInstance == null) return 0;

        return meleeIndexInstance.getMeleeData().getPutAwayTime();
    }
}
