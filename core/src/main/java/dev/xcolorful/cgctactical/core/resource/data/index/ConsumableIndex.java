package dev.xcolorful.cgctactical.core.resource.data.index;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.cgctactical.client.resource.network.SyncDataCache;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.api.resource.data.index.ConsumableIndexTag;
import dev.xcolorful.cgctactical.core.resource._AllDataManager;
import dev.xcolorful.cgctactical.core.resource.data.data.ConsumableData;
import dev.xcolorful.customgun.core.resource.data.index._DataIndex;
import dev.xcolorful.customgun.core.util.JsonUtils;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Map;
import java.util.function.BiConsumer;

public final class ConsumableIndex extends _DataIndex<ConsumableIndex> {

    private static final ConsumableIndex PARSER = new ConsumableIndex();

    public static ConsumableIndex fromJson(JsonReader reader) throws IOException {
        return PARSER.fromJsonReader(reader);
    }

    @Override
    protected ConsumableIndex fromJsonReader(JsonReader reader) throws IOException {
        ConsumableIndex pojo = new ConsumableIndex();
        reader.beginObject(); {
            while (reader.hasNext()) {
                String key = reader.nextName();
                switch (key) {
                    case ConsumableIndexTag.NAME_LANG, ConsumableIndexTag.NAME_LANG_OLD1 -> pojo.setNameLang(JsonUtils.readString(reader));
                    case ConsumableIndexTag.TOOLTIP_LANG, ConsumableIndexTag.TOOLTIP_LANG_OLD1 -> pojo.setTooltipLang(JsonUtils.readString(reader));
                    case ConsumableIndexTag.DATA_LOCATION -> pojo.setDataLocation(JsonUtils.readResourceLocation(reader)); case ConsumableIndexTag.DATA_LOCATION_OLD1 -> pojo.consumableData = ConsumableData.fromJson(reader);
                    case ConsumableIndexTag.DISPLAY_INDEX_LOCATION, ConsumableIndexTag.DISPLAY_INDEX_LOCATION_OLD1 -> pojo.setDisplayIndexLocation(JsonUtils.readResourceLocation(reader));
                    case ConsumableIndexTag.SLOT_SORT, ConsumableIndexTag.SLOT_SORT_OLD1 -> pojo.setSlotSort(JsonUtils.readInt(reader));
                    case ConsumableIndexTag.HIDE_IN_GAME, ConsumableIndexTag.HIDE_IN_GAME_OLD1 -> pojo.setHideInGame(JsonUtils.readBoolean(reader));

                    default -> this.readCustomKey(reader, key);
                }
            }
        }
        reader.endObject();
        return pojo;
    }
    @Override
    protected boolean readCustomKey(JsonReader reader, String key) throws IOException {
        return super.readCustomKey(reader, key);
    }

    public static void toJson(JsonWriter writer, ConsumableIndex pojo) throws IOException {
        if (pojo != null) pojo.toJson(writer);
    }
    @Override
    public void toJson(JsonWriter writer) throws IOException {
        writer.beginObject(); {
            JsonUtils.writeString(writer, ConsumableIndexTag.NAME_LANG, this.getNameLang());
            JsonUtils.writeString(writer, ConsumableIndexTag.TOOLTIP_LANG, this.getTooltipLang());
            JsonUtils.writeResourceLocation(writer, ConsumableIndexTag.DATA_LOCATION, this.getDataLocation());
            JsonUtils.writeResourceLocation(writer, ConsumableIndexTag.DISPLAY_INDEX_LOCATION, this.getDisplayIndexLocation());
            JsonUtils.writeInt(writer, ConsumableIndexTag.SLOT_SORT, this.getSlotSort());
            JsonUtils.writeBoolean(writer, ConsumableIndexTag.HIDE_IN_GAME, this.getHideInGame());
            writeCustomValue(writer);
        }
        writer.endObject();
    }
    @Override
    protected void writeCustomValue(JsonWriter writer) {
        super.writeCustomValue(writer);
    }

    @Override
    protected void validatePojo() {
        super.validatePojo();
        if (!this.isValid()) return;

        this.setValid(true);
    }

    @Override
    protected ConsumableIndex copyTo(final ConsumableIndex pojo) {
        super.copyTo(pojo);
        return pojo;
    }

    @Override
    public ConsumableIndex copy() {
        ConsumableIndex pojo = copyTo(new ConsumableIndex());
        pojo.setValid(this.isValid());
        return pojo;
    }

    // --------Getter & Setter--------

    // --------Back compatibility--------

    @Override
    public ConsumableIndex applyBackCompatibility() {
        super.applyBackCompatibility();
        return this;
    }

    @Deprecated private @Nullable ConsumableData consumableData;
    @Deprecated public @Nullable ConsumableData getConsumableData() {
        return consumableData;
    }
    @Deprecated public void setConsumableData(ConsumableData consumableData) {
        this.consumableData = consumableData;
    }
    @ApiStatus.Internal
    public static void _reloadLegacyData() {
        BiConsumer<ResourceLocation, ConsumableData> consumer; {
            var dataManager = _AllDataManager.getCurrent();
            if (dataManager != null && dataManager.consumableIndexManager != null) consumer = (location, pojo) -> dataManager.consumableDataManager.getAllPojo().putIfAbsent(location, pojo);
            else consumer = (location, pojo) -> SyncDataCache.INSTANCE.consumableData.putIfAbsent(location, pojo);
        }

        for (Map.Entry<ResourceLocation, ConsumableIndex> entry : ResourceApi.getAllConsumableIndex()) {
            ConsumableIndex consumableIndex = entry.getValue();
            @Nullable ConsumableData consumableData = consumableIndex.getConsumableData();
            if (consumableData == null) continue;
            // 有旧字段就直接转换

            var dataLocation = entry.getKey();
            consumer.accept(dataLocation, consumableData);
            consumableIndex.setDataLocation(dataLocation);
            consumableIndex.setConsumableData(null);
        }
    }
}
