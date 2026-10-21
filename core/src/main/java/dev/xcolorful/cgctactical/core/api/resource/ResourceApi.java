package dev.xcolorful.cgctactical.core.api.resource;

import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.cgctactical.client.resource.network.SyncDataCache;
import dev.xcolorful.cgctactical.core.resource._AllDataManager;
import dev.xcolorful.cgctactical.core.resource._DataInstanceManager;
import dev.xcolorful.cgctactical.core.resource.data.data.ConsumableData;
import dev.xcolorful.cgctactical.core.resource.data.data.MeleeData;
import dev.xcolorful.cgctactical.core.resource.data.data.ThrowableData;
import dev.xcolorful.cgctactical.core.resource.data.index.ConsumableIndex;
import dev.xcolorful.cgctactical.core.resource.data.index.MeleeIndex;
import dev.xcolorful.cgctactical.core.resource.data.index.ThrowableIndex;
import dev.xcolorful.cgctactical.core.resource.instance.data.ConsumableIndexInstance;
import dev.xcolorful.cgctactical.core.resource.instance.data.MeleeIndexInstance;
import dev.xcolorful.cgctactical.core.resource.instance.data.ThrowableIndexInstance;
import dev.xcolorful.customgun.core.resource.ResourcePojo;
import dev.xcolorful.customgun.core.resource.instance.PojoInstance;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

/**
 * 资源API
 * <ul>
 *     <li>正常使用请获取返回{@link PojoInstance}的接口</li>
 *     <li>返回{@link ResourcePojo}的接口仅有Pojo自身的validation</li>
 * </ul>
 */
public class ResourceApi {

    // --------data--------

    public static @Nullable ConsumableData getConsumableData(ResourceLocation dataLocation) {
        var dataManager = _AllDataManager.getCurrent();
        if (dataManager != null && dataManager.consumableDataManager != null) return dataManager.consumableDataManager.getPojo(dataLocation);
        else return SyncDataCache.INSTANCE.consumableData.get(dataLocation);
    }
    public static Set<Map.Entry<ResourceLocation, ConsumableData>> getAllConsumableData() {
        var dataManager = _AllDataManager.getCurrent();
        if (dataManager != null && dataManager.consumableDataManager != null) return dataManager.consumableDataManager.getAllPojo().entrySet();
        else return SyncDataCache.INSTANCE.consumableData.entrySet();
    }
    public static @Nullable MeleeData getMeleeData(ResourceLocation dataLocation) {
        var dataManager = _AllDataManager.getCurrent();
        if (dataManager != null && dataManager.meleeDataManager != null) return dataManager.meleeDataManager.getPojo(dataLocation);
        else return SyncDataCache.INSTANCE.meleeData.get(dataLocation);
    }
    public static Set<Map.Entry<ResourceLocation, MeleeData>> getAllMeleeData() {
        var dataManager = _AllDataManager.getCurrent();
        if (dataManager != null && dataManager.meleeDataManager != null) return dataManager.meleeDataManager.getAllPojo().entrySet();
        else return SyncDataCache.INSTANCE.meleeData.entrySet();
    }
    public static @Nullable ThrowableData getThrowableData(ResourceLocation dataLocation) {
        var dataManager = _AllDataManager.getCurrent();
        if (dataManager != null && dataManager.throwableDataManager != null) return dataManager.throwableDataManager.getPojo(dataLocation);
        else return SyncDataCache.INSTANCE.throwableData.get(dataLocation);
    }
    public static Set<Map.Entry<ResourceLocation, ThrowableData>> getAllThrowableData() {
        var dataManager = _AllDataManager.getCurrent();
        if (dataManager != null && dataManager.throwableDataManager != null) return dataManager.throwableDataManager.getAllPojo().entrySet();
        else return SyncDataCache.INSTANCE.throwableData.entrySet();
    }

    // --------index--------

    public static @Nullable ConsumableIndex getConsumableIndex(ResourceLocation consumableLocation) {
        var dataManager = _AllDataManager.getCurrent();
        if (dataManager != null && dataManager.consumableIndexManager != null) return dataManager.consumableIndexManager.getPojo(consumableLocation);
        else return SyncDataCache.INSTANCE.consumableIndex.get(consumableLocation);
    }
    public static Set<Map.Entry<ResourceLocation, ConsumableIndex>> getAllConsumableIndex() {
        var dataManager = _AllDataManager.getCurrent();
        if (dataManager != null && dataManager.consumableIndexManager != null) return dataManager.consumableIndexManager.getAllPojo().entrySet();
        else return SyncDataCache.INSTANCE.consumableIndex.entrySet();
    }
    public static @Nullable MeleeIndex getMeleeIndex(ResourceLocation meleeLocation) {
        var dataManager = _AllDataManager.getCurrent();
        if (dataManager != null && dataManager.meleeIndexManager != null) return dataManager.meleeIndexManager.getPojo(meleeLocation);
        else return SyncDataCache.INSTANCE.meleeIndex.get(meleeLocation);
    }
    public static Set<Map.Entry<ResourceLocation, MeleeIndex>> getAllMeleeIndex() {
        var dataManager = _AllDataManager.getCurrent();
        if (dataManager != null && dataManager.meleeIndexManager != null) return dataManager.meleeIndexManager.getAllPojo().entrySet();
        else return SyncDataCache.INSTANCE.meleeIndex.entrySet();
    }
    public static @Nullable ThrowableIndex getThrowableIndex(ResourceLocation throwableLocation) {
        var dataManager = _AllDataManager.getCurrent();
        if (dataManager != null && dataManager.throwableIndexManager != null) return dataManager.throwableIndexManager.getPojo(throwableLocation);
        else return SyncDataCache.INSTANCE.throwableIndex.get(throwableLocation);
    }
    public static Set<Map.Entry<ResourceLocation, ThrowableIndex>> getAllThrowableIndex() {
        var dataManager = _AllDataManager.getCurrent();
        if (dataManager != null && dataManager.throwableIndexManager != null) return dataManager.throwableIndexManager.getAllPojo().entrySet();
        else return SyncDataCache.INSTANCE.throwableIndex.entrySet();
    }

    // --------data instance--------

    public static @Nullable ConsumableIndexInstance getConsumableIndexInstance(ResourceLocation consumableLocation) {
        if (CustomGun.getSideExecutor().getLogicalSide().isServer()) return _DataInstanceManager.CONSUMABLE_INDEX.get(consumableLocation);
        else return SyncDataCache.INSTANCE.CONSUMABLE_INDEX.get(consumableLocation);
    }
    public static Set<Map.Entry<ResourceLocation, @NotNull ConsumableIndexInstance>> getAllConsumableIndexInstance() {
        if (CustomGun.getSideExecutor().getLogicalSide().isServer()) return _DataInstanceManager.CONSUMABLE_INDEX.entrySet();
        else return SyncDataCache.INSTANCE.CONSUMABLE_INDEX.entrySet();
    }
    public static @Nullable MeleeIndexInstance getMeleeIndexInstance(ResourceLocation meleeLocation) {
        if (CustomGun.getSideExecutor().getLogicalSide().isServer()) return _DataInstanceManager.MELEE_INDEX.get(meleeLocation);
        else return SyncDataCache.INSTANCE.MELEE_INDEX.get(meleeLocation);
    }
    public static Set<Map.Entry<ResourceLocation, @NotNull MeleeIndexInstance>> getAllMeleeIndexInstance() {
        if (CustomGun.getSideExecutor().getLogicalSide().isServer()) return _DataInstanceManager.MELEE_INDEX.entrySet();
        else return SyncDataCache.INSTANCE.MELEE_INDEX.entrySet();
    }
    public static @Nullable ThrowableIndexInstance getThrowableIndexInstance(ResourceLocation throwableLocation) {
        if (CustomGun.getSideExecutor().getLogicalSide().isServer()) return _DataInstanceManager.THROWABLE_INDEX.get(throwableLocation);
        else return SyncDataCache.INSTANCE.THROWABLE_INDEX.get(throwableLocation);
    }
    public static Set<Map.Entry<ResourceLocation, @NotNull ThrowableIndexInstance>> getAllThrowableIndexInstance() {
        if (CustomGun.getSideExecutor().getLogicalSide().isServer()) return _DataInstanceManager.THROWABLE_INDEX.entrySet();
        else return SyncDataCache.INSTANCE.THROWABLE_INDEX.entrySet();
    }
}
