package dev.xcolorful.cgctactical.core.resource.data.data;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.cgctactical.core.api.resource.data.data.ConsumableDataTag;
import dev.xcolorful.cgctactical.core.resource.data.data.consumable._EffectData;
import dev.xcolorful.customgun.core.resource.ResourcePojo;
import dev.xcolorful.customgun.core.util.JsonUtils;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ConsumableData extends ResourcePojo<ConsumableData> {

    private int useDuration = 32;
    private int cooldown = 0;
    private @Nullable Identifier cooldownCategoryLocation;
    private int maxStackSize = 1;
    private int maxDurability = 0;
    private int durabilityDamage = 1;
    private int drawTime = 0;
    private int putAwayTime = 0;
    private float heal = 0f;
    private int food = 0;
    private float saturation = 0f;
    private List<_EffectData> effects;
//    private List<_RemoveEffectSelector> removeEffects; // TODO
//    private UseMode useMode; // TODO

    private static final ConsumableData PARSER = new ConsumableData();
    public static ConsumableData fromJson(JsonReader reader) throws IOException {
        return PARSER.fromJsonReader(reader);
    }
    @Override
    protected ConsumableData fromJsonReader(JsonReader reader) throws IOException {
        ConsumableData pojo = new ConsumableData();
        reader.beginObject(); {
            while (reader.hasNext()) {
                String key = reader.nextName();
                switch (key) {
                    case ConsumableDataTag.USE_DURATION -> pojo.useDuration = JsonUtils.readInt(reader);
                    case ConsumableDataTag.COOLDOWN -> pojo.cooldown = JsonUtils.readInt(reader);
                    case ConsumableDataTag.COOLDOWN_CATEGORY -> pojo.cooldownCategoryLocation = JsonUtils.readResourceLocation(reader);
                    case ConsumableDataTag.MAX_STACK_SIZE, ConsumableDataTag.MAX_STACK_SIZE_OLD1 -> pojo.maxStackSize = JsonUtils.readInt(reader);
                    case ConsumableDataTag.MAX_DURABILITY -> pojo.maxDurability = JsonUtils.readInt(reader);
                    case ConsumableDataTag.DURABILITY_DAMAGE -> pojo.durabilityDamage = JsonUtils.readInt(reader);
                    case ConsumableDataTag.DRAW_TIME -> pojo.drawTime = JsonUtils.readInt(reader);
                    case ConsumableDataTag.PUT_AWAY_TIME -> pojo.putAwayTime = JsonUtils.readInt(reader);
                    case ConsumableDataTag.HEAL -> pojo.heal = JsonUtils.readFloat(reader);
                    case ConsumableDataTag.FOOD -> pojo.food = JsonUtils.readInt(reader);
                    case ConsumableDataTag.SATURATION -> pojo.saturation = JsonUtils.readFloat(reader);
                    case ConsumableDataTag.EFFECTS -> pojo.effects = JsonUtils.readList(reader, _EffectData::fromJson);
                    default -> reader.skipValue();
                }
            }
        }
        reader.endObject();
        return pojo;
    }

    public static void toJson(JsonWriter writer, ConsumableData pojo) throws IOException {
        if (pojo != null) pojo.toJson(writer);
    }
    @Override
    public void toJson(JsonWriter writer) throws IOException {
        writer.beginObject(); {
            JsonUtils.writeInt(writer, ConsumableDataTag.USE_DURATION, this.useDuration);
            JsonUtils.writeInt(writer, ConsumableDataTag.COOLDOWN, this.cooldown);
            JsonUtils.writeResourceLocation(writer, ConsumableDataTag.COOLDOWN_CATEGORY, this.cooldownCategoryLocation);
            JsonUtils.writeInt(writer, ConsumableDataTag.MAX_STACK_SIZE, this.maxStackSize);
            JsonUtils.writeInt(writer, ConsumableDataTag.MAX_DURABILITY, this.maxDurability);
            JsonUtils.writeInt(writer, ConsumableDataTag.DURABILITY_DAMAGE, this.durabilityDamage);
            JsonUtils.writeInt(writer, ConsumableDataTag.DRAW_TIME, this.drawTime);
            JsonUtils.writeInt(writer, ConsumableDataTag.PUT_AWAY_TIME, this.putAwayTime);
            JsonUtils.writeFloat(writer, ConsumableDataTag.HEAL, this.heal);
            JsonUtils.writeInt(writer, ConsumableDataTag.FOOD, this.food);
            JsonUtils.writeFloat(writer, ConsumableDataTag.SATURATION, this.saturation);
            JsonUtils.writeList(writer, ConsumableDataTag.EFFECTS, this.effects, _EffectData::toJson);
        }
        writer.endObject();
    }

    @Override
    protected void validatePojo() {
        if (ENABLE_BACK_COMPATIBILITY) this.applyBackCompatibility();

        boolean n1 = (this.effects == null);
        if (n1) {
            this.setValid(false);
            return;
        }
        if (this.maxStackSize < 1) this.maxStackSize = 1;

        for (_EffectData effect : this.effects) {
            effect.validate();
            if (!effect.isValid()) {
                this.setValid(false);
                return;
            }
        }

        this.setValid(true);
    }

    // --------Getter & Setter--------

    public int getUseDuration() {
        return useDuration;
    }
    public int getCooldown() {
        return cooldown;
    }
    public @Nullable Identifier getCooldownCategoryLocation() {
        return cooldownCategoryLocation;
    }
    public int getMaxStackSize() {
        return maxStackSize;
    }
    public int getMaxDurability() {
        return maxDurability;
    }
    public int getDurabilityDamage() {
        return durabilityDamage;
    }
    public int getDrawTime() {
        return drawTime;
    }
    public int getPutAwayTime() {
        return putAwayTime;
    }
    public float getHeal() {
        return heal;
    }
    public int getFood() {
        return food;
    }
    public float getSaturation() {
        return saturation;
    }
    public List<_EffectData> getEffects() {
        return effects;
    }

    public void setUseDuration(int useDuration) {
        this.useDuration = useDuration;
    }
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
    public void setCooldownCategoryLocation(Identifier cooldownCategoryLocation) {
        this.cooldownCategoryLocation = cooldownCategoryLocation;
    }
    public void setMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
    }
    public void setMaxDurability(int maxDurability) {
        this.maxDurability = maxDurability;
    }
    public void setDurabilityDamage(int durabilityDamage) {
        this.durabilityDamage = durabilityDamage;
    }
    public void setDrawTime(int drawTime) {
        this.drawTime = drawTime;
    }
    public void setPutAwayTime(int putAwayTime) {
        this.putAwayTime = putAwayTime;
    }
    public void setHeal(float heal) {
        this.heal = heal;
    }
    public void setFood(int food) {
        this.food = food;
    }
    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }
    public void setEffects(List<_EffectData> effects) {
        this.effects = effects;
    }

    // --------Back compatibility--------

    @Override
    public ConsumableData applyBackCompatibility() {
        if (this.effects == null) this.effects = new ArrayList<>();
        else this.effects.forEach(_EffectData::applyBackCompatibility);
        return this;
    }
}
