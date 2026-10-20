package dev.xcolorful.cgctactical.core.resource.data.data.melee;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.cgctactical.core.api.resource.data.data.melee._TargetFilterDataTag;
import dev.xcolorful.customgun.core.resource.ResourcePojo;
import dev.xcolorful.customgun.core.util.JsonUtils;

import java.io.IOException;

public final class _TargetFilterData extends ResourcePojo<_TargetFilterData> {

    private String type = "cone"; // TODO
    private float maxRange = 3.5f;
    private float maxAngle = 60f;
    private boolean excludeSelf = true;
    private int penetration = 0;
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
                    case _TargetFilterDataTag.TYPE -> pojo.type = JsonUtils.readString(reader);
                    case _TargetFilterDataTag.MAX_RANGE -> pojo.maxRange = JsonUtils.readFloat(reader);
                    case _TargetFilterDataTag.EXCLUDE_SELF -> pojo.excludeSelf = JsonUtils.readBoolean(reader);
                    case _TargetFilterDataTag.MAX_ANGLE -> pojo.maxAngle = JsonUtils.readFloat(reader);
                    case _TargetFilterDataTag.PENETRATION -> pojo.penetration = JsonUtils.readInt(reader);
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
            JsonUtils.writeString(writer, _TargetFilterDataTag.TYPE, this.type);
            JsonUtils.writeFloat(writer, _TargetFilterDataTag.MAX_RANGE, this.maxRange);
            JsonUtils.writeBoolean(writer, _TargetFilterDataTag.EXCLUDE_SELF, this.excludeSelf);
            JsonUtils.writeFloat(writer, _TargetFilterDataTag.MAX_ANGLE, this.maxAngle);
            JsonUtils.writeInt(writer, _TargetFilterDataTag.PENETRATION, this.penetration);
            JsonUtils.writeFloat(writer, _TargetFilterDataTag.HALF_WIDTH, this.halfWidth);
            JsonUtils.writeFloat(writer, _TargetFilterDataTag.HALF_HEIGHT, this.halfHeight);
            JsonUtils.writeFloat(writer, _TargetFilterDataTag.ROLL, this.roll);
        }
        writer.endObject();
    }

    @Override
    protected void validatePojo() {
        if (ENABLE_BACK_COMPATIBILITY) this.applyBackCompatibility();

        if (this.type == null) {
            this.setValid(false);
            return;
        }

        this.setValid(true);
    }

    // --------Getter & Setter--------

    public String getType() {
        return type;
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
    public int getPenetration() {
        return penetration;
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

    public void setType(String type) {
        this.type = type;
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
    public void setPenetration(int penetration) {
        this.penetration = penetration;
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
        return this;
    }
}
