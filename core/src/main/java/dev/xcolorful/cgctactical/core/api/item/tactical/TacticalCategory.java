package dev.xcolorful.cgctactical.core.api.item.tactical;

import dev.xcolorful.customgun.core.api.resource.ResourceTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum TacticalCategory implements ResourceTag.CategoryTag {
    /**
     * 近战
     */
    MELEE(TacticalCategoryTag.MELEE,
            Component.translatable("cgctactical.tacticalcategory.melee")),
    /**
     * 投掷物
     */
    THROWABLE(TacticalCategoryTag.THROWABLE,
            Component.translatable("cgctactical.tacticalcategory.throwable")),
    /**
     * 消耗品
     */
    CONSUMABLE(TacticalCategoryTag.CONSUMABLE,
            Component.translatable("cgctactical.tacticalcategory.consumable"));

    public final String categoryName;
    public final MutableComponent categoryLang;
    TacticalCategory(String name, MutableComponent lang) {
        this.categoryName = name;
        this.categoryLang = lang;
    }

    @Override public String getTagName() {
        return this.categoryName;
    }
    @Override public String getCategoryName() {
        return this.categoryName;
    }

    /**
     * 调用方手动copy，不然会缓存旧{@link net.minecraft.locale.Language}
     */
    public final MutableComponent getCategoryLang() {
        return this.categoryLang;
    }

    private static final Map<String, TacticalCategory> TACTICAL_CATEGORIES = new HashMap<>();

    static {
        for (TacticalCategory type : values()) {
            TACTICAL_CATEGORIES.put(type.categoryName, type);
            TACTICAL_CATEGORIES.put(type.categoryName.toUpperCase(Locale.ENGLISH), type);
        }
    }

    public static @Nullable TacticalCategory fromString(String name) {
        return name != null ? TACTICAL_CATEGORIES.get(name) : null;
    }

    @Override
    public String toString() {
        return this.categoryName;
    }
}
