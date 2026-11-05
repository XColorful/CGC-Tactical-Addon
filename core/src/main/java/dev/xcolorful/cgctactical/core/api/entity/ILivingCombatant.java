package dev.xcolorful.cgctactical.core.api.entity;

import dev.xcolorful.cgctactical.core.api.entity.combatant.ILivingCombatantConfig;

/*
文档译名：战斗生物 (XiaoColorful译); 否决译名：战斗者, 战术生物
 */
public interface ILivingCombatant extends ILivingCombatantConfig {

    /**
     * 初始化战斗操作的各个数据
     */
    void cgctactical$initLivingCombatant();

    CombatantProperty cgctactical$getCombatantProperty();
}
