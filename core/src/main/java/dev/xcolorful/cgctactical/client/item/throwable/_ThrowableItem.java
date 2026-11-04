package dev.xcolorful.cgctactical.client.item.throwable;

import dev.xcolorful.cgctactical.core.api.item.IThrowable;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.resource.data.index.ThrowableIndex;
import dev.xcolorful.cgctactical.core.resource.instance.data.ThrowableIndexInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class _ThrowableItem {

    public static @Nullable Component getName(IThrowable iThrowable, @NotNull ItemStack throwableItem) {
        var throwableLocation = iThrowable.getThrowableLocation(throwableItem);
        @Nullable ThrowableIndexInstance throwableIndexInstance = ResourceApi.getThrowableIndexInstance(throwableLocation);
        if (throwableIndexInstance == null) return null;

        ThrowableIndex throwableIndex = throwableIndexInstance.getPojo();
        return Component.translatable(throwableIndex.getNameLang());
    }
}
