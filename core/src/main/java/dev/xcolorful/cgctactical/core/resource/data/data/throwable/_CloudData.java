package dev.xcolorful.cgctactical.core.resource.data.data.throwable;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.cgctactical.core.api.resource.data.data.throwable._CloudDataTag;
import dev.xcolorful.cgctactical.core.resource.data.data.throwable.cloud._EffectData;
import dev.xcolorful.customgun.core.resource.ResourcePojo;
import dev.xcolorful.customgun.core.util.JsonUtils;
import net.minecraft.resources.Identifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class _CloudData extends ResourcePojo<_CloudData> {

    private static final Identifier DEFAULT_PARTICLES = JsonUtils.mcRegistry.createResourceLocation("minecraft:effect"); // TODO

    private boolean areaCloud = true;
    private float radius = 5.5f;
    private float radiusPerTick = 0.01f;
    private int waitTime = 20;
    private int duration = 200;
    private Identifier particlesLocation = DEFAULT_PARTICLES;
    private boolean ignite = false;
    private int igniteTime = 2;
    private boolean extinguishBySmoke = false;
    private List<_EffectData> effects;

    private static final _CloudData PARSER = new _CloudData();
    public static _CloudData fromJson(JsonReader reader) throws IOException {
        return PARSER.fromJsonReader(reader);
    }
    @Override
    protected _CloudData fromJsonReader(JsonReader reader) throws IOException {
        _CloudData pojo = new _CloudData();
        reader.beginObject(); {
            while (reader.hasNext()) {
                String key = reader.nextName();
                switch (key) {
                    case _CloudDataTag.AREA_CLOUD -> pojo.areaCloud = JsonUtils.readBoolean(reader);
                    case _CloudDataTag.RADIUS -> pojo.radius = JsonUtils.readFloat(reader);
                    case _CloudDataTag.RADIUS_PER_TICK -> pojo.radiusPerTick = JsonUtils.readFloat(reader);
                    case _CloudDataTag.WAIT_TIME -> pojo.waitTime = JsonUtils.readInt(reader);
                    case _CloudDataTag.DURATION -> pojo.duration = JsonUtils.readInt(reader);
                    case _CloudDataTag.PARTICLES -> pojo.particlesLocation = JsonUtils.readResourceLocation(reader);
                    case _CloudDataTag.IGNITE -> pojo.ignite = JsonUtils.readBoolean(reader);
                    case _CloudDataTag.IGNITE_TIME -> pojo.igniteTime = JsonUtils.readInt(reader);
                    case _CloudDataTag.EXTINGUISH_BY_SMOKE -> pojo.extinguishBySmoke = JsonUtils.readBoolean(reader);
                    case _CloudDataTag.EFFECTS -> pojo.effects = JsonUtils.readList(reader, _EffectData::fromJson);
                    default -> reader.skipValue();
                }
            }
        }
        reader.endObject();
        return pojo;
    }

    public static void toJson(JsonWriter writer, _CloudData pojo) throws IOException {
        if (pojo != null) pojo.toJson(writer);
    }
    @Override
    public void toJson(JsonWriter writer) throws IOException {
        writer.beginObject(); {
            JsonUtils.writeBoolean(writer, _CloudDataTag.AREA_CLOUD, this.areaCloud);
            JsonUtils.writeFloat(writer, _CloudDataTag.RADIUS, this.radius);
            JsonUtils.writeFloat(writer, _CloudDataTag.RADIUS_PER_TICK, this.radiusPerTick);
            JsonUtils.writeInt(writer, _CloudDataTag.WAIT_TIME, this.waitTime);
            JsonUtils.writeInt(writer, _CloudDataTag.DURATION, this.duration);
            JsonUtils.writeResourceLocation(writer, _CloudDataTag.PARTICLES, this.particlesLocation);
            JsonUtils.writeBoolean(writer, _CloudDataTag.IGNITE, this.ignite);
            JsonUtils.writeInt(writer, _CloudDataTag.IGNITE_TIME, this.igniteTime);
            JsonUtils.writeBoolean(writer, _CloudDataTag.EXTINGUISH_BY_SMOKE, this.extinguishBySmoke);
            JsonUtils.writeList(writer, _CloudDataTag.EFFECTS, this.effects, _EffectData::toJson);
        }
        writer.endObject();
    }

    @Override
    protected void validatePojo() {
        if (ENABLE_BACK_COMPATIBILITY) this.applyBackCompatibility();

        boolean n1 = (this.particlesLocation == null | this.effects == null);
        if (n1) {
            this.setValid(false);
            return;
        }

        for (_EffectData effect : this.effects) {
            effect.validate();
            if (!effect.isValid()) {
                this.setValid(false);
                return;
            }
        }

        this.setValid(true);
    }

    // --------Getter & Setter--------

    public boolean isAreaCloud() {
        return areaCloud;
    }
    public float getRadius() {
        return radius;
    }
    public float getRadiusPerTick() {
        return radiusPerTick;
    }
    public int getWaitTime() {
        return waitTime;
    }
    public int getDuration() {
        return duration;
    }
    public Identifier getParticlesLocation() {
        return particlesLocation;
    }
    public boolean isIgnite() {
        return ignite;
    }
    public int getIgniteTime() {
        return igniteTime;
    }
    public boolean isExtinguishBySmoke() {
        return extinguishBySmoke;
    }
    public List<_EffectData> getEffects() {
        return effects;
    }

    public void setAreaCloud(boolean areaCloud) {
        this.areaCloud = areaCloud;
    }
    public void setRadius(float radius) {
        this.radius = radius;
    }
    public void setRadiusPerTick(float radiusPerTick) {
        this.radiusPerTick = radiusPerTick;
    }
    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public void setParticlesLocation(Identifier particlesLocation) {
        this.particlesLocation = particlesLocation;
    }
    public void setIgnite(boolean ignite) {
        this.ignite = ignite;
    }
    public void setIgniteTime(int igniteTime) {
        this.igniteTime = igniteTime;
    }
    public void setExtinguishBySmoke(boolean extinguishBySmoke) {
        this.extinguishBySmoke = extinguishBySmoke;
    }
    public void setEffects(List<_EffectData> effects) {
        this.effects = effects;
    }

    // --------Back compatibility--------

    @Override
    public _CloudData applyBackCompatibility() {
        this.particlesLocation = this.particlesLocation == null ? DEFAULT_PARTICLES : this.particlesLocation;
        if (this.effects == null) this.effects = new ArrayList<>();
        else this.effects.forEach(_EffectData::applyBackCompatibility);
        return this;
    }
}
