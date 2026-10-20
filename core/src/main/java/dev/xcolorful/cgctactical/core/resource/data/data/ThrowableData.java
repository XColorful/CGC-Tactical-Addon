package dev.xcolorful.cgctactical.core.resource.data.data;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.cgctactical.core.api.resource.data.data.ThrowableDataTag;
import dev.xcolorful.cgctactical.core.resource.data.data.throwable._CloudData;
import dev.xcolorful.cgctactical.core.resource.data.data.throwable._EntityData;
import dev.xcolorful.cgctactical.core.resource.data.data.throwable._ExplodeData;
import dev.xcolorful.cgctactical.core.resource.data.data.throwable._StunData;
import dev.xcolorful.customgun.core.resource.ResourcePojo;
import dev.xcolorful.customgun.core.util.JsonUtils;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public final class ThrowableData extends ResourcePojo<ThrowableData> {

    private int prepareTime = 10;
    private boolean cookable = false;
    private float initialSpeed = 1.5f;
    private int cooldown = 40;
    private @Nullable ResourceLocation cooldownCategoryLocation; // TODO
    private int maxStackSize = 1;
    private _EntityData entityData;
    private long putAwayTime = 0;

    private @Nullable _ExplodeData explodeData;
    private @Nullable _CloudData cloudData;
    private @Nullable _StunData stunData;

    private static final ThrowableData PARSER = new ThrowableData();
    public static ThrowableData fromJson(JsonReader reader) throws IOException {
        return PARSER.fromJsonReader(reader);
    }
    @Override
    protected ThrowableData fromJsonReader(JsonReader reader) throws IOException {
        ThrowableData pojo = new ThrowableData();
        reader.beginObject(); {
            while (reader.hasNext()) {
                String key = reader.nextName();
                switch (key) {
                    case ThrowableDataTag.PREPARE_TIME -> pojo.prepareTime = JsonUtils.readInt(reader);
                    case ThrowableDataTag.COOKABLE -> pojo.cookable = JsonUtils.readBoolean(reader);
                    case ThrowableDataTag.INITIAL_SPEED -> pojo.initialSpeed = JsonUtils.readFloat(reader);
                    case ThrowableDataTag.COOLDOWN -> pojo.cooldown = JsonUtils.readInt(reader);
                    case ThrowableDataTag.COOLDOWN_CATEGORY -> pojo.cooldownCategoryLocation = JsonUtils.readResourceLocation(reader);
                    case ThrowableDataTag.MAX_STACK_SIZE, ThrowableDataTag.MAX_STACK_SIZE_OLD1 -> pojo.maxStackSize = JsonUtils.readInt(reader);
                    case ThrowableDataTag.ENTITY -> pojo.entityData = JsonUtils.read(reader, _EntityData::fromJson);
                    case ThrowableDataTag.PUT_AWAY_TIME -> pojo.putAwayTime = JsonUtils.readLong(reader);

                    case ThrowableDataTag.EXPLODE -> pojo.explodeData = JsonUtils.read(reader, _ExplodeData::fromJson);
                    case ThrowableDataTag.CLOUD -> pojo.cloudData = JsonUtils.read(reader, _CloudData::fromJson);
                    case ThrowableDataTag.STUN -> pojo.stunData = JsonUtils.read(reader, _StunData::fromJson);
                    default -> reader.skipValue();
                }
            }
        }
        reader.endObject();
        return pojo;
    }

    public static void toJson(JsonWriter writer, ThrowableData pojo) throws IOException {
        if (pojo != null) pojo.toJson(writer);
    }
    @Override
    public void toJson(JsonWriter writer) throws IOException {
        writer.beginObject(); {
            JsonUtils.writeInt(writer, ThrowableDataTag.PREPARE_TIME, this.prepareTime);
            JsonUtils.writeBoolean(writer, ThrowableDataTag.COOKABLE, this.cookable);
            JsonUtils.writeFloat(writer, ThrowableDataTag.INITIAL_SPEED, this.initialSpeed);
            JsonUtils.writeInt(writer, ThrowableDataTag.COOLDOWN, this.cooldown);
            JsonUtils.writeResourceLocation(writer, ThrowableDataTag.COOLDOWN_CATEGORY, this.cooldownCategoryLocation);
            JsonUtils.writeInt(writer, ThrowableDataTag.MAX_STACK_SIZE, this.maxStackSize);
            JsonUtils.write(writer, ThrowableDataTag.ENTITY, this.entityData, _EntityData::toJson);
            JsonUtils.writeLong(writer, ThrowableDataTag.PUT_AWAY_TIME, this.putAwayTime);

            JsonUtils.write(writer, ThrowableDataTag.EXPLODE, this.explodeData, _ExplodeData::toJson);
            JsonUtils.write(writer, ThrowableDataTag.CLOUD, this.cloudData, _CloudData::toJson);
            JsonUtils.write(writer, ThrowableDataTag.STUN, this.stunData, _StunData::toJson);
        }
        writer.endObject();
    }

    @Override
    protected void validatePojo() {
        if (ENABLE_BACK_COMPATIBILITY) this.applyBackCompatibility();

        boolean n1 = (this.entityData == null);
        if (n1) {
            this.setValid(false);
            return;
        }
        this.entityData.validate();
        if (this.explodeData != null) this.explodeData.validate();
        if (this.cloudData != null) this.cloudData.validate();
        if (this.stunData != null) this.stunData.validate();
        boolean v1 = (this.entityData.isValid() & (this.explodeData == null || this.explodeData.isValid()));
        boolean v2 = ((this.cloudData == null || this.cloudData.isValid()) & (this.stunData == null || this.stunData.isValid()));
        if (!(v1 & v2)) {
            this.setValid(false);
            return;
        }

        this.setValid(true);
    }

    // --------Getter & Setter--------

    public int getPrepareTime() {
        return prepareTime;
    }
    public boolean isCookable() {
        return cookable;
    }
    public float getInitialSpeed() {
        return initialSpeed;
    }
    public int getCooldown() {
        return cooldown;
    }
    public @Nullable ResourceLocation getCooldownCategoryLocation() {
        return cooldownCategoryLocation;
    }
    public int getMaxStackSize() {
        return maxStackSize;
    }
    public _EntityData getEntityData() {
        return entityData;
    }
    public long getPutAwayTime() {
        return putAwayTime;
    }
    public @Nullable _ExplodeData getExplodeData() {
        return explodeData;
    }
    public @Nullable _CloudData getCloudData() {
        return cloudData;
    }
    public @Nullable _StunData getStunData() {
        return stunData;
    }

    public void setPrepareTime(int prepareTime) {
        this.prepareTime = prepareTime;
    }
    public void setCookable(boolean cookable) {
        this.cookable = cookable;
    }
    public void setInitialSpeed(float initialSpeed) {
        this.initialSpeed = initialSpeed;
    }
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
    public void setCooldownCategoryLocation(ResourceLocation cooldownCategoryLocation) {
        this.cooldownCategoryLocation = cooldownCategoryLocation;
    }
    public void setMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
    }
    public void setEntityData(_EntityData entityData) {
        this.entityData = entityData;
    }
    public void setPutAwayTime(long putAwayTime) {
        this.putAwayTime = putAwayTime;
    }
    public void setExplodeData(_ExplodeData explodeData) {
        this.explodeData = explodeData;
    }
    public void setCloudData(_CloudData cloudData) {
        this.cloudData = cloudData;
    }
    public void setStunData(_StunData stunData) {
        this.stunData = stunData;
    }

    // --------Back compatibility--------

    @Override
    public ThrowableData applyBackCompatibility() {
        this.entityData = this.entityData == null ? new _EntityData() : this.entityData;
        if (this.explodeData != null) this.explodeData.applyBackCompatibility();
        if (this.cloudData != null) this.cloudData.applyBackCompatibility();
        if (this.stunData != null) this.stunData.applyBackCompatibility();
        return this;
    }
}
