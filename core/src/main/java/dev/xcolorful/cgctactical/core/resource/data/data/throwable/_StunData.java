package dev.xcolorful.cgctactical.core.resource.data.data.throwable;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.cgctactical.core.api.resource.data.data.throwable._StunDataTag;
import dev.xcolorful.cgctactical.core.resource.data.data.throwable.stun._BlindData;
import dev.xcolorful.cgctactical.core.resource.data.data.throwable.stun._DeafenedData;
import dev.xcolorful.customgun.core.resource.ResourcePojo;
import dev.xcolorful.customgun.core.util.JsonUtils;

import java.io.IOException;

public final class _StunData extends ResourcePojo<_StunData> {

    private float radius = 24f;
    private _BlindData blind;
    private _DeafenedData deafened;

    private static final _StunData PARSER = new _StunData();
    public static _StunData fromJson(JsonReader reader) throws IOException {
        return PARSER.fromJsonReader(reader);
    }
    @Override
    protected _StunData fromJsonReader(JsonReader reader) throws IOException {
        _StunData pojo = new _StunData();
        reader.beginObject(); {
            while (reader.hasNext()) {
                String key = reader.nextName();
                switch (key) {
                    case _StunDataTag.RADIUS -> pojo.radius = JsonUtils.readFloat(reader);
                    case _StunDataTag.BLIND -> pojo.blind = JsonUtils.read(reader, _BlindData::fromJson);
                    case _StunDataTag.DEAFENED -> pojo.deafened = JsonUtils.read(reader, _DeafenedData::fromJson);
                    default -> reader.skipValue();
                }
            }
        }
        reader.endObject();
        return pojo;
    }

    public static void toJson(JsonWriter writer, _StunData pojo) throws IOException {
        if (pojo != null) pojo.toJson(writer);
    }
    @Override
    public void toJson(JsonWriter writer) throws IOException {
        writer.beginObject(); {
            JsonUtils.writeFloat(writer, _StunDataTag.RADIUS, this.radius);
            JsonUtils.write(writer, _StunDataTag.BLIND, this.blind, _BlindData::toJson);
            JsonUtils.write(writer, _StunDataTag.DEAFENED, this.deafened, _DeafenedData::toJson);
        }
        writer.endObject();
    }

    @Override
    protected void validatePojo() {
        if (ENABLE_BACK_COMPATIBILITY) this.applyBackCompatibility();

        boolean n1 = (this.blind == null | this.deafened == null);
        if (n1) {
            this.setValid(false);
            return;
        }
        this.blind.validate();
        this.deafened.validate();
        boolean v1 = (this.blind.isValid() & this.deafened.isValid());
        if (!v1) {
            this.setValid(false);
            return;
        }

        this.setValid(true);
    }

    // --------Getter & Setter--------

    public float getRadius() {
        return radius;
    }
    public _BlindData getBlind() {
        return blind;
    }
    public _DeafenedData getDeafened() {
        return deafened;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
    public void setBlind(_BlindData blind) {
        this.blind = blind;
    }
    public void setDeafened(_DeafenedData deafened) {
        this.deafened = deafened;
    }

    // --------Back compatibility--------

    @Override
    public _StunData applyBackCompatibility() {
        this.blind = this.blind == null ? new _BlindData() : this.blind;
        this.deafened = this.deafened == null ? new _DeafenedData() : this.deafened;
        return this;
    }
}
