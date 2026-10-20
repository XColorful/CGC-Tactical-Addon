package dev.xcolorful.cgctactical.core.resource.data.data.throwable.stun;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.cgctactical.core.api.resource.data.data.throwable.stun._BlindDataTag;
import dev.xcolorful.customgun.core.resource.ResourcePojo;
import dev.xcolorful.customgun.core.util.JsonUtils;

import java.io.IOException;

public final class _BlindData extends ResourcePojo<_BlindData> {

    private int maxDuration = 200;
    private int minDuration = 10;
    private float maxAngle = 85f;
    private float viewAngleFactor = 0.5f;

    private static final _BlindData PARSER = new _BlindData();
    public static _BlindData fromJson(JsonReader reader) throws IOException {
        return PARSER.fromJsonReader(reader);
    }
    @Override
    protected _BlindData fromJsonReader(JsonReader reader) throws IOException {
        _BlindData pojo = new _BlindData();
        reader.beginObject(); {
            while (reader.hasNext()) {
                String key = reader.nextName();
                switch (key) {
                    case _BlindDataTag.MAX_DURATION -> pojo.maxDuration = JsonUtils.readInt(reader);
                    case _BlindDataTag.MIN_DURATION -> pojo.minDuration = JsonUtils.readInt(reader);
                    case _BlindDataTag.MAX_ANGLE -> pojo.maxAngle = JsonUtils.readFloat(reader);
                    case _BlindDataTag.VIEW_ANGLE_FACTOR -> pojo.viewAngleFactor = JsonUtils.readFloat(reader);
                    default -> reader.skipValue();
                }
            }
        }
        reader.endObject();
        return pojo;
    }

    public static void toJson(JsonWriter writer, _BlindData pojo) throws IOException {
        if (pojo != null) pojo.toJson(writer);
    }
    @Override
    public void toJson(JsonWriter writer) throws IOException {
        writer.beginObject(); {
            JsonUtils.writeInt(writer, _BlindDataTag.MAX_DURATION, this.maxDuration);
            JsonUtils.writeInt(writer, _BlindDataTag.MIN_DURATION, this.minDuration);
            JsonUtils.writeFloat(writer, _BlindDataTag.MAX_ANGLE, this.maxAngle);
            JsonUtils.writeFloat(writer, _BlindDataTag.VIEW_ANGLE_FACTOR, this.viewAngleFactor);
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
    public float getMaxAngle() {
        return maxAngle;
    }
    public float getViewAngleFactor() {
        return viewAngleFactor;
    }

    public void setMaxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
    }
    public void setMinDuration(int minDuration) {
        this.minDuration = minDuration;
    }
    public void setMaxAngle(float maxAngle) {
        this.maxAngle = maxAngle;
    }
    public void setViewAngleFactor(float viewAngleFactor) {
        this.viewAngleFactor = viewAngleFactor;
    }

    // --------Back compatibility--------
}
