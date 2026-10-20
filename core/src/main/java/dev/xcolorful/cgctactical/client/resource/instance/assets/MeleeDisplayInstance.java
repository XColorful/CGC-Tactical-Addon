package dev.xcolorful.cgctactical.client.resource.instance.assets;

import dev.xcolorful.cgctactical.client.resource.assets.display.MeleeDisplay;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MeleeDisplayInstance extends _AssetsDisplayInstance<MeleeDisplay> {

    private MeleeDisplayInstance(@NotNull MeleeDisplay pojo) {
        super(pojo);
    }

    public static @Nullable MeleeDisplayInstance fromPojo(MeleeDisplay pojo) {
        if (pojo == null) return null;
        MeleeDisplayInstance instance = new MeleeDisplayInstance(pojo);
        if (!instance.isPojoValid()) return null;
        else return instance;
    }
}
