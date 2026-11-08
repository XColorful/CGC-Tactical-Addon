package dev.xcolorful.cgctactical.core.api.resource.assets.model.bedrock.geometry;

import dev.xcolorful.customgun.core.api.resource.ResourceTag;
import dev.xcolorful.customgun.core.api.resource.assets.model.bedrock.geometry.INodeNameMatcher;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public enum NodeName implements ResourceTag.ConstantTag, INodeNameMatcher {
    // ----Tactical Model----
    /**
     * 第一人称渲染时显示，如挥砍轨迹
     */
    FIRST_PERSON_EFFECT(_BoneTag.Node.FIRST_PERSON_EFFECT),
    /**
     * 实体渲染时隐藏
     */
    ENTITY_HIDE(_BoneTag.Node.ENTITY_HIDE);

    public final String typeName;
    NodeName(final String typeName) {
        this.typeName = typeName;
    }
    @Override public String getTagName() {
        return this.typeName;
    }
    @Override public String getConstantName() {
        return this.typeName;
    }

    @Override
    public String getName() {
        return this.typeName;
    }
    @Override
    public boolean matches(@Nullable String name) {
        return name != null && name.equals(this.typeName);
    }
    @Override
    public @Nullable String getStrippedIfMatches(@Nullable String name) {
        return this.matches(name) ? "" : null;
    }

    private static final Map<String, NodeName> NODE_NAMES = new HashMap<>();

    static {
        for (NodeName name : NodeName.values()) {
            NODE_NAMES.put(name.typeName, name);
        }
    }

    public static @Nullable NodeName fromString(String name) {
        return name != null ? NODE_NAMES.get(name) : null;
    }

    @Override
    public String toString() {
        return this.typeName;
    }

    public enum Prefix implements ResourceTag.ConstantTag, INodeNameMatcher {
        FIRST_PERSON_EFFECT(_BoneTag.Node.FIRST_PERSON_EFFECT),
        ENTITY_HIDE(_BoneTag.Node.ENTITY_HIDE);

        public final String typeName;
        public final String prefix;
        Prefix(final String typeName) {
            this.typeName = typeName;
            this.prefix = typeName + "_";
        }
        @Override public String getTagName() {
            return this.typeName;
        }
        @Override public String getConstantName() {
            return this.typeName;
        }

        @Override
        public String getName() {
            return this.prefix;
        }
        @Override
        public boolean matches(@Nullable String name) {
            return name != null && name.startsWith(this.prefix);
        }
        @Override
        public @Nullable String getStrippedIfMatches(@Nullable String name) {
            if (name == null || !name.startsWith(this.prefix)) {
                return null;
            }
            return name.substring(this.prefix.length());
        }

        private static final Map<String, Prefix> PREFIX_NAMES = new HashMap<>();

        static {
            for (Prefix name : Prefix.values()) {
                PREFIX_NAMES.put(name.typeName, name);
            }
        }

        public static @Nullable Prefix fromString(String name) {
            return name != null ? PREFIX_NAMES.get(name) : null;
        }

        @Override
        public String toString() {
            return this.typeName;
        }
    }
}
