package dev.xcolorful.cgctactical.core.api.melee.physics;

import dev.xcolorful.cgctactical.core.resource.data.data.melee._TargetFilterData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IHitVolume {

    /**
     * 收集判定体积内的目标实体
     * <ul>
     *     <li>各实现按自身判定体积（cone/ray/obb）筛选候选实体</li>
     *     <li>射线判定还需按命中距离由近到远应用 {@link _TargetFilterData#getPierce()} 数量上限</li>
     * </ul>
     * @param level            判定所在的世界维度
     * @param livingCombatant  发起攻击的生物实体
     * @param targetFilterData 判定体积类型、范围、角度、穿透等配置
     * @param origin           判定起点，通常为施击者眼部坐标
     * @param direction        判定朝向，通常为施击者视线方向
     * @return 命中的目标实体列表，顺序由各判定体积的语义决定
     */
    @NotNull List<Entity> collectTargets(Level level,
                                         LivingEntity livingCombatant,
                                         _TargetFilterData targetFilterData,
                                         Vec3 origin, Vec3 direction);
}
