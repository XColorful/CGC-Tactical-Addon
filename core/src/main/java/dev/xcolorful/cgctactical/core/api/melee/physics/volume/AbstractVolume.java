package dev.xcolorful.cgctactical.core.api.melee.physics.volume;

import dev.xcolorful.cgctactical.core.util.HitVolumeUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class AbstractVolume {

    @ApiStatus.Internal
    public static Predicate<Entity> TARGET_ENTITY_FILTER = entity ->
            entity != null
                    && (!(entity instanceof ArmorStand armorStand) || armorStand.isMarker()); // 过滤marker盔甲架

    public static int MAX_CLIP_DISTANCE = 64;

    public AbstractVolume() {
    }

    /**
     * 收集区域内的近战目标
     * <ul>
     *     <li>依次应用施击自身排除、marker盔甲架过滤、形态判定与视线判定</li>
     *     <li>标准检测细节见 {@link HitVolumeUtils}</li>
     * </ul>
     * @param level           实体所在的世界维度
     * @param livingCombatant 发起检测的生物实体
     * @param area            候选实体扫描范围
     * @param excludeSelf     是否排除施击者自身
     * @param shapeTest       判定体积的形态过滤器
     * @return 满足全部条件的目标实体列表
     */
    protected static List<Entity> collectInArea(Level level,
                                                LivingEntity livingCombatant,
                                                AABB area,
                                                boolean excludeSelf,
                                                Predicate<Entity> shapeTest) {
        List<Entity> targets = new ArrayList<>();
        for (Entity entity : level.getEntitiesOfClass(Entity.class, area)) {
            if (excludeSelf && entity == livingCombatant) continue;
            if (!TARGET_ENTITY_FILTER.test(entity)) continue;
            if (!shapeTest.test(entity)) continue;
            if (!HitVolumeUtils.hasLineOfSight(livingCombatant, entity, MAX_CLIP_DISTANCE)) continue;
            targets.add(entity);
        }
        return targets;
    }
}
