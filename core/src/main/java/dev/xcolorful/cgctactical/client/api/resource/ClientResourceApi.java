package dev.xcolorful.cgctactical.client.api.resource;

import dev.xcolorful.cgctactical.client.resource._AllAssetsManager;
import dev.xcolorful.cgctactical.client.resource._AssetsInstanceManager;
import dev.xcolorful.cgctactical.client.resource.assets.display.ConsumableDisplay;
import dev.xcolorful.cgctactical.client.resource.assets.display.MeleeDisplay;
import dev.xcolorful.cgctactical.client.resource.assets.display.ThrowableDisplay;
import dev.xcolorful.cgctactical.client.resource.instance.assets.ConsumableDisplayInstance;
import dev.xcolorful.cgctactical.client.resource.instance.assets.MeleeDisplayInstance;
import dev.xcolorful.cgctactical.client.resource.instance.assets.ThrowableDisplayInstance;
import dev.xcolorful.cgctactical.client.resource.instance.data.ClientConsumableIndexInstance;
import dev.xcolorful.cgctactical.client.resource.instance.data.ClientMeleeIndexInstance;
import dev.xcolorful.cgctactical.client.resource.instance.data.ClientThrowableIndexInstance;
import dev.xcolorful.customgun.core.resource.ResourcePojo;
import dev.xcolorful.customgun.core.resource.instance.PojoInstance;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 客户端资源API
 * <ul>
 *     <li>正常使用请获取返回{@link PojoInstance}的接口</li>
 *     <li>返回{@link ResourcePojo}的接口仅有Pojo自身的validation</li>
 * </ul>
 */
public class ClientResourceApi {

    // --------display--------

    public static @Nullable ConsumableDisplay getConsumableDisplay(ResourceLocation displayLocation) {
        var assetsManager = _AllAssetsManager.INSTANCE.consumableDisplayManager;
        return assetsManager != null ? assetsManager.getPojo(displayLocation) : null;
    }
    public static Set<Map.Entry<ResourceLocation, ConsumableDisplay>> getAllConsumableDisplay() {
        var assetsManager = _AllAssetsManager.INSTANCE.consumableDisplayManager;
        return assetsManager != null ? assetsManager.getAllPojo().entrySet() : new HashSet<>();
    }
    public static @Nullable MeleeDisplay getMeleeDisplay(ResourceLocation displayLocation) {
        var assetsManager = _AllAssetsManager.INSTANCE.meleeDisplayManager;
        return assetsManager != null ? assetsManager.getPojo(displayLocation) : null;
    }
    public static Set<Map.Entry<ResourceLocation, MeleeDisplay>> getAllMeleeDisplay() {
        var assetsManager = _AllAssetsManager.INSTANCE.meleeDisplayManager;
        return assetsManager != null ? assetsManager.getAllPojo().entrySet() : new HashSet<>();
    }
    public static @Nullable ThrowableDisplay getThrowableDisplay(ResourceLocation displayLocation) {
        var assetsManager = _AllAssetsManager.INSTANCE.throwableDisplayManager;
        return assetsManager != null ? assetsManager.getPojo(displayLocation) : null;
    }
    public static Set<Map.Entry<ResourceLocation, ThrowableDisplay>> getAllThrowableDisplay() {
        var assetsManager = _AllAssetsManager.INSTANCE.throwableDisplayManager;
        return assetsManager != null ? assetsManager.getAllPojo().entrySet() : new HashSet<>();
    }

    // --------data instance--------

    public static @Nullable ClientConsumableIndexInstance getClientConsumableIndexInstance(ResourceLocation consumableLocation) {
        return _AssetsInstanceManager.CONSUMABLE_INDEX.get(consumableLocation);
    }
    public static Set<Map.Entry<ResourceLocation, @NotNull ClientConsumableIndexInstance>> getAllClientConsumableIndexInstance() {
        return _AssetsInstanceManager.CONSUMABLE_INDEX.entrySet();
    }
    public static @Nullable ClientMeleeIndexInstance getClientMeleeIndexInstance(ResourceLocation meleeLocation) {
        return _AssetsInstanceManager.MELEE_INDEX.get(meleeLocation);
    }
    public static Set<Map.Entry<ResourceLocation, @NotNull ClientMeleeIndexInstance>> getAllClientMeleeIndexInstance() {
        return _AssetsInstanceManager.MELEE_INDEX.entrySet();
    }
    public static @Nullable ClientThrowableIndexInstance getClientThrowableIndexInstance(ResourceLocation throwableLocation) {
        return _AssetsInstanceManager.THROWABLE_INDEX.get(throwableLocation);
    }
    public static Set<Map.Entry<ResourceLocation, @NotNull ClientThrowableIndexInstance>> getAllClientThrowableIndexInstance() {
        return _AssetsInstanceManager.THROWABLE_INDEX.entrySet();
    }

    // --------assets instance--------

    public static @Nullable ConsumableDisplayInstance getConsumableDisplayInstance(ResourceLocation displayLocation) {
        return _AssetsInstanceManager.CONSUMABLE_DISPLAY.get(displayLocation);
    }
    public static Set<Map.Entry<ResourceLocation, @NotNull ConsumableDisplayInstance>> getAllConsumableDisplayInstance() {
        return _AssetsInstanceManager.CONSUMABLE_DISPLAY.entrySet();
    }
    public static @Nullable MeleeDisplayInstance getMeleeDisplayInstance(ResourceLocation displayLocation) {
        return _AssetsInstanceManager.MELEE_DISPLAY.get(displayLocation);
    }
    public static Set<Map.Entry<ResourceLocation, @NotNull MeleeDisplayInstance>> getAllMeleeDisplayInstance() {
        return _AssetsInstanceManager.MELEE_DISPLAY.entrySet();
    }
    public static @Nullable ThrowableDisplayInstance getThrowableDisplayInstance(ResourceLocation displayLocation) {
        return _AssetsInstanceManager.THROWABLE_DISPLAY.get(displayLocation);
    }
    public static Set<Map.Entry<ResourceLocation, @NotNull ThrowableDisplayInstance>> getAllThrowableDisplayInstance() {
        return _AssetsInstanceManager.THROWABLE_DISPLAY.entrySet();
    }
}
