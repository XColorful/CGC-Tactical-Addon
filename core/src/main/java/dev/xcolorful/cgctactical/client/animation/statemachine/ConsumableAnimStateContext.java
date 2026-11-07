package dev.xcolorful.cgctactical.client.animation.statemachine;

import dev.xcolorful.cgctactical.core.api.item.IConsumable;
import dev.xcolorful.customgun.client.animation.statemachine.ItemAnimStateContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ConsumableAnimStateContext extends ItemAnimStateContext {

    private ItemStack currentConsumableItem = ItemStack.EMPTY;
    private @Nullable IConsumable iConsumable;
    private boolean using = false;
    private int usingTicks = 0;

    public ConsumableAnimStateContext() {
    }

    // --------Deprecated--------

    public void setCurrentItem(ItemStack consumableItem) {
        this.currentConsumableItem = consumableItem;
    }
    public ItemStack getCurrentItem() {
        return this.currentConsumableItem;
    }
    public int getStackCount() {
        return this.currentConsumableItem.getCount();
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
}
