package dev.xcolorful.cgctactical.core.resource.data.data.melee.combat;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.cgctactical.core.api.resource.data.data.melee.combat._MeleeMovementTag;
import dev.xcolorful.customgun.core.resource.ResourcePojo;
import dev.xcolorful.customgun.core.util.JsonUtils;

import java.io.IOException;

public final class _MeleeMovement extends ResourcePojo<_MeleeMovement> {

    private int delay = 0;
    private float speed = 0.5f;

    private static final _MeleeMovement PARSER = new _MeleeMovement();
    public static _MeleeMovement fromJson(JsonReader reader) throws IOException {
        return PARSER.fromJsonReader(reader);
    }
    @Override
    protected _MeleeMovement fromJsonReader(JsonReader reader) throws IOException {
        _MeleeMovement pojo = new _MeleeMovement();
        reader.beginObject(); {
            while (reader.hasNext()) {
                String key = reader.nextName();
                switch (key) {
                    case _MeleeMovementTag.DELAY -> pojo.delay = JsonUtils.readInt(reader);
                    case _MeleeMovementTag.SPEED -> pojo.speed = JsonUtils.readFloat(reader);
                    default -> reader.skipValue();
                }
            }
        }
        reader.endObject();
        return pojo;
    }

    public static void toJson(JsonWriter writer, _MeleeMovement pojo) throws IOException {
        if (pojo != null) pojo.toJson(writer);
    }
    @Override
    public void toJson(JsonWriter writer) throws IOException {
        writer.beginObject(); {
            JsonUtils.writeInt(writer, _MeleeMovementTag.DELAY, this.delay);
            JsonUtils.writeFloat(writer, _MeleeMovementTag.SPEED, this.speed);
        }
        writer.endObject();
    }

    @Override
    protected void validatePojo() {
        this.setValid(true);
    }

    // --------Getter & Setter--------

    public int getDelay() {
        return delay;
    }
    public float getSpeed() {
        return speed;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    // --------Back compatibility--------
}
