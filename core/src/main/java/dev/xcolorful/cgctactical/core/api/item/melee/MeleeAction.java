package dev.xcolorful.cgctactical.core.api.item.melee;

import dev.xcolorful.customgun.core.api.resource.ResourceTag;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public enum MeleeAction implements ResourceTag.CategoryTag {
    LEFT_ATTACK(MeleeActionTag.LEFT_ATTACK, MeleeActionTag.LEFT_ATTACK_OLD1),
    RIGHT_ATTACK(MeleeActionTag.RIGHT_ATTACK, MeleeActionTag.RIGHT_ATTACK_OLD1),
    ;

    public final String typeName;
    public final @Nullable String typeNameOld;
    MeleeAction(String name, @Nullable String nameOld) {
        this.typeName = name;
        this.typeNameOld = nameOld;
    }
    @Override public String getTagName() {
        return this.typeName;
    }
    @Override public String getCategoryName() {
        return this.typeName;
    }

    private static final Map<String, MeleeAction> MELEE_ACTIONS = new HashMap<>();

    static {
        for (MeleeAction type : values()) {
            MELEE_ACTIONS.put(type.typeName, type);
            if (type.typeNameOld != null) MELEE_ACTIONS.put(type.typeNameOld, type);
        }
    }

    public static @Nullable MeleeAction fromString(String name) {
        return name != null ? MELEE_ACTIONS.get(name) : null;
    }

    @Override
    public String toString() {
        return this.typeName;
    }
}
