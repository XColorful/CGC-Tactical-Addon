package dev.xcolorful.cgctactical.client.resource.instance.assets;

import dev.xcolorful.cgctactical.client.resource.assets.display.ConsumableDisplay;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ConsumableDisplayInstance extends _AssetsDisplayInstance<ConsumableDisplay> {

    private ConsumableDisplayInstance(@NotNull ConsumableDisplay pojo) {
        super(pojo);
    }

    public static @Nullable ConsumableDisplayInstance fromPojo(ConsumableDisplay pojo) {
        if (pojo == null) return null;
        ConsumableDisplayInstance instance = new ConsumableDisplayInstance(pojo);
        if (!instance.isPojoValid()) return null;
        else return instance;
    }
}
