package dev.xcolorful.cgctactical.client.resource.assets.display;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.cgctactical.core.api.resource.assets.display.MeleeDisplayTag;
import dev.xcolorful.customgun.core.util.JsonUtils;

import java.io.IOException;

public final class MeleeDisplay extends _AssetsDisplay<MeleeDisplay> {

    private static final MeleeDisplay PARSER = new MeleeDisplay();
    public static MeleeDisplay fromJson(JsonReader reader) throws IOException {
        return PARSER.fromJsonReader(reader);
    }
    @Override
    protected MeleeDisplay fromJsonReader(JsonReader reader) throws IOException {
        MeleeDisplay pojo = new MeleeDisplay();
        reader.beginObject(); {
            while (reader.hasNext()) {
                String key = reader.nextName();
                switch (key) {
                    case MeleeDisplayTag.MODEL_LOCATION, MeleeDisplayTag.MODEL_LOCATION_OLD1 -> pojo.setModelLocation(JsonUtils.readResourceLocation(reader));
                    case MeleeDisplayTag.TEXTURE_LOCATION, MeleeDisplayTag.TEXTURE_LOCATION_OLD1 -> pojo.setTextureLocation(JsonUtils.readResourceLocation(reader));
                    case MeleeDisplayTag.SLOT_TEXTURE_LOCATION, MeleeDisplayTag.SLOT_TEXTURE_LOCATION_OLD1 -> pojo.setSlotTextureLocation(JsonUtils.readResourceLocation(reader));
                    case MeleeDisplayTag.ANIMATION_LOCATION, MeleeDisplayTag.ANIMATION_LOCATION_OLD1 -> pojo.setAnimationLocation(JsonUtils.readResourceLocation(reader));
                    case MeleeDisplayTag.SCRIPT_LOCATION, MeleeDisplayTag.SCRIPT_LOCATION_OLD1 -> pojo.setScriptLocation(JsonUtils.readResourceLocation(reader));
                    case MeleeDisplayTag.DISPLAY_OFFSET -> pojo.setDisplayOffset(JsonUtils.readFloatArrayFast(reader, 3));
                    case MeleeDisplayTag.SOUNDS -> pojo.setSounds(JsonUtils.readString2ObjectMap(reader, JsonUtils::readResourceLocation));
                    default -> reader.skipValue();
                }
            }
        }
        reader.endObject();
        return pojo;
    }

    public static void toJson(JsonWriter writer, MeleeDisplay pojo) throws IOException {
        if (pojo != null) pojo.toJson(writer);
    }
    @Override
    public void toJson(JsonWriter writer) throws IOException {
        writer.beginObject(); {
            JsonUtils.writeResourceLocation(writer, MeleeDisplayTag.MODEL_LOCATION, this.getModelLocation());
            JsonUtils.writeResourceLocation(writer, MeleeDisplayTag.TEXTURE_LOCATION, this.getTextureLocation());
            JsonUtils.writeResourceLocation(writer, MeleeDisplayTag.SLOT_TEXTURE_LOCATION, this.getSlotTextureLocation());
            JsonUtils.writeResourceLocation(writer, MeleeDisplayTag.ANIMATION_LOCATION, this.getAnimationLocation());
            JsonUtils.writeResourceLocation(writer, MeleeDisplayTag.SCRIPT_LOCATION, this.getScriptLocation());
            JsonUtils.writeFloatArray(writer, MeleeDisplayTag.DISPLAY_OFFSET, this.getDisplayOffset());
            JsonUtils.writeString2ObjectMap(writer, MeleeDisplayTag.SOUNDS, this.getSounds(), JsonUtils::writeResourceLocationValue);
        }
        writer.endObject();
    }

    @Override
    protected void validatePojo() {
        super.validatePojo();
        if (!this.isValid()) return;

        this.setValid(true);
    }

    // --------Back compatibility--------

    @Override
    public MeleeDisplay applyBackCompatibility() {
        super.applyBackCompatibility();
        return this;
    }
}
