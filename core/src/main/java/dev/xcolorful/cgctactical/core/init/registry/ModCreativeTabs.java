package dev.xcolorful.cgctactical.core.init.registry;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.api.minecraft.tab.ConsumableTab;
import dev.xcolorful.cgctactical.core.api.minecraft.tab.MeleeTab;
import dev.xcolorful.cgctactical.core.api.minecraft.tab.ThrowableTab;
import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.customgun.core.api.init.registry.IRegistrar;
import dev.xcolorful.customgun.core.api.init.registry.IRegistryObject;
import net.minecraft.world.item.CreativeModeTab;

import static dev.xcolorful.cgctactical.core.api.item.tactical.TacticalCategory.*;

public class ModCreativeTabs {
    public static final IRegistrar<CreativeModeTab> TABS = CustomGun.getRegistrarFactory().createCreativeTabs(CgcTactical.MOD_ID);


    // --------Melee--------

    public static IRegistryObject<CreativeModeTab> MELEE_TAB = TABS.register(MELEE.getCategoryName(), () -> CreativeModeTab.builder()
            .title(MELEE.getCategoryLang())
            .icon(MeleeTab::buildMeleeTabItem)
            .displayItems((parameters, output) -> output.acceptAll(MeleeTab.buildMeleeItems()))
            .withSearchBar()
            .build()
    );

    // --------Throwable--------

    public static IRegistryObject<CreativeModeTab> THROWABLE_TAB = TABS.register(THROWABLE.getCategoryName(), () -> CreativeModeTab.builder()
            .title(THROWABLE.getCategoryLang()).withTabsBefore(MELEE_TAB.getRegistryName())
            .icon(ThrowableTab::buildThrowableTabItem)
            .displayItems((parameters, output) -> output.acceptAll(ThrowableTab.buildThrowableItems()))
            .withSearchBar()
            .build()
    );

    // --------Consumable--------

    public static IRegistryObject<CreativeModeTab> CONSUMABLE_TAB = TABS.register(CONSUMABLE.getCategoryName(), () -> CreativeModeTab.builder()
            .title(CONSUMABLE.getCategoryLang()).withTabsBefore(THROWABLE_TAB.getRegistryName())
            .icon(ConsumableTab::buildConsumableTabItem)
            .displayItems((parameters, output) -> output.acceptAll(ConsumableTab.buildConsumableItems()))
            .withSearchBar()
            .build()
    );
}
