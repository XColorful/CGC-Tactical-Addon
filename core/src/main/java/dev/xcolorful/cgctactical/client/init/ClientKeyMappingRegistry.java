package dev.xcolorful.cgctactical.client.init;

import dev.xcolorful.cgctactical.client.init.registry.ClientInputCategory;
import dev.xcolorful.cgctactical.client.input.combatant.PrimaryActionKey;
import dev.xcolorful.cgctactical.client.input.combatant.SecondaryActionKey;
import net.minecraft.client.KeyMapping;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Consumer;

public class ClientKeyMappingRegistry {

    private static final ClientKeyMappingRegistry INSTANCE = new ClientKeyMappingRegistry();
    public static ClientKeyMappingRegistry get() {
        return INSTANCE;
    }
    private ClientKeyMappingRegistry() {}

    public void registerKeyMappings(Consumer<KeyMapping> register) {
        // combatant
        register.accept(PrimaryActionKey.get().getKeyMapping().get());
        register.accept(SecondaryActionKey.get().getKeyMapping().get());
    }
    @ApiStatus.AvailableSince("1.21.10")
    public void registerInputCategories(Consumer<String> register) { // Consumer<KeyMapping.Category> register
        register.accept(ClientInputCategory.COMBATANT);
    }
}
