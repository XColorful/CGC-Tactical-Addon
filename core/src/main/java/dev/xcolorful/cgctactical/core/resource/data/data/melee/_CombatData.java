package dev.xcolorful.cgctactical.core.resource.data.data.melee;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.cgctactical.core.api.resource.data.data.melee._CombatDataTag;
import dev.xcolorful.cgctactical.core.resource.data.data.melee.combat._MeleeAttackInfo;
import dev.xcolorful.customgun.core.resource.ResourcePojo;
import dev.xcolorful.customgun.core.util.JsonUtils;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class _CombatData extends ResourcePojo<_CombatData> {

    private @Nullable List<_MeleeAttackInfo> leftAttacks;
    private @Nullable List<_MeleeAttackInfo> rightAttacks;

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
                    case _CombatDataTag.PRIMARY_ACTION, _CombatDataTag.PRIMARY_ACTION_OLD1 -> pojo.leftAttacks = readAttackInfos(reader);
                    case _CombatDataTag.SECONDARY_ACTION, _CombatDataTag.SECONDARY_ACTION_OLD1 -> pojo.rightAttacks = readAttackInfos(reader);
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
            JsonUtils.writeList(writer, _CombatDataTag.PRIMARY_ACTION, this.leftAttacks, _MeleeAttackInfo::toJson);
            JsonUtils.writeList(writer, _CombatDataTag.SECONDARY_ACTION, this.rightAttacks, _MeleeAttackInfo::toJson);
        }
        writer.endObject();
    }

    @Override
    protected void validatePojo() {
        if (ENABLE_BACK_COMPATIBILITY) this.applyBackCompatibility();

        if (this.leftAttacks != null) {
            for (int i = 0; i < this.leftAttacks.size(); i++) {
                _MeleeAttackInfo attackInfo = this.leftAttacks.get(i);
                attackInfo.validate();
                if (!attackInfo.isValid()) {
                    this.setValid(false);
                    return;
                }
            }
        }
        if (this.rightAttacks != null) {
            for (int i = 0; i < this.rightAttacks.size(); i++) {
                _MeleeAttackInfo attackInfo = this.rightAttacks.get(i);
                attackInfo.validate();
                if (!attackInfo.isValid()) {
                    this.setValid(false);
                    return;
                }
            }
        }

        this.setValid(true);
    }

    @Override
    protected _CombatData copyTo(final _CombatData pojo) {
        @Nullable List<_MeleeAttackInfo> leftAttacks = null; {
            if (this.leftAttacks != null) {
                leftAttacks = new ArrayList<>(this.leftAttacks.size());
                for (int i = 0; i < this.leftAttacks.size(); i++) {
                    leftAttacks.add(this.leftAttacks.get(i).copy());
                }
            }
        } pojo.setLeftAttacks(leftAttacks);
        @Nullable List<_MeleeAttackInfo> rightAttacks = null; {
            if (this.rightAttacks != null) {
                rightAttacks = new ArrayList<>(this.rightAttacks.size());
                for (int i = 0; i < this.rightAttacks.size(); i++) {
                    rightAttacks.add(this.rightAttacks.get(i).copy());
                }
            }
        } pojo.setRightAttacks(rightAttacks);
        return pojo;
    }

    @Override
    public _CombatData copy() {
        _CombatData pojo = copyTo(new _CombatData());
        pojo.setValid(this.isValid());
        return pojo;
    }

    // --------Getter & Setter--------

    public @Nullable List<_MeleeAttackInfo> getLeftAttacks() {
        return leftAttacks;
    }
    public @Nullable List<_MeleeAttackInfo> getRightAttacks() {
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
