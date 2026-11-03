package dev.xcolorful.cgctactical.core.api.item.builder;

import dev.xcolorful.cgctactical.core.api.item.IMelee;
import dev.xcolorful.cgctactical.core.api.item.melee.IMeleeGetter;
import dev.xcolorful.cgctactical.core.api.item.melee.MeleeProperty;
import dev.xcolorful.customgun.core.api.item.builder.ItemBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

public class MeleeBuilder extends ItemBuilder<MeleeBuilder> {

    private final IMelee iMelee;

    private MeleeBuilder(IMelee iMelee, ItemStack meleeItem) {
        super(meleeItem);
        this.iMelee = iMelee;
    }
    public static MeleeBuilder create(ItemLike melee) {
        ItemStack meleeItem = new ItemStack(melee);
        @Nullable IMelee iMelee = IMeleeGetter.fromItemStack(meleeItem);
        if (iMelee != null) return new MeleeBuilder(iMelee, meleeItem);
        else throw new IllegalArgumentException("Item is not a IMelee");
    }

    public <T> MeleeBuilder setProperty(MeleeProperty property, Class<T> type, T value) {
        property.set(this.iMelee, this.itemStack, value);
        return this;
    }
}
