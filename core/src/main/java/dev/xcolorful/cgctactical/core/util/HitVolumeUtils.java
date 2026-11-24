package dev.xcolorful.cgctactical.core.util;

import dev.xcolorful.customgun.core.util.RayTraceUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import org.joml.Intersectionf;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class HitVolumeUtils {

    /**
     * 沿射线路径检测实体
     * <ul>
     *     <li>标准检测直接复用 {@link RayTraceUtils.EntityTrace#rayTraceEntities}</li>
     *     <li>其未涵盖“射线起点已位于目标包围盒内”的情形，此处补足并按离射线起点由近到远排序</li>
     *     <li>便于调用方按命中顺序应用穿透数量上限</li>
     * </ul>
     * @param contextEntity     进行检测的基准实体（获取世界与延迟补偿基准）
     * @param startPos          射线起点
     * @param endPos            射线终点
     * @param inflateRadius     轴向额外扩充半径
     * @param targetPredicate   实体过滤器
     * @param visiblePredicate  视线过滤器，仅在射线实际命中后调用，返回 {@code false} 则丢弃该命中
     * @return 按命中距离升序排列的命中结果
     */
    public static List<EntityHitResult> rayTraceEntities(Entity contextEntity,
                                                         Vec3 startPos, Vec3 endPos,
                                                         double inflateRadius,
                                                         Predicate<Entity> targetPredicate,
                                                         Predicate<Entity> visiblePredicate) {
        List<EntityHitResult> hitResults = RayTraceUtils.EntityTrace.rayTraceEntities(
                contextEntity,
                contextEntity,
                startPos,
                endPos,
                inflateRadius,
                targetPredicate,
                (entity, hitPos) -> visiblePredicate.test(entity)
                        ? new EntityHitResult(entity, hitPos)
                        : null
        );
        // 起点已位于目标包围盒内时 clip 不会返回交点，参考 ITargetFilter 将其视为命中
        AABB searchBox = RayTraceUtils.EntityTrace.createTraceBox(contextEntity, startPos, endPos, inflateRadius);
        for (Entity entity : contextEntity.level().getEntities(contextEntity, searchBox, targetPredicate)) {
            if (!entity.getBoundingBox().contains(startPos)) continue;
            if (hitResults.stream().anyMatch(result -> result.getEntity() == entity)) continue;
            if (!visiblePredicate.test(entity)) continue;
            hitResults.add(new EntityHitResult(entity, startPos));
        }
        hitResults.sort(Comparator.comparingDouble(result -> result.getLocation().distanceToSqr(startPos)));
        return hitResults;
    }

    /**
     * 判断施击者与目标之间是否存在无方块遮挡的视线
     * <ul>
     *     <li>先检测从施击者眼部到目标眼部的连线</li>
     *     <li>若存在遮挡，则进一步检测施击者眼部到目标包围盒八个顶点的连线</li>
     *     <li>任意一条未被碰撞方块阻挡即返回 {@code true}</li>
     * </ul>
     * @param livingCombatant 发起检测的生物实体，作为视线起点
     * @param target          被检测的目标实体
     * @param maxClipDistance 视线检测的最大距离
     * @return 若处于同一维度、距离在检测上限内且至少有一条视线路径未被方块阻挡则返回 {@code true}，否则返回 {@code false}
     */
    public static boolean hasLineOfSight(LivingEntity livingCombatant, Entity target, int maxClipDistance) {
        Level level = livingCombatant.level();
        if (level != target.level()) return false;

        // 检测两人眼睛之间直通
        Vec3 origin = new Vec3(livingCombatant.getX(), livingCombatant.getEyeY(), livingCombatant.getZ());
        if (clip(level, livingCombatant, origin, new Vec3(target.getX(), target.getEyeY(), target.getZ()), maxClipDistance)) {
            return true;
        }

        // 检测到碰撞箱任意顶点直通
        AABB box = target.getBoundingBox();
        double[] xs = {box.minX, box.maxX};
        double[] ys = {box.minY, box.maxY};
        double[] zs = {box.minZ, box.maxZ};

        for (int i = 0; i < 8; i++) {
            Vec3 corner = new Vec3(xs[i & 1], ys[(i >> 1) & 1], zs[(i >> 2) & 1]);
            if (clip(level, livingCombatant, origin, corner, maxClipDistance)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测从起点到终点之间是否被具有碰撞箱的方块遮挡
     * <ul>
     *     <li>与 {@link dev.xcolorful.customgun.core.projectile.physics.ProjectilePhysicsManager} 一致</li>
     *     <li>通过 {@link RayTraceUtils.BlockTrace#rayTraceBlocksWithFilter} 完成方块射线检测</li>
     * </ul>
     * @param level           实体所在的世界维度
     * @param livingCombatant 执行射线检测的实体上下文
     * @param start           射线起点坐标
     * @param end             射线终点坐标
     * @param maxClipDistance 方块检测的最大距离
     * @return 若终点在最大检测距离内且射线未命中任何碰撞方块则返回 {@code true}，否则返回 {@code false}
     */
    public static boolean clip(Level level, LivingEntity livingCombatant, Vec3 start, Vec3 end, int maxClipDistance) {
        if (start.distanceToSqr(end) > maxClipDistance * maxClipDistance) {
            return false;
        }
        ClipContext context = new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, livingCombatant);
        BlockHitResult blockHitResult = RayTraceUtils.BlockTrace.rayTraceBlocksWithFilter(
                level,
                context.getFrom(),
                context.getTo(),
                (blockState, blockPos) -> context.getBlockShape(blockState, level, blockPos),
                (fluidState, blockPos) -> context.getFluidShape(fluidState, level, blockPos),
                blockState -> false
        );
        return blockHitResult.getType() == HitResult.Type.MISS;
    }

    /**
     * 计算点到某AABB的最短路径向量
     * @param point 起点
     * @param box   AABB
     */
    public static Vec3 distanceVector(Vec3 point, AABB box) {
        double dx = 0;
        if (box.minX > point.x) {
            dx = box.minX - point.x;
        } else if (box.maxX < point.x) {
            dx = box.maxX - point.x;
        }
        double dy = 0;
        if (box.minY > point.y) {
            dy = box.minY - point.y;
        } else if (box.maxY < point.y) {
            dy = box.maxY - point.y;
        }
        double dz = 0;
        if (box.minZ > point.z) {
            dz = box.minZ - point.z;
        } else if (box.maxZ < point.z) {
            dz = box.maxZ - point.z;
        }
        return new Vec3(dx, dy, dz);
    }

    /**
     * 计算两个向量之间的夹角（角度制）
     */
    public static double angleBetween(Vec3 v1, Vec3 v2) {
        return Math.toDegrees(Math.acos(v1.normalize().dot(v2.normalize())));
    }

    /**
     * 判断目标是否位于扇形判定范围内
     * @param origin      扇形顶点
     * @param view        扇形朝向
     * @param target      目标实体
     * @param halfAngle   半锥角（角度制）
     * @param maxDistance 最大检测距离
     * @return 目标到包围盒的最近距离超过检测距离时返回 {@code false}；否则最近路径方向或实体中心方向任一落入半锥角即返回 {@code true}
     */
    public static boolean isInAngle(Vec3 origin, Vec3 view, Entity target, double halfAngle, double maxDistance) {
        Vec3 positionVector = target.position().add(0, target.getBbHeight() / 2F, 0).subtract(origin);

        Vec3 distanceVector = distanceVector(origin, target.getBoundingBox());
        double distance = distanceVector.length();

        if (distance == 0) return true;

        if (distance > maxDistance) {
            return false;
        }

        // 满足最短路或者实体中心在最大夹角内任一条件即可
        return angleBetween(view, distanceVector) <= halfAngle || angleBetween(view, positionVector) <= halfAngle;
    }

    /**
     * 判断有向包围盒与轴对齐包围盒是否相交
     * @param center   有向包围盒中心
     * @param extents  有向包围盒三个轴向上的半长
     * @param rotation 有向包围盒旋转
     * @param aabb     待检测的轴对齐包围盒
     */
    public static boolean obbIntersectsAABB(Vector3f center, Vector3f extents, Quaternionf rotation, AABB aabb) {
        Vector3f axisX = rotation.transform(new Vector3f(1, 0, 0));
        Vector3f axisY = rotation.transform(new Vector3f(0, 1, 0));
        Vector3f axisZ = rotation.transform(new Vector3f(0, 0, 1));

        Vector3f aabbCenter = aabb.getCenter().toVector3f();
        Vector3f aabbExtents = new Vector3f(
                (float) (aabb.getXsize() / 2.0),
                (float) (aabb.getYsize() / 2.0),
                (float) (aabb.getZsize() / 2.0)
        );

        return Intersectionf.testObOb(
                center.x, center.y, center.z,
                axisX.x, axisX.y, axisX.z,
                axisY.x, axisY.y, axisY.z,
                axisZ.x, axisZ.y, axisZ.z,
                extents.x, extents.y, extents.z,
                aabbCenter.x, aabbCenter.y, aabbCenter.z,
                1, 0, 0,
                0, 1, 0,
                0, 0, 1,
                aabbExtents.x, aabbExtents.y, aabbExtents.z
        );
    }
}
