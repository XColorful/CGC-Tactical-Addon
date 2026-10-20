package dev.xcolorful.cgctactical.client.resource.instance.assets;

import dev.xcolorful.cgctactical.client.resource.assets.display.ThrowableDisplay;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ThrowableDisplayInstance extends _AssetsDisplayInstance<ThrowableDisplay> {

    private ThrowableDisplayInstance(@NotNull ThrowableDisplay pojo) {
        super(pojo);
    }

    public static @Nullable ThrowableDisplayInstance fromPojo(ThrowableDisplay pojo) {
        if (pojo == null) return null;
        ThrowableDisplayInstance instance = new ThrowableDisplayInstance(pojo);
        if (!instance.isPojoValid()) return null;
        else return instance;
    }
}
