package dev.xcolorful.cgctactical.core.resource.data.index;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.cgctactical.client.resource.network.SyncDataCache;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.api.resource.data.index.MeleeIndexTag;
import dev.xcolorful.cgctactical.core.resource._AllDataManager;
import dev.xcolorful.cgctactical.core.resource.data.data.MeleeData;
import dev.xcolorful.customgun.core.api.resource.ResourceTag;
import dev.xcolorful.customgun.core.resource.data.index._DataIndex;
import dev.xcolorful.customgun.core.util.JsonUtils;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Map;
import java.util.function.BiConsumer;

public final class MeleeIndex extends _DataIndex<MeleeIndex> {

    private ResourceLocation meleeCategoryLocation; // TODO
    private ResourceLocation baseItemLocation;

    private static final MeleeIndex PARSER = new MeleeIndex();
    public static MeleeIndex fromJson(JsonReader reader) throws IOException {
        return PARSER.fromJsonReader(reader);
    }
    @Override
    protected MeleeIndex fromJsonReader(JsonReader reader) throws IOException {
        MeleeIndex pojo = new MeleeIndex();
        reader.beginObject(); {
            while (reader.hasNext()) {
                String key = reader.nextName();
                switch (key) {
                    case MeleeIndexTag.NAME_LANG, MeleeIndexTag.NAME_LANG_OLD1 -> pojo.setNameLang(JsonUtils.readString(reader));
                    case MeleeIndexTag.TOOLTIP_LANG, MeleeIndexTag.TOOLTIP_LANG_OLD1 -> pojo.setTooltipLang(JsonUtils.readString(reader));
                    case MeleeIndexTag.DATA_LOCATION -> pojo.setDataLocation(JsonUtils.readResourceLocation(reader)); case MeleeIndexTag.DATA_LOCATION_OLD1 -> pojo.meleeData = MeleeData.fromJson(reader);
                    case MeleeIndexTag.DISPLAY_INDEX_LOCATION, MeleeIndexTag.DISPLAY_INDEX_LOCATION_OLD1 -> pojo.setDisplayIndexLocation(JsonUtils.readResourceLocation(reader));
                    case MeleeIndexTag.SLOT_SORT, MeleeIndexTag.SLOT_SORT_OLD1 -> pojo.setSlotSort(JsonUtils.readInt(reader));
                    case MeleeIndexTag.HIDE_IN_GAME, MeleeIndexTag.HIDE_IN_GAME_OLD1 -> pojo.setHideInGame(JsonUtils.readBoolean(reader));

                    case MeleeIndexTag.MELEE_CATEGORY, MeleeIndexTag.MELEE_CATEGORY_OLD1 -> pojo.meleeCategoryLocation = JsonUtils.readResourceLocation(reader);
                    case MeleeIndexTag.BASE_ITEM -> pojo.baseItemLocation = JsonUtils.readResourceLocation(reader);
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

    public static void toJson(JsonWriter writer, MeleeIndex pojo) throws IOException {
        if (pojo != null) pojo.toJson(writer);
    }
    @Override
    public void toJson(JsonWriter writer) throws IOException {
        writer.beginObject(); {
            JsonUtils.writeString(writer, MeleeIndexTag.NAME_LANG, this.getNameLang());
            JsonUtils.writeString(writer, MeleeIndexTag.TOOLTIP_LANG, this.getTooltipLang());
            JsonUtils.writeResourceLocation(writer, MeleeIndexTag.DATA_LOCATION, this.getDataLocation());
            JsonUtils.writeResourceLocation(writer, MeleeIndexTag.DISPLAY_INDEX_LOCATION, this.getDisplayIndexLocation());
            JsonUtils.writeInt(writer, MeleeIndexTag.SLOT_SORT, this.getSlotSort());
            JsonUtils.writeBoolean(writer, MeleeIndexTag.HIDE_IN_GAME, this.getHideInGame());

            JsonUtils.writeResourceLocation(writer, MeleeIndexTag.MELEE_CATEGORY, this.meleeCategoryLocation);
            JsonUtils.writeResourceLocation(writer, MeleeIndexTag.BASE_ITEM, this.baseItemLocation);
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

        boolean n1 = (this.meleeCategoryLocation == null | this.baseItemLocation == null);
        if (n1) {
            this.setValid(false);
            return;
        }

        this.setValid(true);
    }

    @Override
    protected MeleeIndex copyTo(final MeleeIndex pojo) {
        super.copyTo(pojo);
        pojo.setMeleeCategoryLocation(this.meleeCategoryLocation);
        pojo.setBaseItemLocation(this.baseItemLocation);
        return pojo;
    }

    @Override
    public MeleeIndex copy() {
        MeleeIndex pojo = copyTo(new MeleeIndex());
        pojo.setValid(this.isValid());
        return pojo;
    }

    // --------Getter & Setter--------

    public ResourceLocation getMeleeCategoryLocation() {
        return meleeCategoryLocation;
    }
    public ResourceLocation getBaseItemLocation() {
        return baseItemLocation;
    }

    public void setMeleeCategoryLocation(ResourceLocation meleeCategoryLocation) {
        this.meleeCategoryLocation = meleeCategoryLocation;
    }
    public void setBaseItemLocation(ResourceLocation baseItemLocation) {
        this.baseItemLocation = baseItemLocation;
    }

    // --------Back compatibility--------

    @Override
    public MeleeIndex applyBackCompatibility() {
        super.applyBackCompatibility();
        this.baseItemLocation = this.baseItemLocation == null ? ResourceTag.NULL_LOCATION : this.baseItemLocation;
        return this;
    }

    @Deprecated private @Nullable MeleeData meleeData;
    @Deprecated public @Nullable MeleeData getMeleeData() {
        return meleeData;
    }
    @Deprecated public void setMeleeData(MeleeData meleeData) {
        this.meleeData = meleeData;
    }
    @ApiStatus.Internal
    public static void _reloadLegacyData() {
        BiConsumer<ResourceLocation, MeleeData> consumer; {
            var dataManager = _AllDataManager.getCurrent();
            if (dataManager != null && dataManager.meleeIndexManager != null) consumer = (location, pojo) -> dataManager.meleeDataManager.getAllPojo().putIfAbsent(location, pojo);
            else consumer = (location, pojo) -> SyncDataCache.INSTANCE.meleeData.putIfAbsent(location, pojo);
        }

        for (Map.Entry<ResourceLocation, MeleeIndex> entry : ResourceApi.getAllMeleeIndex()) {
            MeleeIndex meleeIndex = entry.getValue();
            @Nullable MeleeData meleeData = meleeIndex.getMeleeData();
            if (meleeData == null) continue;
            // 有旧字段就直接转换

            var dataLocation = entry.getKey();
            consumer.accept(dataLocation, meleeData);
            meleeIndex.setDataLocation(dataLocation);
            meleeIndex.setMeleeData(null);
        }
    }
}
