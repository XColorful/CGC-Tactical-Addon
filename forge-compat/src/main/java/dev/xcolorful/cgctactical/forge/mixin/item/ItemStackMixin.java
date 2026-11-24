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
 *     <li>Forge 1.21.1 移除了 {@code getMaxDamage}/{@code isDamageable} 等 Item 侧耐久钩子，需要额外 Mixin 处理</li>
 *     <li>NeoForge 1.21.1 保留了这些钩子，但 {@code ItemStack#isDamageableItem} 仍按数据组件判断</li>
 *     <li>所以两个平台都需要该 Mixin 才能让数据驱动的物品耐久生效</li>
 * </ul>
 * 关于放在{@code cgctactical.core}还是{@code cgctactical.forge}/{@code cgctactical.neoforge}
 * <ul>
 *     <li>如果放{@code core}就相当于把从NBT/组件系统读unbreakable的方式给跨版本统一，需要系统性封装</li>
 *     <li>而放平台层则视为可逐版本一次性处理的差异</li>
 *     <li>毕竟这玩意估计就用这一两次，跨版本封装的成本划不来</li>
 *     <li>进而，没有向后构造版本差异(低版本分支不知道)，也就当作是平台侧处理好的事情</li>
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
