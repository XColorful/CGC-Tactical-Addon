package dev.xcolorful.cgctactical.client.resource.assets.display;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.cgctactical.core.api.resource.assets.display.ThrowableDisplayTag;
import dev.xcolorful.customgun.core.util.JsonUtils;

import java.io.IOException;

public final class ThrowableDisplay extends _AssetsDisplay<ThrowableDisplay> {

//    private @Nullable Transformation entityTransform; // TODO

    private static final ThrowableDisplay PARSER = new ThrowableDisplay();
    public static ThrowableDisplay fromJson(JsonReader reader) throws IOException {
        return PARSER.fromJsonReader(reader);
    }
    @Override
    protected ThrowableDisplay fromJsonReader(JsonReader reader) throws IOException {
        ThrowableDisplay pojo = new ThrowableDisplay();
        reader.beginObject(); {
            while (reader.hasNext()) {
                String key = reader.nextName();
                switch (key) {
                    case ThrowableDisplayTag.MODEL_LOCATION, ThrowableDisplayTag.MODEL_LOCATION_OLD1 -> pojo.setModelLocation(JsonUtils.readResourceLocation(reader));
                    case ThrowableDisplayTag.TEXTURE_LOCATION, ThrowableDisplayTag.TEXTURE_LOCATION_OLD1 -> pojo.setTextureLocation(JsonUtils.readResourceLocation(reader));
                    case ThrowableDisplayTag.SLOT_TEXTURE_LOCATION, ThrowableDisplayTag.SLOT_TEXTURE_LOCATION_OLD1 -> pojo.setSlotTextureLocation(JsonUtils.readResourceLocation(reader));
                    case ThrowableDisplayTag.ANIMATION_LOCATION, ThrowableDisplayTag.ANIMATION_LOCATION_OLD1 -> pojo.setAnimationLocation(JsonUtils.readResourceLocation(reader));
                    case ThrowableDisplayTag.SCRIPT_LOCATION, ThrowableDisplayTag.SCRIPT_LOCATION_OLD1 -> pojo.setScriptLocation(JsonUtils.readResourceLocation(reader));
                    case ThrowableDisplayTag.DISPLAY_OFFSET -> pojo.setDisplayOffset(JsonUtils.readFloatArrayFast(reader, 3));
                    case ThrowableDisplayTag.SOUNDS -> pojo.setSounds(JsonUtils.readString2ObjectMap(reader, JsonUtils::readResourceLocation));
                    default -> reader.skipValue();
                }
            }
        }
        reader.endObject();
        return pojo;
    }

    public static void toJson(JsonWriter writer, ThrowableDisplay pojo) throws IOException {
        if (pojo != null) pojo.toJson(writer);
    }
    @Override
    public void toJson(JsonWriter writer) throws IOException {
        writer.beginObject(); {
            JsonUtils.writeResourceLocation(writer, ThrowableDisplayTag.MODEL_LOCATION, this.getModelLocation());
            JsonUtils.writeResourceLocation(writer, ThrowableDisplayTag.TEXTURE_LOCATION, this.getTextureLocation());
            JsonUtils.writeResourceLocation(writer, ThrowableDisplayTag.SLOT_TEXTURE_LOCATION, this.getSlotTextureLocation());
            JsonUtils.writeResourceLocation(writer, ThrowableDisplayTag.ANIMATION_LOCATION, this.getAnimationLocation());
            JsonUtils.writeResourceLocation(writer, ThrowableDisplayTag.SCRIPT_LOCATION, this.getScriptLocation());
            JsonUtils.writeFloatArray(writer, ThrowableDisplayTag.DISPLAY_OFFSET, this.getDisplayOffset());
            JsonUtils.writeString2ObjectMap(writer, ThrowableDisplayTag.SOUNDS, this.getSounds(), JsonUtils::writeResourceLocationValue);
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
    public ThrowableDisplay applyBackCompatibility() {
        super.applyBackCompatibility();
        return this;
    }
}
