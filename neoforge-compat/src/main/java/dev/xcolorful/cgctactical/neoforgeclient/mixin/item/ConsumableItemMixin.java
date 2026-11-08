package dev.xcolorful.cgctactical.neoforgeclient.mixin.item;

import dev.xcolorful.cgctactical.client.renderer.item.ConsumableItemRenderer;
import dev.xcolorful.cgctactical.core.item.consumable.ConsumableItem;
import dev.xcolorful.customgun.client.api.item.IAnimateGeoItem;
import dev.xcolorful.customgun.client.api.item.IItemBEWLR;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Consumer;

@Mixin(ConsumableItem.class)
public abstract class ConsumableItemMixin extends Item implements IAnimateGeoItem, IItemBEWLR {

    public ConsumableItemRenderer cgctactical$renderer;

    public ConsumableItemMixin(Properties properties) {
        super(properties);
    }

    // --------IClientItemExtensions-------

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Deprecated(since = "1.21.4")
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return ConsumableItemMixin.this.cgc$getBEWLR();
            }
        });
    }

    // --------IAnimateGeoItem--------

    @Override
    public ConsumableItemRenderer cgc$getCustomRenderer() {
        if (this.cgctactical$renderer == null) this.cgctactical$renderer = new ConsumableItemRenderer();

        return this.cgctactical$renderer;
    }

    // --------IItemBEWLR--------

    @Override
    public ConsumableItemRenderer cgc$getBEWLR() {
        return this.cgc$getCustomRenderer();
    }
}
