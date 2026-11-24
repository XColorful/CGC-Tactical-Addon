package dev.xcolorful.cgctactical.core.resource.data.data.melee;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.cgctactical.core.api.minecraft.attribute.AttributeModifierOperation;
import dev.xcolorful.cgctactical.core.api.resource.data.data.melee._AttributeInfoTag;
import dev.xcolorful.customgun.core.resource.ResourcePojo;
import dev.xcolorful.customgun.core.util.JsonUtils;

import java.io.IOException;

public final class _AttributeInfo extends ResourcePojo<_AttributeInfo> {

    private float amount = 0f;
    private AttributeModifierOperation modifierOperation;

    private static final _AttributeInfo PARSER = new _AttributeInfo();
    public static _AttributeInfo fromJson(JsonReader reader) throws IOException {
        return PARSER.fromJsonReader(reader);
    }
    @Override
    protected _AttributeInfo fromJsonReader(JsonReader reader) throws IOException {
        _AttributeInfo pojo = new _AttributeInfo();
        if (reader.peek() != JsonToken.BEGIN_OBJECT) {
            // value 直接是数值时等价于 amount + SHARED_BASE_ADD
            pojo.amount = JsonUtils.readFloat(reader);
            return pojo;
        }

        reader.beginObject(); {
            while (reader.hasNext()) {
                String key = reader.nextName();
                switch (key) {
                    case _AttributeInfoTag.AMOUNT -> pojo.amount = JsonUtils.readFloat(reader);
                    case _AttributeInfoTag.MODIFIER_OPERATION, _AttributeInfoTag.MODIFIER_OPERATION_OLD1 -> pojo.modifierOperation = JsonUtils.readFromString(reader, AttributeModifierOperation::fromString);
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

    public static void toJson(JsonWriter writer, _AttributeInfo pojo) throws IOException {
        if (pojo != null) pojo.toJson(writer);
    }
    @Override
    public void toJson(JsonWriter writer) throws IOException {
        writer.beginObject(); {
            JsonUtils.writeFloat(writer, _AttributeInfoTag.AMOUNT, this.amount);
            JsonUtils.writeToString(writer, _AttributeInfoTag.MODIFIER_OPERATION, this.modifierOperation);
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

        boolean n1 = (this.modifierOperation == null);
        if (n1) {
            this.setValid(false);
            return;
        }

        this.setValid(true);
    }

    @Override
    protected _AttributeInfo copyTo(final _AttributeInfo pojo) {
        pojo.setAmount(this.amount);
        pojo.setModifierOperation(this.modifierOperation);
        return pojo;
    }

    @Override
    public _AttributeInfo copy() {
        _AttributeInfo pojo = copyTo(new _AttributeInfo());
        pojo.setValid(this.isValid());
        return pojo;
    }

    // --------Getter & Setter--------

    public float getAmount() {
        return amount;
    }
    public AttributeModifierOperation getModifierOperation() {
        return modifierOperation;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
    public void setModifierOperation(AttributeModifierOperation modifierOperation) {
        this.modifierOperation = modifierOperation;
    }

    // --------Back compatibility--------

    @Override
    public _AttributeInfo applyBackCompatibility() {
        if (this.modifierOperation == null) this.modifierOperation = AttributeModifierOperation.SHARED_BASE_ADD;
        return this;
    }
}
