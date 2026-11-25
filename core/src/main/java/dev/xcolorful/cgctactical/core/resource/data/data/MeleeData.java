package dev.xcolorful.cgctactical.core.resource.data.data;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.cgctactical.core.api.resource.data.data.MeleeDataTag;
import dev.xcolorful.cgctactical.core.resource.data.data.melee._AttackData;
import dev.xcolorful.cgctactical.core.resource.data.data.melee._AttributeInfo;
import dev.xcolorful.customgun.core.resource.ResourcePojo;
import dev.xcolorful.customgun.core.util.JsonUtils;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class MeleeData extends ResourcePojo<MeleeData> {

    private int drawTime = 0;
    private int putAwayTime = 0;
    private _AttackData attackData;
    private Map<Identifier, _AttributeInfo> attributeData;
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
                    case MeleeDataTag.ATTACK -> pojo.attackData = JsonUtils.read(reader, _AttackData::fromJson);
                    case MeleeDataTag.ATTRIBUTE_DATA, MeleeDataTag.ATTRIBUTE_DATA_OLD1 -> pojo.attributeData = JsonUtils.readRl2ObjectMap(reader, _AttributeInfo::fromJson);
                    case MeleeDataTag.MAX_DURABILITY -> pojo.maxDurability = JsonUtils.readInt(reader);
                    case MeleeDataTag.ENCHANTMENT_VALUE -> pojo.enchantmentValue = JsonUtils.readInt(reader);
                    default -> this.readCustomKey(reader, key);
                }
            }
        }
        reader.endObject();
        return pojo;
    }
    @Override
    protected boolean readCustomKey(JsonReader reader, String key) throws IOException {
        return super.readCustomKey(reader, key);
    }

    public static void toJson(JsonWriter writer, MeleeData pojo) throws IOException {
        if (pojo != null) pojo.toJson(writer);
    }
    @Override
    public void toJson(JsonWriter writer) throws IOException {
        writer.beginObject(); {
            JsonUtils.writeInt(writer, MeleeDataTag.DRAW_TIME, this.drawTime);
            JsonUtils.writeInt(writer, MeleeDataTag.PUT_AWAY_TIME, this.putAwayTime);
            JsonUtils.write(writer, MeleeDataTag.ATTACK, this.attackData, _AttackData::toJson);
            JsonUtils.writeRl2ObjectMap(writer, MeleeDataTag.ATTRIBUTE_DATA, this.attributeData, _AttributeInfo::toJson);
            JsonUtils.writeInt(writer, MeleeDataTag.MAX_DURABILITY, this.maxDurability);
            JsonUtils.writeInt(writer, MeleeDataTag.ENCHANTMENT_VALUE, this.enchantmentValue);
            writeCustomValue(writer);
        }
        writer.endObject();
    }
    @Override
    protected void writeCustomValue(JsonWriter writer) {
        super.writeCustomValue(writer);
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
        boolean v1 = this.attackData.isValid();
        if (!v1) {
            this.setValid(false);
            return;
        }

        for (_AttributeInfo info : this.attributeData.values()) {
            info.validate();
            if (!info.isValid()) {
                this.setValid(false);
                return;
            }
        }

        this.setValid(true);
    }

    @Override
    protected MeleeData copyTo(final MeleeData pojo) {
        pojo.setDrawTime(this.drawTime);
        pojo.setPutAwayTime(this.putAwayTime);
        pojo.setAttackData(this.attackData.copy());

        @Nullable Map<Identifier, _AttributeInfo> attributeData = null; {
            if (this.attributeData != null) {
                attributeData = new HashMap<>();
                for (var entry : this.attributeData.entrySet()) {
                    attributeData.put(entry.getKey(), entry.getValue().copy());
                }
            }
        } pojo.setAttributeData(attributeData);

        pojo.setMaxDurability(this.maxDurability);
        pojo.setEnchantmentValue(this.enchantmentValue);
        return pojo;
    }

    @Override
    public MeleeData copy() {
        MeleeData pojo = copyTo(new MeleeData());
        pojo.setValid(this.isValid());
        return pojo;
    }

    // --------Getter & Setter--------

    public int getDrawTime() {
        return drawTime;
    }
    public int getPutAwayTime() {
        return putAwayTime;
    }
    public _AttackData getAttackData() {
        return attackData;
    }
    public Map<Identifier, _AttributeInfo> getAttributeData() {
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
    public void setAttackData(_AttackData attackData) {
        this.attackData = attackData;
    }
    public void setAttributeData(Map<Identifier, _AttributeInfo> attributeData) {
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
        this.attackData = this.attackData == null ? new _AttackData().applyBackCompatibility() : this.attackData.applyBackCompatibility();
        if (this.attributeData == null) this.attributeData = new HashMap<>();
        else this.attributeData.values().forEach(_AttributeInfo::applyBackCompatibility);
        return this;
    }
}
