package dev.xcolorful.cgctactical.client.animation.statemachine;

import dev.xcolorful.cgctactical.client.api.script.context.IClientMeleeScriptApi;
import dev.xcolorful.cgctactical.core.api.item.IMelee;
import dev.xcolorful.cgctactical.core.api.item.melee.IMeleeGetter;
import dev.xcolorful.customgun.client.animation.statemachine.ItemAnimStateContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

public class MeleeAnimStateContext extends ItemAnimStateContext implements IClientMeleeScriptApi {

    private ItemStack currentMeleeItem = ItemStack.EMPTY;
    private @Nullable IMelee iMelee;
    private float walkDistAnchor = 0f;

    public MeleeAnimStateContext() {
    }

    // --------Setter--------

    /**
     * 状态机脚本请不要调用此方法。此方法用于状态机更新时设置当前的物品对象
     */
    @ApiStatus.Internal
    public void setCurrentMeleeItem(ItemStack meleeItem) {
        this.currentMeleeItem = meleeItem;
        this.iMelee = IMeleeGetter.fromItemStack(currentMeleeItem);

        if (this.iMelee != null) {
        }
    }

    // --------IMeleeScriptContextAccess--------

    @Override public @Nullable IMelee getIMelee() {
        return this.iMelee;
    }
    @Override public ItemStack getMeleeItem() {
        return this.currentMeleeItem;
    }

    // --------IClientMeleeScriptContextAccess--------

    @Override public @Nullable LocalPlayer getLocalCombatant() {
        return Minecraft.getInstance().player;
    }

    @Override public @Nullable Entity getCameraCombatant() {
        return Minecraft.getInstance().getCameraEntity();
    }

    @Deprecated(forRemoval = false)
    @Override public float getWalkDistAnchor() {
        return this.walkDistAnchor;
    }

    @Deprecated(forRemoval = false)
    @Override public void setWalkDistAnchor(float value) {
        this.walkDistAnchor = value;
    }

    // --------Deprecated--------

    @Deprecated public void setCurrentItem(ItemStack meleeItem) {
        setCurrentMeleeItem(meleeItem);
    }
}
