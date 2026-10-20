package dev.xcolorful.cgctactical.core.resource.data.index;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.cgctactical.core.api.resource.data.index.MeleeIndexTag;
import dev.xcolorful.customgun.core.api.resource.ResourceTag;
import dev.xcolorful.customgun.core.resource.data.index._DataIndex;
import dev.xcolorful.customgun.core.util.JsonUtils;
import net.minecraft.resources.Identifier;

import java.io.IOException;

public final class MeleeIndex extends _DataIndex<MeleeIndex> {

    private Identifier meleeCategoryLocation; // TODO
    private Identifier baseItemLocation;

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
                    case MeleeIndexTag.DATA_LOCATION, MeleeIndexTag.DATA_LOCATION_OLD1 -> pojo.setDataLocation(JsonUtils.readResourceLocation(reader));
                    case MeleeIndexTag.DISPLAY_INDEX_LOCATION, MeleeIndexTag.DISPLAY_INDEX_LOCATION_OLD1 -> pojo.setDisplayIndexLocation(JsonUtils.readResourceLocation(reader));
                    case MeleeIndexTag.SLOT_SORT, MeleeIndexTag.SLOT_SORT_OLD1 -> pojo.setSlotSort(JsonUtils.readInt(reader));

                    case MeleeIndexTag.MELEE_CATEGORY, MeleeIndexTag.MELEE_CATEGORY_OLD1 -> pojo.meleeCategoryLocation = JsonUtils.readResourceLocation(reader);
                    case MeleeIndexTag.BASE_ITEM -> pojo.baseItemLocation = JsonUtils.readResourceLocation(reader);
                    default -> reader.skipValue();
                }
            }
        }
        reader.endObject();
        return pojo;
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

            JsonUtils.writeResourceLocation(writer, MeleeIndexTag.MELEE_CATEGORY, this.meleeCategoryLocation);
            JsonUtils.writeResourceLocation(writer, MeleeIndexTag.BASE_ITEM, this.baseItemLocation);
        }
        writer.endObject();
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

    // --------Getter & Setter--------

    public Identifier getMeleeCategoryLocation() {
        return meleeCategoryLocation;
    }
    public Identifier getBaseItemLocation() {
        return baseItemLocation;
    }

    public void setMeleeCategoryLocation(Identifier meleeCategoryLocation) {
        this.meleeCategoryLocation = meleeCategoryLocation;
    }
    public void setBaseItemLocation(Identifier baseItemLocation) {
        this.baseItemLocation = baseItemLocation;
    }

    // --------Back compatibility--------

    @Override
    public MeleeIndex applyBackCompatibility() {
        super.applyBackCompatibility();
        this.baseItemLocation = this.baseItemLocation == null ? ResourceTag.NULL_LOCATION : this.baseItemLocation;
        return this;
    }
}
