package dev.xcolorful.cgctactical.core.resource.data.data.throwable;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.cgctactical.core.api.resource.data.data.throwable._EntityDataTag;
import dev.xcolorful.customgun.core.resource.ResourcePojo;
import dev.xcolorful.customgun.core.util.JsonUtils;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public final class _EntityData extends ResourcePojo<_EntityData> {

    private static final ResourceLocation DEFAULT_TAIL_PARTICLES = JsonUtils.mcRegistry.createResourceLocation("minecraft:smoke"); // TODO

    private int lifeTime = 100;
    private float gravity = 0.07f;
    private boolean shouldBounce = true;
    private boolean brokeOnGround = false;
    private float bounceFactor = 0.75f;
    private float hitDamage = 1.0f;
    private @Nullable ResourceLocation tailParticlesLocation = DEFAULT_TAIL_PARTICLES;

    private static final _EntityData PARSER = new _EntityData();
    public static _EntityData fromJson(JsonReader reader) throws IOException {
        return PARSER.fromJsonReader(reader);
    }
    @Override
    protected _EntityData fromJsonReader(JsonReader reader) throws IOException {
        _EntityData pojo = new _EntityData();
        reader.beginObject(); {
            while (reader.hasNext()) {
                String key = reader.nextName();
                switch (key) {
                    case _EntityDataTag.LIFE_TIME -> pojo.lifeTime = JsonUtils.readInt(reader);
                    case _EntityDataTag.GRAVITY -> pojo.gravity = JsonUtils.readFloat(reader);
                    case _EntityDataTag.SHOULD_BOUNCE -> pojo.shouldBounce = JsonUtils.readBoolean(reader);
                    case _EntityDataTag.BROKE_ON_GROUND -> pojo.brokeOnGround = JsonUtils.readBoolean(reader);
                    case _EntityDataTag.BOUNCE_FACTOR -> pojo.bounceFactor = JsonUtils.readFloat(reader);
                    case _EntityDataTag.HIT_DAMAGE -> pojo.hitDamage = JsonUtils.readFloat(reader);
                    case _EntityDataTag.TAIL_PARTICLES -> pojo.tailParticlesLocation = JsonUtils.readResourceLocation(reader);
                    default -> reader.skipValue();
                }
            }
        }
        reader.endObject();
        return pojo;
    }

    public static void toJson(JsonWriter writer, _EntityData pojo) throws IOException {
        if (pojo != null) pojo.toJson(writer);
    }
    @Override
    public void toJson(JsonWriter writer) throws IOException {
        writer.beginObject(); {
            JsonUtils.writeInt(writer, _EntityDataTag.LIFE_TIME, this.lifeTime);
            JsonUtils.writeFloat(writer, _EntityDataTag.GRAVITY, this.gravity);
            JsonUtils.writeBoolean(writer, _EntityDataTag.SHOULD_BOUNCE, this.shouldBounce);
            JsonUtils.writeBoolean(writer, _EntityDataTag.BROKE_ON_GROUND, this.brokeOnGround);
            JsonUtils.writeFloat(writer, _EntityDataTag.BOUNCE_FACTOR, this.bounceFactor);
            JsonUtils.writeFloat(writer, _EntityDataTag.HIT_DAMAGE, this.hitDamage);
            JsonUtils.writeResourceLocation(writer, _EntityDataTag.TAIL_PARTICLES, this.tailParticlesLocation);
        }
        writer.endObject();
    }

    @Override
    protected void validatePojo() {
        this.setValid(true);
    }

    // --------Getter & Setter--------

    public int getLifeTime() {
        return lifeTime;
    }
    public float getGravity() {
        return gravity;
    }
    public boolean isShouldBounce() {
        return shouldBounce;
    }
    public boolean isBrokeOnGround() {
        return brokeOnGround;
    }
    public float getBounceFactor() {
        return bounceFactor;
    }
    public float getHitDamage() {
        return hitDamage;
    }
    public @Nullable ResourceLocation getTailParticlesLocation() {
        return tailParticlesLocation;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }
    public void setGravity(float gravity) {
        this.gravity = gravity;
    }
    public void setShouldBounce(boolean shouldBounce) {
        this.shouldBounce = shouldBounce;
    }
    public void setBrokeOnGround(boolean brokeOnGround) {
        this.brokeOnGround = brokeOnGround;
    }
    public void setBounceFactor(float bounceFactor) {
        this.bounceFactor = bounceFactor;
    }
    public void setHitDamage(float hitDamage) {
        this.hitDamage = hitDamage;
    }
    public void setTailParticlesLocation(ResourceLocation tailParticlesLocation) {
        this.tailParticlesLocation = tailParticlesLocation;
    }

    // --------Back compatibility--------
}
