package dev.xcolorful.cgctactical.core.api.melee.physics;

import dev.xcolorful.cgctactical.core.api.melee.physics.volume.ConeVolume;
import dev.xcolorful.cgctactical.core.api.melee.physics.volume.ObbVolume;
import dev.xcolorful.cgctactical.core.api.melee.physics.volume.RayVolume;
import dev.xcolorful.customgun.core.api.resource.ResourceTag;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public enum HitVolumeType implements ResourceTag.CategoryTag {
    /**
     * 圆锥/扇形判定体积
     */
    CONE(HitVolumeTypeTag.CONE, ConeVolume.INSTANCE),
    /**
     * 有向包围盒判定体积
     */
    OBB(HitVolumeTypeTag.OBB, ObbVolume.INSTANCE),
    /**
     * 射线判定体积
     */
    RAY(HitVolumeTypeTag.RAY, RayVolume.INSTANCE);

    public final String typeName;
    public final IHitVolume hitVolume;
    HitVolumeType(String name, IHitVolume hitVolume) {
        this.typeName = name;
        this.hitVolume = hitVolume;
    }
    @Override public String getTagName() {
        return this.typeName;
    }
    @Override public String getCategoryName() {
        return this.typeName;
    }

    public IHitVolume getHitVolume() {
        return this.hitVolume;
    }

    private static final Map<String, HitVolumeType> VOLUME_TYPES = new HashMap<>();

    static {
        for (HitVolumeType type : HitVolumeType.values()) {
            VOLUME_TYPES.put(type.typeName, type);
        }
    }

    public static @Nullable HitVolumeType fromString(String name) {
        return name != null ? VOLUME_TYPES.get(name) : null;
    }

    @Override
    public String toString() {
        return this.typeName;
    }
}
