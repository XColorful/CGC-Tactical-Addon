package dev.xcolorful.cgctactical.core.resource.data.data;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.cgctactical.core.api.resource.data.data.MeleeDataTag;
import dev.xcolorful.cgctactical.core.resource.data.data.melee._AttributeData;
import dev.xcolorful.cgctactical.core.resource.data.data.melee._CombatData;
import dev.xcolorful.customgun.core.resource.ResourcePojo;
import dev.xcolorful.customgun.core.util.JsonUtils;

import java.io.IOException;

public final class MeleeData extends ResourcePojo<MeleeData> {

    private int drawTime = 0;
    private int putAwayTime = 0;
    private _CombatData attackData;
    private _AttributeData attributeData;
    private int maxDurability = 0;
    private int enchantmentValue = 14;

    private static final MeleeData PARSER = new MeleeData();
    public static MeleeData fromJson(JsonReader reader) throws IOException {
        return PARSER.fromJsonReader(reader);
    }
    @Override
    protected MeleeData fromJsonReader(JsonReader reader) throws IOException {
        MeleeData pojo = new MeleeData();
        reader.beginObject(); {
            while (reader.hasNext()) {
                String key = reader.nextName();
                switch (key) {
                    case MeleeDataTag.DRAW_TIME -> pojo.drawTime = JsonUtils.readInt(reader);
                    case MeleeDataTag.PUT_AWAY_TIME -> pojo.putAwayTime = JsonUtils.readInt(reader);
                    case MeleeDataTag.ATTACK -> pojo.attackData = JsonUtils.read(reader, _CombatData::fromJson);
                    case MeleeDataTag.ATTRIBUTES -> pojo.attributeData = JsonUtils.read(reader, _AttributeData::fromJson);
                    case MeleeDataTag.MAX_DURABILITY -> pojo.maxDurability = JsonUtils.readInt(reader);
                    case MeleeDataTag.ENCHANTMENT_VALUE -> pojo.enchantmentValue = JsonUtils.readInt(reader);
                    default -> reader.skipValue();
                }
            }
        }
        reader.endObject();
        return pojo;
    }

    public static void toJson(JsonWriter writer, MeleeData pojo) throws IOException {
        if (pojo != null) pojo.toJson(writer);
    }
    @Override
    public void toJson(JsonWriter writer) throws IOException {
        writer.beginObject(); {
            JsonUtils.writeInt(writer, MeleeDataTag.DRAW_TIME, this.drawTime);
            JsonUtils.writeInt(writer, MeleeDataTag.PUT_AWAY_TIME, this.putAwayTime);
            JsonUtils.write(writer, MeleeDataTag.ATTACK, this.attackData, _CombatData::toJson);
            JsonUtils.write(writer, MeleeDataTag.ATTRIBUTES, this.attributeData, _AttributeData::toJson);
            JsonUtils.writeInt(writer, MeleeDataTag.MAX_DURABILITY, this.maxDurability);
            JsonUtils.writeInt(writer, MeleeDataTag.ENCHANTMENT_VALUE, this.enchantmentValue);
        }
        writer.endObject();
    }

    @Override
    protected void validatePojo() {
        if (ENABLE_BACK_COMPATIBILITY) this.applyBackCompatibility();

        boolean n1 = (this.attackData == null | this.attributeData == null);
        if (n1) {
            this.setValid(false);
            return;
        }
        this.attackData.validate();
        this.attributeData.validate();
        boolean v1 = (this.attackData.isValid() & this.attributeData.isValid());
        if (!v1) {
            this.setValid(false);
            return;
        }

        this.setValid(true);
    }

    // --------Getter & Setter--------

    public int getDrawTime() {
        return drawTime;
    }
    public int getPutAwayTime() {
        return putAwayTime;
    }
    public _CombatData getAttackData() {
        return attackData;
    }
    public _AttributeData getAttributeData() {
        return attributeData;
    }
    public int getMaxDurability() {
        return maxDurability;
    }
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    public void setDrawTime(int drawTime) {
        this.drawTime = drawTime;
    }
    public void setPutAwayTime(int putAwayTime) {
        this.putAwayTime = putAwayTime;
    }
    public void setAttackData(_CombatData attackData) {
        this.attackData = attackData;
    }
    public void setAttributeData(_AttributeData attributeData) {
        this.attributeData = attributeData;
    }
    public void setMaxDurability(int maxDurability) {
        this.maxDurability = maxDurability;
    }
    public void setEnchantmentValue(int enchantmentValue) {
        this.enchantmentValue = enchantmentValue;
    }

    // --------Back compatibility--------

    @Override
    public MeleeData applyBackCompatibility() {
        this.attackData = this.attackData == null ? new _CombatData().applyBackCompatibility() : this.attackData.applyBackCompatibility();
        this.attributeData = this.attributeData == null ? new _AttributeData().applyBackCompatibility() : this.attributeData.applyBackCompatibility();
        return this;
    }
}
