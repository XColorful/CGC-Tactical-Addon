package dev.xcolorful.cgctactical.client.animation.statemachine;

import dev.xcolorful.cgctactical.core.api.item.IThrowable;
import dev.xcolorful.cgctactical.core.api.item.throwable.IThrowableGetter;
import dev.xcolorful.customgun.client.animation.statemachine.ItemAnimStateContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ThrowableAnimStateContext extends ItemAnimStateContext {
    
    private ItemStack currentThrowableItem = ItemStack.EMPTY;
    private @Nullable IThrowable iThrowable;
    private boolean using = false;
    private int usingTicks = 0;
    private int prepareTime = 0;
    
    public ThrowableAnimStateContext() {
    }

    // --------Deprecated--------

    public void setCurrentItem(ItemStack throwableItem) {
        this.currentThrowableItem = throwableItem;
        this.iThrowable = IThrowableGetter.fromItemStack(throwableItem);
        this.prepareTime = this.iThrowable != null ? this.iThrowable.getUseDurationLimit(throwableItem) : 0;
    }
    public ItemStack getCurrentItem() {
        return this.currentThrowableItem;
    }
    public int getStackCount() {
        return this.currentThrowableItem.getCount();
    }
    public boolean isUsing() {
        return this.using;
    }
    public void setUsing(boolean using) {
        this.using = using;
    }
    public int getUsingTick() {
        return this.usingTicks;
    }
    public void setUsingTick(int usingTicks) {
        this.usingTicks = usingTicks;
    }
    public int getPrepareTime() {
        return this.prepareTime;
    }
}
