package dev.xcolorful.cgctactical.neoforgeclient.mixin.item;

import dev.xcolorful.cgctactical.client.renderer.item.MeleeItemRenderer;
import dev.xcolorful.cgctactical.core.item.melee.MeleeItem;
import dev.xcolorful.customgun.client.api.item.IAnimateGeoItem;
import dev.xcolorful.customgun.client.api.item.IItemBEWLR;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Consumer;

@Mixin(MeleeItem.class)
public abstract class MeleeItemMixin extends Item implements IAnimateGeoItem, IItemBEWLR {

    public MeleeItemRenderer cgctactical$renderer;

    public MeleeItemMixin(Properties properties) {
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
                return MeleeItemMixin.this.cgc$getBEWLR();
            }
        });
    }

    // --------IAnimateGeoItem--------

    @Override
    public MeleeItemRenderer cgc$getCustomRenderer() {
        if (this.cgctactical$renderer == null) this.cgctactical$renderer = new MeleeItemRenderer();

        return this.cgctactical$renderer;
    }

    // --------IItemBEWLR--------

    @Override
    public MeleeItemRenderer cgc$getBEWLR() {
        return this.cgc$getCustomRenderer();
    }
}
