package dev.xcolorful.cgctactical.core.resource.data.data.throwable.cloud;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.cgctactical.core.api.resource.data.data.throwable.cloud._EffectDataTag;
import dev.xcolorful.customgun.core.api.resource.ResourceTag;
import dev.xcolorful.customgun.core.resource.ResourcePojo;
import dev.xcolorful.customgun.core.util.JsonUtils;
import net.minecraft.resources.Identifier;

import java.io.IOException;

public final class _EffectData extends ResourcePojo<_EffectData> {

    private Identifier effectLocation;
    private int duration = 200;
    private int amplifier = 0;
    private boolean visible = true;
    private boolean showIcon = true;

    private static final _EffectData PARSER = new _EffectData();
    public static _EffectData fromJson(JsonReader reader) throws IOException {
        return PARSER.fromJsonReader(reader);
    }
    @Override
    protected _EffectData fromJsonReader(JsonReader reader) throws IOException {
        _EffectData pojo = new _EffectData();
        reader.beginObject(); {
            while (reader.hasNext()) {
                String key = reader.nextName();
                switch (key) {
                    case _EffectDataTag.EFFECT_LOCATION, _EffectDataTag.EFFECT_LOCATION_OLD1 -> pojo.effectLocation = JsonUtils.readResourceLocation(reader);
                    case _EffectDataTag.DURATION -> pojo.duration = JsonUtils.readInt(reader);
                    case _EffectDataTag.AMPLIFIER -> pojo.amplifier = JsonUtils.readInt(reader);
                    case _EffectDataTag.VISIBLE -> pojo.visible = JsonUtils.readBoolean(reader);
                    case _EffectDataTag.SHOW_ICON -> pojo.showIcon = JsonUtils.readBoolean(reader);
                    default -> reader.skipValue();
                }
            }
        }
        reader.endObject();
        return pojo;
    }

    public static void toJson(JsonWriter writer, _EffectData pojo) throws IOException {
        if (pojo != null) pojo.toJson(writer);
    }
    @Override
    public void toJson(JsonWriter writer) throws IOException {
        writer.beginObject(); {
            JsonUtils.writeResourceLocation(writer, _EffectDataTag.EFFECT_LOCATION, this.effectLocation);
            JsonUtils.writeInt(writer, _EffectDataTag.DURATION, this.duration);
            JsonUtils.writeInt(writer, _EffectDataTag.AMPLIFIER, this.amplifier);
            JsonUtils.writeBoolean(writer, _EffectDataTag.VISIBLE, this.visible);
            JsonUtils.writeBoolean(writer, _EffectDataTag.SHOW_ICON, this.showIcon);
        }
        writer.endObject();
    }

    @Override
    protected void validatePojo() {
        if (ENABLE_BACK_COMPATIBILITY) this.applyBackCompatibility();

        if (this.effectLocation == null) {
            this.setValid(false);
            return;
        }

        this.setValid(true);
    }

    // --------Getter & Setter--------

    public Identifier getEffectLocation() {
        return effectLocation;
    }
    public int getDuration() {
        return duration;
    }
    public int getAmplifier() {
        return amplifier;
    }
    public boolean isVisible() {
        return visible;
    }
    public boolean isShowIcon() {
        return showIcon;
    }

    public void setEffectLocation(Identifier effectLocation) {
        this.effectLocation = effectLocation;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public void setAmplifier(int amplifier) {
        this.amplifier = amplifier;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    public void setShowIcon(boolean showIcon) {
        this.showIcon = showIcon;
    }

    // --------Back compatibility--------

    @Override
    public _EffectData applyBackCompatibility() {
        this.effectLocation = this.effectLocation == null ? ResourceTag.NULL_LOCATION : this.effectLocation;
        return this;
    }
}
