package dev.xcolorful.cgctactical.neoforgeclient.mixin.item;

import dev.xcolorful.cgctactical.client.renderer.item.ThrowableItemRenderer;
import dev.xcolorful.cgctactical.core.item.throwable.ThrowableItem;
import dev.xcolorful.customgun.client.api.item.IAnimateGeoItem;
import dev.xcolorful.customgun.client.api.item.IItemBEWLR;
import dev.xcolorful.customgun.client.compat.minecraft.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Consumer;

@Mixin(ThrowableItem.class)
public abstract class ThrowableItemMixin extends Item implements IAnimateGeoItem, IItemBEWLR {

    public ThrowableItemRenderer cgctactical$renderer;

    public ThrowableItemMixin(Properties properties) {
        super(properties);
    }

    // --------IClientItemExtensions-------

    @OnlyIn(Dist.CLIENT)
//    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Deprecated(since = "1.21.4")
//            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return ThrowableItemMixin.this.cgc$getBEWLR();
            }
        });
    }

    // --------IAnimateGeoItem--------

    @Override
    public ThrowableItemRenderer cgc$getCustomRenderer() {
        if (this.cgctactical$renderer == null) this.cgctactical$renderer = new ThrowableItemRenderer();

        return this.cgctactical$renderer;
    }

    // --------IItemBEWLR--------

    @Override
    public ThrowableItemRenderer cgc$getBEWLR() {
        return this.cgc$getCustomRenderer();
    }
}
