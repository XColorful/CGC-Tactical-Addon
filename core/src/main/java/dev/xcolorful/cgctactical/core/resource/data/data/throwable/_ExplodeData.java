package dev.xcolorful.cgctactical.core.resource.data.data.throwable;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.cgctactical.core.api.resource.data.data.throwable._ExplodeDataTag;
import dev.xcolorful.customgun.core.resource.ResourcePojo;
import dev.xcolorful.customgun.core.util.JsonUtils;

import java.io.IOException;

public final class _ExplodeData extends ResourcePojo<_ExplodeData> {

    private float explodeScale = 5.5f;
    private float explodeDamage = 22.0f;
    private boolean enableWorldDestruction = false;
    private float destroyMultiplier = 1.0f;
    private boolean triggerOnExplode = false;
    private boolean remoteDetonation = false;
    private float screenShakeTime = 20f;
    private float screenShakeAmplitude = 50f;

    private static final _ExplodeData PARSER = new _ExplodeData();
    public static _ExplodeData fromJson(JsonReader reader) throws IOException {
        return PARSER.fromJsonReader(reader);
    }
    @Override
    protected _ExplodeData fromJsonReader(JsonReader reader) throws IOException {
        _ExplodeData pojo = new _ExplodeData();
        reader.beginObject(); {
            while (reader.hasNext()) {
                String key = reader.nextName();
                switch (key) {
                    case _ExplodeDataTag.EXPLODE_SCALE, _ExplodeDataTag.EXPLODE_SCALE_OLD1 -> pojo.explodeScale = JsonUtils.readFloat(reader);
                    case _ExplodeDataTag.EXPLODE_DAMAGE, _ExplodeDataTag.EXPLODE_DAMAGE_OLD1 -> pojo.explodeDamage = JsonUtils.readFloat(reader);
                    case _ExplodeDataTag.ENABLE_WORLD_DESTRUCTION, _ExplodeDataTag.ENABLE_WORLD_DESTRUCTION_OLD1 -> pojo.enableWorldDestruction = JsonUtils.readBoolean(reader);
                    case _ExplodeDataTag.DESTROY_MULTIPLIER -> pojo.destroyMultiplier = JsonUtils.readFloat(reader);
                    case _ExplodeDataTag.TRIGGER_ON_EXPLODE -> pojo.triggerOnExplode = JsonUtils.readBoolean(reader);
                    case _ExplodeDataTag.REMOTE_DETONATION -> pojo.remoteDetonation = JsonUtils.readBoolean(reader);
                    case _ExplodeDataTag.SCREEN_SHAKE_TIME -> pojo.screenShakeTime = JsonUtils.readFloat(reader);
                    case _ExplodeDataTag.SCREEN_SHAKE_AMPLITUDE -> pojo.screenShakeAmplitude = JsonUtils.readFloat(reader);
                    default -> reader.skipValue();
                }
            }
        }
        reader.endObject();
        return pojo;
    }

    public static void toJson(JsonWriter writer, _ExplodeData pojo) throws IOException {
        if (pojo != null) pojo.toJson(writer);
    }
    @Override
    public void toJson(JsonWriter writer) throws IOException {
        writer.beginObject(); {
            JsonUtils.writeFloat(writer, _ExplodeDataTag.EXPLODE_SCALE, this.explodeScale);
            JsonUtils.writeFloat(writer, _ExplodeDataTag.EXPLODE_DAMAGE, this.explodeDamage);
            JsonUtils.writeBoolean(writer, _ExplodeDataTag.ENABLE_WORLD_DESTRUCTION, this.enableWorldDestruction);
            JsonUtils.writeFloat(writer, _ExplodeDataTag.DESTROY_MULTIPLIER, this.destroyMultiplier);
            JsonUtils.writeBoolean(writer, _ExplodeDataTag.TRIGGER_ON_EXPLODE, this.triggerOnExplode);
            JsonUtils.writeBoolean(writer, _ExplodeDataTag.REMOTE_DETONATION, this.remoteDetonation);
            JsonUtils.writeFloat(writer, _ExplodeDataTag.SCREEN_SHAKE_TIME, this.screenShakeTime);
            JsonUtils.writeFloat(writer, _ExplodeDataTag.SCREEN_SHAKE_AMPLITUDE, this.screenShakeAmplitude);
        }
        writer.endObject();
    }

    @Override
    protected void validatePojo() {
        this.setValid(true);
    }

    // --------Getter & Setter--------

    public float getExplodeScale() {
        return explodeScale;
    }
    public float getExplodeDamage() {
        return explodeDamage;
    }
    public boolean getEnableWorldDestruction() {
        return enableWorldDestruction;
    }
    public float getDestroyMultiplier() {
        return destroyMultiplier;
    }
    public boolean isTriggerOnExplode() {
        return triggerOnExplode;
    }
    public boolean isRemoteDetonation() {
        return remoteDetonation;
    }
    public float getScreenShakeTime() {
        return screenShakeTime;
    }
    public float getScreenShakeAmplitude() {
        return screenShakeAmplitude;
    }

    public void setExplodeScale(float explodeScale) {
        this.explodeScale = explodeScale;
    }
    public void setExplodeDamage(float explodeDamage) {
        this.explodeDamage = explodeDamage;
    }
    public void setEnableWorldDestruction(boolean enableWorldDestruction) {
        this.enableWorldDestruction = enableWorldDestruction;
    }
    public void setDestroyMultiplier(float destroyMultiplier) {
        this.destroyMultiplier = destroyMultiplier;
    }
    public void setTriggerOnExplode(boolean triggerOnExplode) {
        this.triggerOnExplode = triggerOnExplode;
    }
    public void setRemoteDetonation(boolean remoteDetonation) {
        this.remoteDetonation = remoteDetonation;
    }
    public void setScreenShakeTime(float screenShakeTime) {
        this.screenShakeTime = screenShakeTime;
    }
    public void setScreenShakeAmplitude(float screenShakeAmplitude) {
        this.screenShakeAmplitude = screenShakeAmplitude;
    }

    // --------Back compatibility--------
}
