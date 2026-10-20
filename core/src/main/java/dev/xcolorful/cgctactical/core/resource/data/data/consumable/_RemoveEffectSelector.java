package dev.xcolorful.cgctactical.core.resource.data.data.consumable;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

@Deprecated(forRemoval = true)
public final class _RemoveEffectSelector {

    private static final String BENEFICIAL = "@beneficial"; // TODO
    private static final String HARMFUL = "@harmful"; // TODO
    private static final String NEUTRAL = "@neutral"; // TODO

    private final @Nullable ResourceLocation effectLocation;
//    private final @Nullable MobEffectCategory category; // TODO

    public _RemoveEffectSelector(@Nullable ResourceLocation effectLocation) {
        this.effectLocation = effectLocation;
    }

    // --------Getter--------

    public @Nullable ResourceLocation getEffectLocation() {
        return effectLocation;
    }
}
