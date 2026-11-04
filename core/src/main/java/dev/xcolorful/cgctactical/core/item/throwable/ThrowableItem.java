package dev.xcolorful.cgctactical.core.item.throwable;

import dev.xcolorful.cgctactical.client.item.throwable._ThrowableItem;
import dev.xcolorful.cgctactical.core.api.item.IThrowable;
import dev.xcolorful.cgctactical.core.api.item.throwable.ThrowableDataAccessor;
import dev.xcolorful.cgctactical.core.api.minecraft.item.ItemType;
import dev.xcolorful.cgctactical.core.gui.tooltip.throwable.ThrowableTooltip;
import dev.xcolorful.cgctactical.core.init.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ThrowableItem extends Item implements IThrowable, ThrowableDataAccessor {

    protected ThrowableItem(Properties properties) {
        super(properties);
    }
    public ThrowableItem() {
        this(ModItems.CUSTOM_ITEM_PROPERTY.apply(ItemType.THROWABLE.getRegistryLocation()));
    }

    // --------Item--------

    /**
     * 获取供客户端使用的 Tooltip 信息
     */
    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack throwableItem) {
        return Optional.ofNullable(ThrowableTooltip.fromItem(throwableItem));
    }

    // --------Client--------

    @Override
    public @NotNull Component getName(@NotNull ItemStack throwableItem) {
        var name = _ThrowableItem.getName(this, throwableItem);
        return name != null ? name : super.getName(throwableItem);
    }
}
