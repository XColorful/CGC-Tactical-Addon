package dev.xcolorful.cgctactical.core.api.item.throwable;

import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.resource.data.index.ThrowableIndex;
import dev.xcolorful.cgctactical.core.resource.instance.data.ThrowableIndexInstance;
import dev.xcolorful.customgun.core.api.resource.ResourceTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IThrowableDataAccess extends IThrowableStateAccess,
        _IThrowablePropertyAccess {

    /**
     * 获取近战资源位置，如不存在则返回 {@link ResourceTag#NULL_LOCATION}
     */
    @NotNull ResourceLocation getThrowableLocation(ItemStack throwableItem);
    void setThrowableLocation(ItemStack throwableItem, ResourceLocation throwableLocation);
    /**
     * 获取NBT指定的ThrowableDisplay，其次查找{@link ThrowableIndexInstance}，如无则返回 {@link ResourceTag#NULL_LOCATION}
     */
    @NotNull ResourceLocation getThrowableDisplayLocation(ItemStack throwableItem);
    void setThrowableDisplayLocation(ItemStack throwableItem, ResourceLocation throwableDisplayLocation);

    // --------Deprecated--------

    @Deprecated(forRemoval = true) default @Nullable ThrowableIndex getThrowableIndex(ItemStack throwableItem) {
        var throwableLocation = this.getThrowableLocation(throwableItem);
        @Nullable ThrowableIndexInstance throwableIndexInstance = ResourceApi.getThrowableIndexInstance(throwableLocation);
        if (throwableIndexInstance == null) return null;

        return throwableIndexInstance.getPojo();
    }
}
