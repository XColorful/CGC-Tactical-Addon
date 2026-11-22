package dev.xcolorful.cgctactical.core.resource.data.data.melee.attack.info;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.cgctactical.core.api.melee.physics.HitVolumeType;
import dev.xcolorful.cgctactical.core.api.melee.physics.IHitVolume;
import dev.xcolorful.cgctactical.core.api.resource.data.data.melee.attack.info._TargetFilterDataTag;
import dev.xcolorful.customgun.core.resource.ResourcePojo;
import dev.xcolorful.customgun.core.util.JsonUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public final class _TargetFilterData extends ResourcePojo<_TargetFilterData> {

    private HitVolumeType hitVolumeType;
    private float maxRange = 3.5f;
    private float maxAngle = 60f;
    private boolean excludeSelf = true;
    private int pierce = 0;
    private float halfWidth = 0.5f;
    private float halfHeight = 0.5f;
    private float roll = 0f;

    private static final _TargetFilterData PARSER = new _TargetFilterData();
    public static _TargetFilterData fromJson(JsonReader reader) throws IOException {
        return PARSER.fromJsonReader(reader);
    }
    @Override
    protected _TargetFilterData fromJsonReader(JsonReader reader) throws IOException {
        _TargetFilterData pojo = new _TargetFilterData();
        reader.beginObject(); {
            while (reader.hasNext()) {
                String key = reader.nextName();
                switch (key) {
                    case _TargetFilterDataTag.HIT_VOLUME_TYPE, _TargetFilterDataTag.HIT_VOLUME_TYPE_OLD1 -> pojo.hitVolumeType = JsonUtils.readFromString(reader, HitVolumeType::fromString);
                    case _TargetFilterDataTag.MAX_RANGE -> pojo.maxRange = JsonUtils.readFloat(reader);
                    case _TargetFilterDataTag.EXCLUDE_SELF -> pojo.excludeSelf = JsonUtils.readBoolean(reader);
                    case _TargetFilterDataTag.MAX_ANGLE -> pojo.maxAngle = JsonUtils.readFloat(reader);
                    case _TargetFilterDataTag.PIERCE, _TargetFilterDataTag.PIERCE_OLD1 -> pojo.pierce = JsonUtils.readInt(reader);
                    case _TargetFilterDataTag.HALF_WIDTH -> pojo.halfWidth = JsonUtils.readFloat(reader);
                    case _TargetFilterDataTag.HALF_HEIGHT -> pojo.halfHeight = JsonUtils.readFloat(reader);
                    case _TargetFilterDataTag.ROLL -> pojo.roll = JsonUtils.readFloat(reader);
                    default -> reader.skipValue();
                }
            }
        }
        reader.endObject();
        return pojo;
    }

    public static void toJson(JsonWriter writer, _TargetFilterData pojo) throws IOException {
        if (pojo != null) pojo.toJson(writer);
    }
    @Override
    public void toJson(JsonWriter writer) throws IOException {
        writer.beginObject(); {
            JsonUtils.writeToString(writer, _TargetFilterDataTag.HIT_VOLUME_TYPE, this.hitVolumeType);
            JsonUtils.writeFloat(writer, _TargetFilterDataTag.MAX_RANGE, this.maxRange);
            JsonUtils.writeBoolean(writer, _TargetFilterDataTag.EXCLUDE_SELF, this.excludeSelf);
            JsonUtils.writeFloat(writer, _TargetFilterDataTag.MAX_ANGLE, this.maxAngle);
            JsonUtils.writeInt(writer, _TargetFilterDataTag.PIERCE, this.pierce);
            JsonUtils.writeFloat(writer, _TargetFilterDataTag.HALF_WIDTH, this.halfWidth);
            JsonUtils.writeFloat(writer, _TargetFilterDataTag.HALF_HEIGHT, this.halfHeight);
            JsonUtils.writeFloat(writer, _TargetFilterDataTag.ROLL, this.roll);
        }
        writer.endObject();
    }

    @Override
    protected void validatePojo() {
        if (ENABLE_BACK_COMPATIBILITY) this.applyBackCompatibility();

        if (this.hitVolumeType == null) {
            this.setValid(false);
            return;
        }

        this.setValid(true);
    }

    @Override
    protected _TargetFilterData copyTo(final _TargetFilterData pojo) {
        pojo.setHitVolumeType(this.hitVolumeType);
        pojo.setMaxRange(this.maxRange);
        pojo.setMaxAngle(this.maxAngle);
        pojo.setExcludeSelf(this.excludeSelf);
        pojo.setPierce(this.pierce);
        pojo.setHalfWidth(this.halfWidth);
        pojo.setHalfHeight(this.halfHeight);
        pojo.setRoll(this.roll);
        return pojo;
    }

    @Override
    public _TargetFilterData copy() {
        _TargetFilterData pojo = copyTo(new _TargetFilterData());
        pojo.setValid(this.isValid());
        return pojo;
    }

    // --------Getter & Setter--------

    public HitVolumeType getHitVolumeType() {
        return hitVolumeType;
    }
    public float getMaxRange() {
        return maxRange;
    }
    public float getMaxAngle() {
        return maxAngle;
    }
    public boolean isExcludeSelf() {
        return excludeSelf;
    }
    public int getPierce() {
        return pierce;
    }
    public float getHalfWidth() {
        return halfWidth;
    }
    public float getHalfHeight() {
        return halfHeight;
    }
    public float getRoll() {
        return roll;
    }

    public void setHitVolumeType(HitVolumeType hitVolumeType) {
        this.hitVolumeType = hitVolumeType;
    }
    public void setMaxRange(float maxRange) {
        this.maxRange = maxRange;
    }
    public void setMaxAngle(float maxAngle) {
        this.maxAngle = maxAngle;
    }
    public void setExcludeSelf(boolean excludeSelf) {
        this.excludeSelf = excludeSelf;
    }
    public void setPierce(int pierce) {
        this.pierce = pierce;
    }
    public void setHalfWidth(float halfWidth) {
        this.halfWidth = halfWidth;
    }
    public void setHalfHeight(float halfHeight) {
        this.halfHeight = halfHeight;
    }
    public void setRoll(float roll) {
        this.roll = roll;
    }

    // --------Back compatibility--------

    @Override
    public _TargetFilterData applyBackCompatibility() {
        if (this.hitVolumeType == null) this.hitVolumeType = HitVolumeType.CONE;
        return this;
    }

    // --------Deprecated--------

    @Deprecated(forRemoval = true)
    public @NotNull List<Entity> filterTargets(LivingEntity livingCombatant,
                                               Vec3 origin, Vec3 direction) {
        IHitVolume iHitVolume = this.hitVolumeType.getHitVolume();
        return iHitVolume.collectTargets(livingCombatant.level(), livingCombatant,  this,  origin,  direction);
    }
}
