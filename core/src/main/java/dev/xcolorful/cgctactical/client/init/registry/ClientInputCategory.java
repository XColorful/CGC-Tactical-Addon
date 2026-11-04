package dev.xcolorful.cgctactical.client.init.registry;

import dev.xcolorful.cgctactical.client.api.minecraft.input.CustomInputCategory;
import net.minecraft.client.KeyMapping;

public class ClientInputCategory {
    public static final KeyMapping.Category COMBATANT = new KeyMapping.Category(CustomInputCategory.COMBATANT.getRegistryLocation());
}
