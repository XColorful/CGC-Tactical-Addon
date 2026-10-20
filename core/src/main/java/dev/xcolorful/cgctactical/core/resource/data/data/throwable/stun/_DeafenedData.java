package dev.xcolorful.cgctactical.core.resource.data.data.throwable.stun;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.cgctactical.core.api.resource.data.data.throwable.stun._DeafenedDataTag;
import dev.xcolorful.customgun.core.resource.ResourcePojo;
import dev.xcolorful.customgun.core.util.JsonUtils;

import java.io.IOException;

public final class _DeafenedData extends ResourcePojo<_DeafenedData> {

    private int maxDuration = 200;
    private int minDuration = 10;

    private static final _DeafenedData PARSER = new _DeafenedData();
    public static _DeafenedData fromJson(JsonReader reader) throws IOException {
        return PARSER.fromJsonReader(reader);
    }
    @Override
    protected _DeafenedData fromJsonReader(JsonReader reader) throws IOException {
        _DeafenedData pojo = new _DeafenedData();
        reader.beginObject(); {
            while (reader.hasNext()) {
                String key = reader.nextName();
                switch (key) {
                    case _DeafenedDataTag.MAX_DURATION -> pojo.maxDuration = JsonUtils.readInt(reader);
                    case _DeafenedDataTag.MIN_DURATION -> pojo.minDuration = JsonUtils.readInt(reader);
                    default -> reader.skipValue();
                }
            }
        }
        reader.endObject();
        return pojo;
    }

    public static void toJson(JsonWriter writer, _DeafenedData pojo) throws IOException {
        if (pojo != null) pojo.toJson(writer);
    }
    @Override
    public void toJson(JsonWriter writer) throws IOException {
        writer.beginObject(); {
            JsonUtils.writeInt(writer, _DeafenedDataTag.MAX_DURATION, this.maxDuration);
            JsonUtils.writeInt(writer, _DeafenedDataTag.MIN_DURATION, this.minDuration);
        }
        writer.endObject();
    }

    @Override
    protected void validatePojo() {
        this.setValid(true);
    }

    // --------Getter & Setter--------

    public int getMaxDuration() {
        return maxDuration;
    }
    public int getMinDuration() {
        return minDuration;
    }

    public void setMaxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
    }
    public void setMinDuration(int minDuration) {
        this.minDuration = minDuration;
    }

    // --------Back compatibility--------
}
