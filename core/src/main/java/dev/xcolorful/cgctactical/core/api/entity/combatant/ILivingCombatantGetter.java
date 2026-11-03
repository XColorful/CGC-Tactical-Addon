package dev.xcolorful.cgctactical.core.api.entity.combatant;

import dev.xcolorful.cgctactical.core.api.entity.ILivingCombatant;
import dev.xcolorful.cgctactical.core.mixin.entity.LivingEntityMixin;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ILivingCombatantGetter {

    static @Nullable ILivingCombatant cgctactical$fromEntity(@Nullable Entity entity) {
        return entity instanceof ILivingCombatant iLivingCombatant ? iLivingCombatant : null;
    }

    /**
     * {@link LivingEntityMixin} mixin到LivingEntity实现该接口
     */
    static @NotNull ILivingCombatant cgctactical$fromLivingEntity(@NotNull LivingEntity livingEntity) {
        return (ILivingCombatant) livingEntity;
    }
}
