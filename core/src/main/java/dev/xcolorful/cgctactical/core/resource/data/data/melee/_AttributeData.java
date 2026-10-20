package dev.xcolorful.cgctactical.core.resource.data.data.melee;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.xcolorful.customgun.core.resource.ResourcePojo;

import java.io.IOException;

public final class _AttributeData extends ResourcePojo<_AttributeData> {

//    private List<_AttributeInfo> attributes; // TODO

    private static final _AttributeData PARSER = new _AttributeData();
    public static _AttributeData fromJson(JsonReader reader) throws IOException {
        return PARSER.fromJsonReader(reader);
    }
    @Override
    protected _AttributeData fromJsonReader(JsonReader reader) throws IOException {
        _AttributeData pojo = new _AttributeData();

        reader.beginObject(); {
            while (reader.hasNext()) {
                reader.nextName();
                reader.skipValue();
            }
        }
        reader.endObject();
        return pojo;
    }

    public static void toJson(JsonWriter writer, _AttributeData pojo) throws IOException {
        if (pojo != null) pojo.toJson(writer);
    }
    @Override
    public void toJson(JsonWriter writer) throws IOException {
        writer.beginObject();
        writer.endObject();
    }

    @Override
    protected void validatePojo() {
        this.setValid(true);
    }

    // --------Getter & Setter--------

    // --------Back compatibility--------
}
