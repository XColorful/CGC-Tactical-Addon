package dev.xcolorful.cgctactical.core.resource.data.index;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.api.resource.data.index.ThrowableIndexTag;
import dev.xcolorful.cgctactical.core.resource._AllDataManager;
import dev.xcolorful.cgctactical.core.resource.data.data.ThrowableData;
import dev.xcolorful.customgun.core.api.resource.ResourceTag;
import dev.xcolorful.customgun.core.resource.data.index._DataIndex;
import dev.xcolorful.customgun.core.util.JsonUtils;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.function.BiConsumer;

public final class ThrowableIndex extends _DataIndex<ThrowableIndex> {

    private Identifier throwableCategoryLocation; // TODO
    private Identifier baseItemLocation;

    private static final ThrowableIndex PARSER = new ThrowableIndex();
    public static ThrowableIndex fromJson(JsonReader reader) throws IOException {
        return PARSER.fromJsonReader(reader);
    }
    @Override
    protected ThrowableIndex fromJsonReader(JsonReader reader) throws IOException {
        ThrowableIndex pojo = new ThrowableIndex();
        reader.beginObject(); {
            while (reader.hasNext()) {
                String key = reader.nextName();
                switch (key) {
                    case ThrowableIndexTag.NAME_LANG, ThrowableIndexTag.NAME_LANG_OLD1 -> pojo.setNameLang(JsonUtils.readString(reader));
                    case ThrowableIndexTag.TOOLTIP_LANG, ThrowableIndexTag.TOOLTIP_LANG_OLD1 -> pojo.setTooltipLang(JsonUtils.readString(reader));
                    case ThrowableIndexTag.DATA_LOCATION -> pojo.setDataLocation(JsonUtils.readResourceLocation(reader)); case ThrowableIndexTag.DATA_LOCATION_OLD1 -> pojo.throwableData = ThrowableData.fromJson(reader);
                    case ThrowableIndexTag.DISPLAY_INDEX_LOCATION, ThrowableIndexTag.DISPLAY_INDEX_LOCATION_OLD1 -> pojo.setDisplayIndexLocation(JsonUtils.readResourceLocation(reader));
                    case ThrowableIndexTag.SLOT_SORT, ThrowableIndexTag.SLOT_SORT_OLD1 -> pojo.setSlotSort(JsonUtils.readInt(reader));
                    case ThrowableIndexTag.HIDE_IN_GAME, ThrowableIndexTag.HIDE_IN_GAME_OLD1 -> pojo.setHideInGame(JsonUtils.readBoolean(reader));

                    case ThrowableIndexTag.THROWABLE_CATEGORY, ThrowableIndexTag.THROWABLE_CATEGORY_OLD1 -> pojo.throwableCategoryLocation = JsonUtils.readResourceLocation(reader);
                    case ThrowableIndexTag.BASE_ITEM -> pojo.baseItemLocation = JsonUtils.readResourceLocation(reader);
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

    public static void toJson(JsonWriter writer, ThrowableIndex pojo) throws IOException {
        if (pojo != null) pojo.toJson(writer);
    }
    @Override
    public void toJson(JsonWriter writer) throws IOException {
        writer.beginObject(); {
            JsonUtils.writeString(writer, ThrowableIndexTag.NAME_LANG, this.getNameLang());
            JsonUtils.writeString(writer, ThrowableIndexTag.TOOLTIP_LANG, this.getTooltipLang());
            JsonUtils.writeResourceLocation(writer, ThrowableIndexTag.DATA_LOCATION, this.getDataLocation());
            JsonUtils.writeResourceLocation(writer, ThrowableIndexTag.DISPLAY_INDEX_LOCATION, this.getDisplayIndexLocation());
            JsonUtils.writeInt(writer, ThrowableIndexTag.SLOT_SORT, this.getSlotSort());
            JsonUtils.writeBoolean(writer, ThrowableIndexTag.HIDE_IN_GAME, this.getHideInGame());

            JsonUtils.writeResourceLocation(writer, ThrowableIndexTag.THROWABLE_CATEGORY, this.throwableCategoryLocation);
            JsonUtils.writeResourceLocation(writer, ThrowableIndexTag.BASE_ITEM, this.baseItemLocation);
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

        boolean n1 = (this.throwableCategoryLocation == null | this.baseItemLocation == null);
        if (n1) {
            this.setValid(false);
            return;
        }

        this.setValid(true);
    }

    @Override
    protected ThrowableIndex copyTo(final ThrowableIndex pojo) {
        super.copyTo(pojo);
        pojo.setThrowableCategoryLocation(this.throwableCategoryLocation);
        pojo.setBaseItemLocation(this.baseItemLocation);
        return pojo;
    }

    @Override
    public ThrowableIndex copy() {
        ThrowableIndex pojo = copyTo(new ThrowableIndex());
        pojo.setValid(this.isValid());
        return pojo;
    }

    // --------Getter & Setter--------

    public Identifier getThrowableCategoryLocation() {
        return throwableCategoryLocation;
    }
    public Identifier getBaseItemLocation() {
        return baseItemLocation;
    }

    public void setThrowableCategoryLocation(Identifier throwableCategoryLocation) {
        this.throwableCategoryLocation = throwableCategoryLocation;
    }
    public void setBaseItemLocation(Identifier baseItemLocation) {
        this.baseItemLocation = baseItemLocation;
    }

    // --------Back compatibility--------

    @Override
    public ThrowableIndex applyBackCompatibility() {
        super.applyBackCompatibility();
        this.baseItemLocation = this.baseItemLocation == null ? ResourceTag.NULL_LOCATION : this.baseItemLocation;
        return this;
    }

    @Deprecated private @Nullable ThrowableData throwableData;
    @Deprecated public @Nullable ThrowableData getThrowableData() {
        return throwableData;
    }
    @Deprecated public void setThrowableData(ThrowableData throwableData) {
        this.throwableData = throwableData;
    }
    @ApiStatus.Internal
    @Deprecated public static void _reloadLegacyData() {
        var dataManager = _AllDataManager.getCurrent();
        if (dataManager == null || dataManager.throwableIndexManager == null) return;

        BiConsumer<Identifier, ThrowableData> consumer = (location, pojo) -> {
            dataManager.throwableDataManager.getAllPojo().putIfAbsent(location, pojo);
            // 写网络缓存
            var networkCache = dataManager.throwableDataManager.getNetworkCache();
            if (!networkCache.containsKey(location)) {
                try (StringWriter stringWriter = new StringWriter();
                     JsonWriter writer = new JsonWriter(stringWriter)) {

                    writer.setLenient(false);
                    pojo.toJson(writer);
                    networkCache.put(location, stringWriter.toString());
                } catch (Exception e) { // IO 异常或其他未知错误
                    CgcTactical.LOGGER.debug("ThrowableIndex: Failed to write legacy pojo file at: {}", location, e);
                }
            }
        };

        for (Map.Entry<Identifier, ThrowableIndex> entry : ResourceApi.getAllThrowableIndex()) {
            ThrowableIndex throwableIndex = entry.getValue();
            @Nullable ThrowableData throwableData = throwableIndex.getThrowableData();
            if (throwableData == null) continue;
            // 有旧字段就直接转换

            throwableData.applyBackCompatibility();
            throwableData.validate();
            if (!throwableData.isValid()) {
                CgcTactical.LOGGER.debug("ThrowableIndex: legacy ThrowableData {} not valid", entry.getKey());
                continue;
            }

            var dataLocation = entry.getKey();
            consumer.accept(dataLocation, throwableData);
            throwableIndex.setDataLocation(dataLocation);
            throwableIndex.setThrowableData(null);
        }
    }
    @ApiStatus.Internal
    @Deprecated public void _reloadLegacyDisplay(Identifier pojoLocation) {
        var displayLocation = this.getDisplayIndexLocation();
        if (displayLocation == null || displayLocation.equals(ResourceTag.NULL_LOCATION)) {
            this.setDisplayIndexLocation(pojoLocation);
        }
    }
}
