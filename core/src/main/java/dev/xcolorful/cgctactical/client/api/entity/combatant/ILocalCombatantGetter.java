package dev.xcolorful.cgctactical.client.api.entity.combatant;

import dev.xcolorful.cgctactical.client.api.entity.ILocalCombatant;
import dev.xcolorful.cgctactical.client.mixin.entity.LocalPlayerMixin;
import net.minecraft.client.player.LocalPlayer;

/**
 * @deprecated 暂未使用
 */
@Deprecated
public interface ILocalCombatantGetter {

    /**
     * {@link LocalPlayerMixin} 通过 Mixin 的方式实现了 {@link ILocalCombatant} 接口
     */
    static ILocalCombatant cgctactical$fromLocalPlayer(LocalPlayer localPlayer) {
        return (ILocalCombatant) localPlayer;
    }
}
