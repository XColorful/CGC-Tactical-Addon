package dev.xcolorful.cgctactical.core.resource.data.index;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.cgctactical.core.api.resource.data.index.ThrowableIndexTag;
import dev.xcolorful.customgun.core.api.resource.ResourceTag;
import dev.xcolorful.customgun.core.resource.data.index._DataIndex;
import dev.xcolorful.customgun.core.util.JsonUtils;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;

public final class ThrowableIndex extends _DataIndex<ThrowableIndex> {

    private ResourceLocation throwableCategoryLocation; // TODO
    private ResourceLocation baseItemLocation;

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
                    case ThrowableIndexTag.DATA_LOCATION, ThrowableIndexTag.DATA_LOCATION_OLD1 -> pojo.setDataLocation(JsonUtils.readResourceLocation(reader));
                    case ThrowableIndexTag.DISPLAY_INDEX_LOCATION, ThrowableIndexTag.DISPLAY_INDEX_LOCATION_OLD1 -> pojo.setDisplayIndexLocation(JsonUtils.readResourceLocation(reader));
                    case ThrowableIndexTag.SLOT_SORT, ThrowableIndexTag.SLOT_SORT_OLD1 -> pojo.setSlotSort(JsonUtils.readInt(reader));

                    case ThrowableIndexTag.THROWABLE_CATEGORY, ThrowableIndexTag.THROWABLE_CATEGORY_OLD1 -> pojo.throwableCategoryLocation = JsonUtils.readResourceLocation(reader);
                    case ThrowableIndexTag.BASE_ITEM -> pojo.baseItemLocation = JsonUtils.readResourceLocation(reader);
                    default -> reader.skipValue();
                }
            }
        }
        reader.endObject();
        return pojo;
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

            JsonUtils.writeResourceLocation(writer, ThrowableIndexTag.THROWABLE_CATEGORY, this.throwableCategoryLocation);
            JsonUtils.writeResourceLocation(writer, ThrowableIndexTag.BASE_ITEM, this.baseItemLocation);
        }
        writer.endObject();
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

    // --------Getter & Setter--------

    public ResourceLocation getThrowableCategoryLocation() {
        return throwableCategoryLocation;
    }
    public ResourceLocation getBaseItemLocation() {
        return baseItemLocation;
    }

    public void setThrowableCategoryLocation(ResourceLocation throwableCategoryLocation) {
        this.throwableCategoryLocation = throwableCategoryLocation;
    }
    public void setBaseItemLocation(ResourceLocation baseItemLocation) {
        this.baseItemLocation = baseItemLocation;
    }

    // --------Back compatibility--------

    @Override
    public ThrowableIndex applyBackCompatibility() {
        super.applyBackCompatibility();
        this.baseItemLocation = this.baseItemLocation == null ? ResourceTag.NULL_LOCATION : this.baseItemLocation;
        return this;
    }
}
