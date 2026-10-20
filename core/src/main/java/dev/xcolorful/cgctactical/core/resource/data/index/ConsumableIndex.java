package dev.xcolorful.cgctactical.core.resource.data.index;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.customgun.core.api.resource.data.index.GunIndexTag;
import dev.xcolorful.customgun.core.resource.data.index._DataIndex;
import dev.xcolorful.customgun.core.util.JsonUtils;

import java.io.IOException;

public class ConsumableIndex extends _DataIndex<ConsumableIndex> {

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
                    case GunIndexTag.NAME_LANG, GunIndexTag.NAME_LANG_OLD1 -> pojo.setNameLang(JsonUtils.readString(reader));
                    case GunIndexTag.TOOLTIP_LANG, GunIndexTag.TOOLTIP_LANG_OLD1 -> pojo.setTooltipLang(JsonUtils.readString(reader));
                    case GunIndexTag.DATA_LOCATION, GunIndexTag.DATA_LOCATION_OLD1 -> pojo.setDataLocation(JsonUtils.readResourceLocation(reader));
                    case GunIndexTag.DISPLAY_INDEX_LOCATION, GunIndexTag.DISPLAY_INDEX_LOCATION_OLD1 -> pojo.setDisplayIndexLocation(JsonUtils.readResourceLocation(reader));
                    case GunIndexTag.SLOT_SORT, GunIndexTag.SLOT_SORT_OLD1 -> pojo.setSlotSort(JsonUtils.readInt(reader));

                    default -> reader.skipValue();
                }
            }
        }
        reader.endObject();
        return pojo;
    }

    public static void toJson(JsonWriter writer, ConsumableIndex pojo) throws IOException {
        if (pojo != null) pojo.toJson(writer);
    }
    @Override
    public void toJson(JsonWriter writer) throws IOException {
        writer.beginObject(); {
            JsonUtils.writeString(writer, GunIndexTag.NAME_LANG, this.getNameLang());
            JsonUtils.writeString(writer, GunIndexTag.TOOLTIP_LANG, this.getTooltipLang());
            JsonUtils.writeResourceLocation(writer, GunIndexTag.DATA_LOCATION, this.getDataLocation());
            JsonUtils.writeResourceLocation(writer, GunIndexTag.DISPLAY_INDEX_LOCATION, this.getDisplayIndexLocation());
            JsonUtils.writeInt(writer, GunIndexTag.SLOT_SORT, this.getSlotSort());
        }
        writer.endObject();
    }

    @Override
    protected void validatePojo() {
        super.validatePojo();
        if (!this.isValid()) return;

        this.setValid(true);
    }

    // --------Getter & Setter--------

    // --------Back compatibility--------

    @Override
    public ConsumableIndex applyBackCompatibility() {
        super.applyBackCompatibility();
        return this;
    }
}