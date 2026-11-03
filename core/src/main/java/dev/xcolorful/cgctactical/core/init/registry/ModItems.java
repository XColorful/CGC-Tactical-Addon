package dev.xcolorful.cgctactical.core.init.registry;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.api.minecraft.item.ItemType;
import dev.xcolorful.cgctactical.core.item.consumable.ConsumableItem;
import dev.xcolorful.cgctactical.core.item.melee.MeleeItem;
import dev.xcolorful.cgctactical.core.item.throwable.ThrowableItem;
import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.customgun.core.api.init.registry.IRegistrar;
import dev.xcolorful.customgun.core.api.init.registry.IRegistryObject;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class ModItems {
    public static final IRegistrar<Item> ITEMS = CustomGun.getRegistrarFactory().createItems(CgcTactical.MOD_ID);

    public static final Function<Identifier, Item.Properties> CUSTOM_ITEM_PROPERTY = (registryLocation) -> new Item.Properties()
            .stacksTo(1); // 堆叠数会重载getter来修改


    // 暂时不需要hack注册，因此还是用register而不是registerItem
    public static final IRegistryObject<MeleeItem> MELEE = ITEMS.register(ItemType.MELEE.getTagName(), MeleeItem::new);
    public static final IRegistryObject<ThrowableItem> THROWABLE = ITEMS.register(ItemType.THROWABLE.getTagName(), ThrowableItem::new);
    public static final IRegistryObject<ConsumableItem> CONSUMABLE = ITEMS.register(ItemType.CONSUMABLE.getTagName(), ConsumableItem::new);
}
