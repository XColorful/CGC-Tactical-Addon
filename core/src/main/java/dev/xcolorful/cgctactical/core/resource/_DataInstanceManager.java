package dev.xcolorful.cgctactical.core.resource;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.resource.instance.data.ConsumableIndexInstance;
import dev.xcolorful.cgctactical.core.resource.instance.data.MeleeIndexInstance;
import dev.xcolorful.cgctactical.core.resource.instance.data.ThrowableIndexInstance;
import dev.xcolorful.customgun.core.resource.ResourcePojo;
import dev.xcolorful.customgun.core.resource.instance.PojoInstance;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * 存放Pojo二次校验后的实例，直接丢弃索引无效的ResourceLocation
 * <p>
 * Pojo自身的校验只包含自身(可并发各自同时校验)的类型检查，valid只保证自身接口的@Nullable/@NotNull生效，不保证跨Pojo索引生效
 */
@ApiStatus.Internal
public class _DataInstanceManager {

    // data
    public static final Map<ResourceLocation, ConsumableIndexInstance> CONSUMABLE_INDEX = new HashMap<>();
    public static final Map<ResourceLocation, MeleeIndexInstance> MELEE_INDEX = new HashMap<>();
    public static final Map<ResourceLocation, ThrowableIndexInstance> THROWABLE_INDEX = new HashMap<>();

    private _DataInstanceManager() {}

    /**
     * 主线程操作(线程不安全)
     */
    public static void clear() {
        CONSUMABLE_INDEX.clear();
        MELEE_INDEX.clear();
        THROWABLE_INDEX.clear();
    }
    /**
     * 主线程操作(线程不安全)
     */
    public static void reload() {
        clear();

        buildPojoInstance(ResourceApi.getAllConsumableIndex(), CONSUMABLE_INDEX, ConsumableIndexInstance::fromPojo, ConsumableIndexInstance.class);
        buildPojoInstance(ResourceApi.getAllMeleeIndex(), MELEE_INDEX, MeleeIndexInstance::fromPojo, MeleeIndexInstance.class);
        buildPojoInstance(ResourceApi.getAllThrowableIndex(), THROWABLE_INDEX, ThrowableIndexInstance::fromPojo, ThrowableIndexInstance.class);
    }
    public static <T extends ResourcePojo<T>, I extends PojoInstance<T>> void buildPojoInstance(
            Set<Map.Entry<ResourceLocation, T>> entries,
            Map<ResourceLocation, I> targetMap,
            Function<T, @Nullable I> instanceFactory,
            Class<I> instanceClass) {

        for (Map.Entry<ResourceLocation, T> entry : entries) {
            var location = entry.getKey();
            T pojo = entry.getValue();
            I instance = instanceFactory.apply(pojo);
            if (instance != null) targetMap.put(location, instance);
            else CgcTactical.LOGGER.warn("{} {} validation failed", instanceClass, location);
        }
    }
}
