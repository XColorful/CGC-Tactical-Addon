package dev.xcolorful.cgctactical.client.init;

import dev.xcolorful.cgctactical.client.gui.tooltip.consumable.ClientConsumableTooltip;
import dev.xcolorful.cgctactical.client.gui.tooltip.melee.ClientMeleeTooltip;
import dev.xcolorful.cgctactical.client.gui.tooltip.throwable.ClientThrowableTooltip;
import dev.xcolorful.cgctactical.core.gui.tooltip.consumable.ConsumableTooltip;
import dev.xcolorful.cgctactical.core.gui.tooltip.melee.MeleeTooltip;
import dev.xcolorful.cgctactical.core.gui.tooltip.throwable.ThrowableTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

import java.util.function.Function;

public class ClientTooltipRegistry {

    private static ClientTooltipRegistry INSTANCE = new ClientTooltipRegistry();
    public static ClientTooltipRegistry get() {
        return INSTANCE;
    }
    private ClientTooltipRegistry() {}

    @FunctionalInterface
    public interface TooltipRegistrar {
        <T extends TooltipComponent> void register(
                Class<T> clazz,
                Function<T, ClientTooltipComponent> factory
        );
    }
    public void registerTooltips(TooltipRegistrar registrar) {
        registrar.register(MeleeTooltip.class, ClientMeleeTooltip::new);
        registrar.register(ThrowableTooltip.class, ClientThrowableTooltip::new);
        registrar.register(ConsumableTooltip.class, ClientConsumableTooltip::new);
    }
}
