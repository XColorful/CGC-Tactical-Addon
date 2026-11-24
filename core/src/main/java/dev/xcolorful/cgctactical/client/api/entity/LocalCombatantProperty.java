package dev.xcolorful.cgctactical.client.api.entity;

import dev.xcolorful.cgctactical.core.api.entity.CombatantProperty;
import net.minecraft.client.player.LocalPlayer;

/**
 * @deprecated 暂未使用
 */
@Deprecated
public class LocalCombatantProperty extends CombatantProperty {

    public LocalCombatantProperty(LocalPlayer entity) {
        super(entity);
    }
}
