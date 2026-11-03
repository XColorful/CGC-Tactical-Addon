package dev.xcolorful.cgctactical.client.resource.network;

import com.google.gson.stream.JsonReader;
import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.resource.data.data.ConsumableData;
import dev.xcolorful.cgctactical.core.resource.data.data.MeleeData;
import dev.xcolorful.cgctactical.core.resource.data.data.ThrowableData;
import dev.xcolorful.cgctactical.core.resource.data.index.ConsumableIndex;
import dev.xcolorful.cgctactical.core.resource.data.index.MeleeIndex;
import dev.xcolorful.cgctactical.core.resource.data.index.ThrowableIndex;
import dev.xcolorful.cgctactical.core.resource.instance.data.ConsumableIndexInstance;
import dev.xcolorful.cgctactical.core.resource.instance.data.MeleeIndexInstance;
import dev.xcolorful.cgctactical.core.resource.instance.data.ThrowableIndexInstance;
import dev.xcolorful.cgctactical.core.resource.network.SyncDataType;
import dev.xcolorful.customgun.core.resource.ResourcePojo;
import dev.xcolorful.customgun.core.util.JsonUtils;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static dev.xcolorful.customgun.core.resource._DataInstanceManager.buildPojoInstance;

public final class SyncDataCache {
    public static final SyncDataCache INSTANCE = new SyncDataCache();
    private SyncDataCache() {}

    /**
     * ./datapacks/{datapack}/data/{namespace}/{@link dev.xcolorful.customgun.core.api.resource.data.DataFolderType#DATA}
     */
    public @NotNull volatile Map<Identifier, ConsumableData> consumableData = new HashMap<>();
    public @NotNull volatile Map<Identifier, MeleeData> meleeData = new HashMap<>();
    public @NotNull volatile Map<Identifier, ThrowableData> throwableData = new HashMap<>();
    /**
     * ./datapacks/{datapack}/data/{namespace}/{@link dev.xcolorful.customgun.core.api.resource.data.DataFolderType#INDEX}
     */
    public @NotNull volatile Map<Identifier, ConsumableIndex> consumableIndex = new HashMap<>();
    public @NotNull volatile Map<Identifier, MeleeIndex> meleeIndex = new HashMap<>();
    public @NotNull volatile Map<Identifier, ThrowableIndex> throwableIndex = new HashMap<>();

    // data instance
    public final Map<Identifier, ConsumableIndexInstance> CONSUMABLE_INDEX = new HashMap<>();
    public final Map<Identifier, MeleeIndexInstance> MELEE_INDEX = new HashMap<>();
    public final Map<Identifier, ThrowableIndexInstance> THROWABLE_INDEX = new HashMap<>();

    @ApiStatus.Internal
    public void clear() {
        this.consumableData = new HashMap<>();
        this.meleeData = new HashMap<>();
        this.throwableData = new HashMap<>();

        this.consumableIndex = new HashMap<>();
        this.meleeIndex = new HashMap<>();
        this.throwableIndex = new HashMap<>();

        // data instance
        this.CONSUMABLE_INDEX.clear();
        this.MELEE_INDEX.clear();
        this.THROWABLE_INDEX.clear();
    }

    /**
     * 线程不安全（由主线程或者通过确定无竞态的 Ticket 触发）
     */
    @SuppressWarnings("unchecked")
    @ApiStatus.Internal
    public void setParseResult(Map<SyncDataType, Map<Identifier, ? extends ResourcePojo<?>>> result) {
        if (result == null) return;

        this.consumableData = (Map<Identifier, ConsumableData>) result.getOrDefault(SyncDataType.CONSUMABLE_DATA, new HashMap<>());
        this.meleeData = (Map<Identifier, MeleeData>) result.getOrDefault(SyncDataType.MELEE_DATA, new HashMap<>());
        this.throwableData = (Map<Identifier, ThrowableData>) result.getOrDefault(SyncDataType.THROWABLE_DATA, new HashMap<>());

        this.consumableIndex = (Map<Identifier, ConsumableIndex>) result.getOrDefault(SyncDataType.CONSUMABLE_INDEX, new HashMap<>());
        this.meleeIndex = (Map<Identifier, MeleeIndex>) result.getOrDefault(SyncDataType.MELEE_INDEX, new HashMap<>());
        this.throwableIndex = (Map<Identifier, ThrowableIndex>) result.getOrDefault(SyncDataType.THROWABLE_INDEX, new HashMap<>());

        // data instance
        buildPojoInstance(ResourceApi.getAllConsumableIndex(), this.CONSUMABLE_INDEX, ConsumableIndexInstance::fromPojo, ConsumableIndexInstance.class);
        buildPojoInstance(ResourceApi.getAllMeleeIndex(), this.MELEE_INDEX, MeleeIndexInstance::fromPojo, MeleeIndexInstance.class);
        buildPojoInstance(ResourceApi.getAllThrowableIndex(), this.THROWABLE_INDEX, ThrowableIndexInstance::fromPojo, ThrowableIndexInstance.class);
    }
    /**
     * 线程安全
     */
    public Map<SyncDataType, Map<Identifier, ? extends ResourcePojo<?>>> rebuildFromNetworkAsync(Map<SyncDataType, Map<Identifier, String>> cache) {
        Map<SyncDataType, Map<Identifier, ? extends ResourcePojo<?>>> resultMap = new HashMap<>();
        for (Map.Entry<SyncDataType, Map<Identifier, String>> entry : cache.entrySet()) {
            switch (entry.getKey()) {
                case CONSUMABLE_INDEX -> resultMap.put(SyncDataType.CONSUMABLE_INDEX, parseFromNetworkAsync(entry.getValue(), ConsumableIndex::fromJson));
                case MELEE_INDEX -> resultMap.put(SyncDataType.MELEE_INDEX, parseFromNetworkAsync(entry.getValue(), MeleeIndex::fromJson));
                case THROWABLE_INDEX -> resultMap.put(SyncDataType.THROWABLE_INDEX, parseFromNetworkAsync(entry.getValue(), ThrowableIndex::fromJson));
                case CONSUMABLE_DATA -> resultMap.put(SyncDataType.CONSUMABLE_DATA, parseFromNetworkAsync(entry.getValue(), ConsumableData::fromJson));
                case MELEE_DATA -> resultMap.put(SyncDataType.MELEE_DATA, parseFromNetworkAsync(entry.getValue(), MeleeData::fromJson));
                case THROWABLE_DATA -> resultMap.put(SyncDataType.THROWABLE_DATA, parseFromNetworkAsync(entry.getValue(), ThrowableData::fromJson));
//                default -> ; // 如果添加了同步类型(枚举)，强制此处编译不通过
            }
        }
        return resultMap;
    }

    private <T extends ResourcePojo<T>> Map<Identifier, T> parseFromNetworkAsync(Map<Identifier, String> cache,
                                                                                       JsonUtils.ReadFunction<T> parser) {
        Map<Identifier, T> map = new HashMap<>();
        for (Map.Entry<Identifier, String> entry : cache.entrySet()) {
            String jsonStr = entry.getValue();
            if (jsonStr == null || jsonStr.isEmpty()) {
                continue;
            }
            try (StringReader stringReader = new StringReader(jsonStr);
                 JsonReader jsonReader = new JsonReader(stringReader)) {

                T pojo = parser.apply(jsonReader);

                if (pojo != null) {
                    pojo.validate();
                    if (pojo.isValid()) map.put(entry.getKey(), pojo);
                    else CgcTactical.LOGGER.debug("Received invalid pojo (ResourceLocation: {})", entry.getKey());
                }
            } catch (Exception e) {
                CgcTactical.LOGGER.error("Error parsing pojo (ResourceLocation: {})", entry.getKey(), e);
            }
        }
        return map;
    }
}
