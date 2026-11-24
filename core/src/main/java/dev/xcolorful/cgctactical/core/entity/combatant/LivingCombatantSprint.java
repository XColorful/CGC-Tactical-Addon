package dev.xcolorful.cgctactical.core.entity.combatant;

import dev.xcolorful.cgctactical.core.api.entity.CombatantProperty;
import dev.xcolorful.cgctactical.core.api.item.IMelee;
import dev.xcolorful.cgctactical.core.api.item.melee.IMeleeGetter;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public final class LivingCombatantSprint extends LivingCombatantAspect {

    public LivingCombatantSprint(LivingEntity livingCombatant, CombatantProperty combatantProperty) {
        super(livingCombatant, combatantProperty);
    }

    public void tickSprint() {
        ItemStack meleeItem = this.livingCombatant.getMainHandItem();
        @Nullable IMelee iMelee = IMeleeGetter.fromItemStack(meleeItem);
        if (iMelee == null) return;

        if (
                // 禁止攻击时疾跑
                !iMelee.canSprintingAttack()
                // 正在攻击 且 在疾跑
                && this.combatantProperty.preparingAttack && this.livingCombatant.isSprinting()
        ) {
            /*
            只在状态确实需要改变时才调用：
            LivingEntity.setSprinting 每次都会先移除再挂回冲刺移速修饰符，把 MOVEMENT_SPEED 属性标脏
            服务端随后会把属性快照发给玩家本人（ServerEntity#broadcastAndSend）
            每tick重复调用会让客户端属性反复被服务端快照覆盖，与原版移速 FOV 冲突（骑乘时尤为明显）
             */
            this.livingCombatant.setSprinting(false);
        }
    }
}
