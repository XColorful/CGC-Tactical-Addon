package dev.xcolorful.cgctactical.client.resource;

import dev.xcolorful.cgctactical.client.api.resource.ClientResourceApi;
import dev.xcolorful.cgctactical.client.resource.instance.assets.ConsumableDisplayInstance;
import dev.xcolorful.cgctactical.client.resource.instance.assets.MeleeDisplayInstance;
import dev.xcolorful.cgctactical.client.resource.instance.assets.ThrowableDisplayInstance;
import dev.xcolorful.cgctactical.client.resource.instance.data.ClientConsumableIndexInstance;
import dev.xcolorful.cgctactical.client.resource.instance.data.ClientMeleeIndexInstance;
import dev.xcolorful.cgctactical.client.resource.instance.data.ClientThrowableIndexInstance;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;

import static dev.xcolorful.customgun.core.resource._DataInstanceManager.buildPojoInstance;

/**
 * 存放Pojo二次校验后的实例，直接丢弃索引无效的ResourceLocation
 * <p>
 * Pojo自身的校验只包含自身(可并发各自同时校验)的类型检查，valid只保证自身接口的@Nullable/@NotNull生效，不保证跨Pojo索引生效
 */
@ApiStatus.Internal
public class _AssetsInstanceManager {

    // data
    public static final Map<Identifier, ClientConsumableIndexInstance> CONSUMABLE_INDEX = new HashMap<>();
    public static final Map<Identifier, ClientMeleeIndexInstance> MELEE_INDEX = new HashMap<>();
    public static final Map<Identifier, ClientThrowableIndexInstance> THROWABLE_INDEX = new HashMap<>();

    // assets
    public static final Map<Identifier, ConsumableDisplayInstance> CONSUMABLE_DISPLAY = new HashMap<>(); // displayLocation -> ConsumableDisplay
    public static final Map<Identifier, MeleeDisplayInstance> MELEE_DISPLAY = new HashMap<>();
    public static final Map<Identifier, ThrowableDisplayInstance> THROWABLE_DISPLAY = new HashMap<>();

    private _AssetsInstanceManager() {}

    /**
     * 主线程操作(线程不安全)
     */
    public static void clear() {
        CONSUMABLE_INDEX.clear();
        MELEE_INDEX.clear();
        THROWABLE_INDEX.clear();
        CONSUMABLE_DISPLAY.clear();
        MELEE_DISPLAY.clear();
        THROWABLE_DISPLAY.clear();
    }
    /**
     * 主线程操作(线程不安全)
     */
    public static void reload() {
        clear();

        buildPojoInstance(ResourceApi.getAllConsumableIndex(), CONSUMABLE_INDEX, ClientConsumableIndexInstance::fromPojo, ClientConsumableIndexInstance.class);
        buildPojoInstance(ResourceApi.getAllMeleeIndex(), MELEE_INDEX, ClientMeleeIndexInstance::fromPojo, ClientMeleeIndexInstance.class);
        buildPojoInstance(ResourceApi.getAllThrowableIndex(), THROWABLE_INDEX, ClientThrowableIndexInstance::fromPojo, ClientThrowableIndexInstance.class);

        buildPojoInstance(ClientResourceApi.getAllConsumableDisplay(), CONSUMABLE_DISPLAY, ConsumableDisplayInstance::fromPojo, ConsumableDisplayInstance.class);
        buildPojoInstance(ClientResourceApi.getAllMeleeDisplay(), MELEE_DISPLAY, MeleeDisplayInstance::fromPojo, MeleeDisplayInstance.class);
        buildPojoInstance(ClientResourceApi.getAllThrowableDisplay(), THROWABLE_DISPLAY, ThrowableDisplayInstance::fromPojo, ThrowableDisplayInstance.class);
    }
}
