package dev.xcolorful.cgctactical.core.item.throwable;

import dev.xcolorful.cgctactical.core.api.item.IThrowable;
import dev.xcolorful.cgctactical.core.api.item.throwable.ThrowableDataAccessor;
import dev.xcolorful.cgctactical.core.api.minecraft.item.ItemType;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.gui.tooltip.throwable.ThrowableTooltip;
import dev.xcolorful.cgctactical.core.init.registry.ModItems;
import dev.xcolorful.cgctactical.core.resource.data.index.ThrowableIndex;
import dev.xcolorful.cgctactical.core.resource.instance.data.ThrowableIndexInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ThrowableItem extends Item implements IThrowable, ThrowableDataAccessor {

    protected ThrowableItem(Properties properties) {
        super(properties);
    }
    public ThrowableItem() {
        this(ModItems.CUSTOM_ITEM_PROPERTY.apply(ItemType.THROWABLE.getRegistryLocation()));
    }

    // --------Item--------

    @Override
    public @NotNull Component getName(@NotNull ItemStack throwableItem) {
        var throwableLocation = this.getThrowableLocation(throwableItem);
        @Nullable ThrowableIndexInstance throwableIndexInstance = ResourceApi.getThrowableIndexInstance(throwableLocation);
        if (throwableIndexInstance == null) return super.getName(throwableItem);

        ThrowableIndex throwableIndex = throwableIndexInstance.getPojo();
        return Component.translatable(throwableIndex.getNameLang());
    }

    /**
     * 获取供客户端使用的 Tooltip 信息
     */
    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack throwableItem) {
        return Optional.ofNullable(ThrowableTooltip.fromItem(throwableItem));
    }
}
