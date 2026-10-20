package dev.xcolorful.cgctactical.core.resource.data.data.melee;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.cgctactical.core.api.resource.data.data.melee._CombatDataTag;
import dev.xcolorful.cgctactical.core.resource.data.data.melee.combat._MeleeAttackInfo;
import dev.xcolorful.customgun.core.resource.ResourcePojo;
import dev.xcolorful.customgun.core.util.JsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class _CombatData extends ResourcePojo<_CombatData> {

    private List<_MeleeAttackInfo> leftAttacks;
    private List<_MeleeAttackInfo> rightAttacks;

    private static final _CombatData PARSER = new _CombatData();
    public static _CombatData fromJson(JsonReader reader) throws IOException {
        return PARSER.fromJsonReader(reader);
    }
    @Override
    protected _CombatData fromJsonReader(JsonReader reader) throws IOException {
        _CombatData pojo = new _CombatData();
        reader.beginObject(); {
            while (reader.hasNext()) {
                String key = reader.nextName();
                switch (key) {
                    case _CombatDataTag.ATTACK_LEFT -> pojo.leftAttacks = readAttackInfos(reader);
                    case _CombatDataTag.ATTACK_RIGHT -> pojo.rightAttacks = readAttackInfos(reader);
                    default -> reader.skipValue();
                }
            }
        }
        reader.endObject();
        return pojo;
    }

    private static List<_MeleeAttackInfo> readAttackInfos(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.BEGIN_ARRAY) {
            return JsonUtils.readList(reader, _MeleeAttackInfo::fromJson);
        }
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return new ArrayList<>();
        }
        if (reader.peek() == JsonToken.BEGIN_OBJECT) {
            var attackInfo = _MeleeAttackInfo.fromJson(reader);
            var attackInfos = new ArrayList<_MeleeAttackInfo>();
            if (attackInfo != null) attackInfos.add(attackInfo);
            return attackInfos;
        }
        reader.skipValue();
        return new ArrayList<>();
    }

    public static void toJson(JsonWriter writer, _CombatData pojo) throws IOException {
        if (pojo != null) pojo.toJson(writer);
    }
    @Override
    public void toJson(JsonWriter writer) throws IOException {
        writer.beginObject(); {
            JsonUtils.writeList(writer, _CombatDataTag.ATTACK_LEFT, this.leftAttacks, _MeleeAttackInfo::toJson);
            JsonUtils.writeList(writer, _CombatDataTag.ATTACK_RIGHT, this.rightAttacks, _MeleeAttackInfo::toJson);
        }
        writer.endObject();
    }

    @Override
    protected void validatePojo() {
        if (ENABLE_BACK_COMPATIBILITY) this.applyBackCompatibility();

        boolean n1 = (this.leftAttacks == null | this.rightAttacks == null);
        if (n1) {
            this.setValid(false);
            return;
        }

        for (_MeleeAttackInfo attackInfo : this.leftAttacks) {
            attackInfo.validate();
            if (!attackInfo.isValid()) {
                this.setValid(false);
                return;
            }
        }
        for (_MeleeAttackInfo attackInfo : this.rightAttacks) {
            attackInfo.validate();
            if (!attackInfo.isValid()) {
                this.setValid(false);
                return;
            }
        }

        this.setValid(true);
    }

    // --------Getter & Setter--------

    public List<_MeleeAttackInfo> getLeftAttacks() {
        return leftAttacks;
    }
    public List<_MeleeAttackInfo> getRightAttacks() {
        return rightAttacks;
    }

    public void setLeftAttacks(List<_MeleeAttackInfo> leftAttacks) {
        this.leftAttacks = leftAttacks;
    }
    public void setRightAttacks(List<_MeleeAttackInfo> rightAttacks) {
        this.rightAttacks = rightAttacks;
    }

    // --------Back compatibility--------

    @Override
    public _CombatData applyBackCompatibility() {
        if (this.leftAttacks == null) this.leftAttacks = new ArrayList<>();
        else this.leftAttacks.forEach(_MeleeAttackInfo::applyBackCompatibility);
        if (this.rightAttacks == null) this.rightAttacks = new ArrayList<>();
        else this.rightAttacks.forEach(_MeleeAttackInfo::applyBackCompatibility);
        return this;
    }
}
