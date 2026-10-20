package dev.xcolorful.cgctactical.client.network.message.combatant;

import dev.xcolorful.cgctactical.core.network.message.combatant.S2CMessageCombatantShield_block;
import dev.xcolorful.customgun.client.api.item.IAnimateGeoItem;
import dev.xcolorful.customgun.client.api.renderer.item.IAnimateGeoItemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public class _S2CMessageCombatantShield_block {

    public static void handle(S2CMessageCombatantShield_block message) {
        Minecraft mc = Minecraft.getInstance();
        @Nullable LocalPlayer localPlayer = mc.player;
        @Nullable ClientLevel level = mc.level;
        if (localPlayer == null || level == null) return;

        // TODO

        ItemStack itemStack = localPlayer.getMainHandItem();
        @Nullable IAnimateGeoItemRenderer<?, ?> renderer = IAnimateGeoItem.cgc$getCustomRenderer(itemStack);
        if (renderer == null) return;

        renderer.triggerAnimation(itemStack, ""); // TODO
    }
}
