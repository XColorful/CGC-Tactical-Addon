package dev.xcolorful.cgctactical.core.api.item.consumable;

import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.resource.data.index.ConsumableIndex;
import dev.xcolorful.cgctactical.core.resource.instance.data.ConsumableIndexInstance;
import dev.xcolorful.customgun.core.api.resource.ResourceTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IConsumableDataAccess {

    /**
     * 获取近战资源位置，如不存在则返回 {@link ResourceTag#NULL_LOCATION}
     */
    @NotNull Identifier getConsumableLocation(ItemStack consumableItem);
    void setConsumableLocation(ItemStack consumableItem, Identifier consumableLocation);
    /**
     * 获取NBT指定的ConsumableDisplay，其次查找{@link ConsumableIndexInstance}，如无则返回 {@link ResourceTag#NULL_LOCATION}
     */
    @NotNull Identifier getConsumableDisplayLocation(ItemStack consumableItem);
    void setConsumableDisplayLocation(ItemStack consumableItem, Identifier consumableDisplayLocation);

    // --------Deprecated--------

    @Deprecated(forRemoval = true) default @Nullable ConsumableIndex getConsumableIndex(ItemStack consumableItem) {
        var consumableLocation = this.getConsumableLocation(consumableItem);
        @Nullable ConsumableIndexInstance consumableIndexInstance = ResourceApi.getConsumableIndexInstance(consumableLocation);
        if (consumableIndexInstance == null) return null;

        return consumableIndexInstance.getPojo();
    }
}
