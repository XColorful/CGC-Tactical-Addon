package dev.xcolorful.cgctactical.forge.mixin.item;

import dev.xcolorful.cgctactical.core.item.melee.MeleeItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * <ul>
 *     <li>Forge 1.21.1 删了很久旧接口导致很多地方都要额外 Mixin 处理</li>
 *     <li>NeoForge 依然保留了 {@code IItemExtensim#getMaxDamage}，还是 Forge 的味道</li>
 *     <li>所以只对 Forge 1.21.1+ 这个多灾多难的区间进行专门兼容</li>
 * </ul>
 */
@ApiStatus.AvailableSince("1.21.1forge")
@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(method = "getMaxDamage", at = @At("HEAD"), cancellable = true)
    private void cgctactical$getMaxDamage(CallbackInfoReturnable<Integer> cir) {
        ItemStack _this = (ItemStack) (Object) this;
        if (!(_this.getItem() instanceof MeleeItem meleeItem)) return;

        cir.setReturnValue(meleeItem.getMaxDurability(_this));
    }

    @ApiStatus.AvailableSince("1.21.1forge")
    @Inject(method = "isDamageableItem", at = @At("HEAD"), cancellable = true)
    private void cgctactical$isDamageableItem(CallbackInfoReturnable<Boolean> cir) {
        ItemStack _this = (ItemStack) (Object) this;
        if (!(_this.getItem() instanceof MeleeItem meleeItem)) return;

        if (!_this.has(DataComponents.UNBREAKABLE) && meleeItem.getMaxDurability(_this) > 0) {
            cir.setReturnValue(true);
        }
    }
}
