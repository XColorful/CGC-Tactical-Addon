package dev.xcolorful.cgctactical.core.api.resource.assets.display;

import dev.xcolorful.customgun.core.api.resource.ResourceTag;
import dev.xcolorful.customgun.core.api.resource.assets.model.bedrock.geometry.NodeName;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * 设计同 {@link NodeName}
 */
public enum SoundName implements ResourceTag.ConstantTag, ISoundNameMatcher {
    // 排列组合直接让AI一键输出，省得加Suffix变复杂
    // ----Tactical action----
    PRIMARY_ACTION(SoundNameTag.PRIMARY_ACTION, SoundNameTag.PRIMARY_ACTION_OLD1),
    PRIMARY_ACTION_HIT(SoundNameTag.PRIMARY_ACTION + "_" + SoundNameTag.Suffix.HIT,
            SoundNameTag.PRIMARY_ACTION_OLD1 + "_" + SoundNameTag.Suffix.HIT),
    PRIMARY_ACTION_FEEDBACK(SoundNameTag.PRIMARY_ACTION + "_" + SoundNameTag.Suffix.FEEDBACK,
            SoundNameTag.PRIMARY_ACTION_OLD1 + "_" + SoundNameTag.Suffix.FEEDBACK),
    SECONDARY_ACTION(SoundNameTag.SECONDARY_ACTION, SoundNameTag.SECONDARY_ACTION_OLD1),
    SECONDARY_ACTION_HIT(SoundNameTag.SECONDARY_ACTION + "_" + SoundNameTag.Suffix.HIT,
            SoundNameTag.SECONDARY_ACTION_OLD1 + "_" + SoundNameTag.Suffix.HIT),
    SECONDARY_ACTION_FEEDBACK(SoundNameTag.SECONDARY_ACTION + "_" + SoundNameTag.Suffix.FEEDBACK,
            SoundNameTag.SECONDARY_ACTION_OLD1 + "_" + SoundNameTag.Suffix.FEEDBACK),
    // ----IMeleeAttackRuntime----
    KILL(SoundNameTag.KILL),
    KILL_FEEDBACK(SoundNameTag.KILL + "_" + SoundNameTag.Suffix.FEEDBACK),
    CRIT(SoundNameTag.CRIT),
    CRIT_FEEDBACK(SoundNameTag.CRIT + "_" + SoundNameTag.Suffix.FEEDBACK),
    ;

    public final String typeName;
    public final String typeNameOld;
    SoundName(String typeName) {
        this(typeName, null);
    }
    SoundName(String typeName, String typeNameOld) {
        this.typeName = typeName;
        this.typeNameOld = typeNameOld;
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

    private static final Map<String, SoundName> SOUND_NAMES = new HashMap<>();
    /**
     * 旧名称 -> 新名称
     */
    private static final Map<String, String> SOUND_NAMES_OLD = new HashMap<>();

    static {
        for (SoundName soundName : SoundName.values()) {
            SOUND_NAMES.put(soundName.typeName, soundName);
            if (soundName.typeNameOld != null) {
                SOUND_NAMES.put(soundName.typeNameOld, soundName);
                SOUND_NAMES_OLD.put(soundName.typeNameOld, soundName.typeName);
            }
        }
    }

    public static @Nullable SoundName fromString(String name) {
        return name != null ? SOUND_NAMES.get(name) : null;
    }

    @Override
    public String toString() {
        return this.typeName;
    }

    public static @Nullable String fromOldName(String oldName) {
        return SOUND_NAMES_OLD.get(oldName);
    }
}
