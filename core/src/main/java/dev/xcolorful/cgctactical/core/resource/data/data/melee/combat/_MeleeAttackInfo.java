package dev.xcolorful.cgctactical.core.resource.data.data.melee.combat;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.cgctactical.core.api.resource.data.data.melee.combat._MeleeAttackInfoTag;
import dev.xcolorful.cgctactical.core.resource.data.data.melee._TargetFilterData;
import dev.xcolorful.customgun.core.resource.ResourcePojo;
import dev.xcolorful.customgun.core.util.JsonUtils;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public final class _MeleeAttackInfo extends ResourcePojo<_MeleeAttackInfo> {

    private float factor = 1.0f;
    private float knockback = 0.02f;
    private int cooldown = 20;
    private int delay = 0;
    private _TargetFilterData hitbox;
    private int durabilityDamage = 1;
    private @Nullable _MeleeMovement movement;

    private static final _MeleeAttackInfo PARSER = new _MeleeAttackInfo();
    public static _MeleeAttackInfo fromJson(JsonReader reader) throws IOException {
        return PARSER.fromJsonReader(reader);
    }
    @Override
    protected _MeleeAttackInfo fromJsonReader(JsonReader reader) throws IOException {
        _MeleeAttackInfo pojo = new _MeleeAttackInfo();
        reader.beginObject(); {
            while (reader.hasNext()) {
                String key = reader.nextName();
                switch (key) {
                    case _MeleeAttackInfoTag.FACTOR -> pojo.factor = JsonUtils.readFloat(reader);
                    case _MeleeAttackInfoTag.KNOCKBACK -> pojo.knockback = JsonUtils.readFloat(reader);
                    case _MeleeAttackInfoTag.COOLDOWN -> pojo.cooldown = JsonUtils.readInt(reader);
                    case _MeleeAttackInfoTag.DELAY -> pojo.delay = JsonUtils.readInt(reader);
                    case _MeleeAttackInfoTag.HITBOX -> pojo.hitbox = JsonUtils.read(reader, _TargetFilterData::fromJson);
                    case _MeleeAttackInfoTag.DURABILITY_DAMAGE -> pojo.durabilityDamage = JsonUtils.readInt(reader);
                    case _MeleeAttackInfoTag.MOVEMENT -> pojo.movement = JsonUtils.read(reader, _MeleeMovement::fromJson);
                    default -> reader.skipValue();
                }
            }
        }
        reader.endObject();
        return pojo;
    }

    public static void toJson(JsonWriter writer, _MeleeAttackInfo pojo) throws IOException {
        if (pojo != null) pojo.toJson(writer);
    }
    @Override
    public void toJson(JsonWriter writer) throws IOException {
        writer.beginObject(); {
            JsonUtils.writeFloat(writer, _MeleeAttackInfoTag.FACTOR, this.factor);
            JsonUtils.writeFloat(writer, _MeleeAttackInfoTag.KNOCKBACK, this.knockback);
            JsonUtils.writeInt(writer, _MeleeAttackInfoTag.COOLDOWN, this.cooldown);
            JsonUtils.writeInt(writer, _MeleeAttackInfoTag.DELAY, this.delay);
            JsonUtils.write(writer, _MeleeAttackInfoTag.HITBOX, this.hitbox, _TargetFilterData::toJson);
            JsonUtils.writeInt(writer, _MeleeAttackInfoTag.DURABILITY_DAMAGE, this.durabilityDamage);
            JsonUtils.write(writer, _MeleeAttackInfoTag.MOVEMENT, this.movement, _MeleeMovement::toJson);
        }
        writer.endObject();
    }

    @Override
    protected void validatePojo() {
        if (ENABLE_BACK_COMPATIBILITY) this.applyBackCompatibility();

        boolean n1 = (this.hitbox == null);
        if (n1) {
            this.setValid(false);
            return;
        }
        this.hitbox.validate();
        if (this.movement != null) this.movement.validate();
        boolean v1 = (this.hitbox.isValid() & (this.movement == null || this.movement.isValid()));
        if (!v1) {
            this.setValid(false);
            return;
        }

        this.setValid(true);
    }

    // --------Getter & Setter--------

    public float getFactor() {
        return factor;
    }
    public float getKnockback() {
        return knockback;
    }
    public int getCooldown() {
        return cooldown;
    }
    public int getDelay() {
        return delay;
    }
    public _TargetFilterData getHitbox() {
        return hitbox;
    }
    public int getDurabilityDamage() {
        return durabilityDamage;
    }
    public @Nullable _MeleeMovement getMovement() {
        return movement;
    }

    public void setFactor(float factor) {
        this.factor = factor;
    }
    public void setKnockback(float knockback) {
        this.knockback = knockback;
    }
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
    public void setDelay(int delay) {
        this.delay = delay;
    }
    public void setHitbox(_TargetFilterData hitbox) {
        this.hitbox = hitbox;
    }
    public void setDurabilityDamage(int durabilityDamage) {
        this.durabilityDamage = durabilityDamage;
    }
    public void setMovement(_MeleeMovement movement) {
        this.movement = movement;
    }

    // --------Back compatibility--------

    @Override
    public _MeleeAttackInfo applyBackCompatibility() {
        this.hitbox = this.hitbox == null ? new _TargetFilterData().applyBackCompatibility() : this.hitbox.applyBackCompatibility();
        if (this.movement != null) this.movement.applyBackCompatibility();
        return this;
    }
}
