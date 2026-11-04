package dev.xcolorful.cgctactical.core.api.item.tactical;

import dev.xcolorful.customgun.core.api.resource.ResourceTag;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public enum TacticalAction implements ResourceTag.CategoryTag {
    PRIMARY_ACTION(TacticalActionTag.PRIMARY_ACTION, TacticalActionTag.PRIMARY_ACTION_OLD1),
    SECONDARY_ACTION(TacticalActionTag.SECONDARY_ACTION, TacticalActionTag.SECONDARY_ACTION_OLD1),
    ;

    public final String typeName;
    public final @Nullable String typeNameOld;
    TacticalAction(String name, @Nullable String nameOld) {
        this.typeName = name;
        this.typeNameOld = nameOld;
    }
    @Override public String getTagName() {
        return this.typeName;
    }
    @Override public String getCategoryName() {
        return this.typeName;
    }

    private static final Map<String, TacticalAction> TACTICAL_ACTIONS = new HashMap<>();

    static {
        for (TacticalAction type : values()) {
            TACTICAL_ACTIONS.put(type.typeName, type);
            if (type.typeNameOld != null) TACTICAL_ACTIONS.put(type.typeNameOld, type);
        }
    }

    public static @Nullable TacticalAction fromString(String name) {
        return name != null ? TACTICAL_ACTIONS.get(name) : null;
    }

    @Override
    public String toString() {
        return this.typeName;
    }
}
