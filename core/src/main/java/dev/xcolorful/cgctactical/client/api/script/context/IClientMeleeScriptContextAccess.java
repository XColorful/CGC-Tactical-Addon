package dev.xcolorful.cgctactical.client.api.script.context;

import dev.xcolorful.cgctactical.core.api.item.IMelee;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface IClientMeleeScriptContextAccess {

    @Nullable LocalPlayer getLocalCombatant();

    @Nullable Entity getCameraCombatant();

    @Nullable IMelee getIMelee();
    ItemStack getMeleeItem();

    float getWalkDistAnchor();
    float getPartialTicks();

    void setWalkDistAnchor(float value);
}
