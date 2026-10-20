package dev.xcolorful.cgctactical.core.api.event.combatant;

import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.customgun.core.api.common.ILogicalSideOnly;
import dev.xcolorful.customgun.core.api.common.McLogicalSide;
import dev.xcolorful.customgun.core.api.event.CustomEvent;
import dev.xcolorful.customgun.core.api.event.CustomEventType;
import dev.xcolorful.customgun.core.api.event.ICustomEvent;
import dev.xcolorful.customgun.core.api.event.ICustomEventHandler;
import dev.xcolorful.customgun.core.event.EventDispatcher;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// TODO
/**
 * 跟GunFireEvent类似，可能会做成客户端先client side (shoot)，服务端检查一份shoot+gun fire，回给客户端执行一份client gun fire (本事件)
 * 两个事件两端各触发一次 (大概
 * 目前为占位
 */
public class MeleeAttackEvent extends CustomEvent implements ILogicalSideOnly {

    private final McLogicalSide logicalSide;

    public MeleeAttackEvent(McLogicalSide logicalSide) {
        this.logicalSide = logicalSide;
    }

    @Override
    public McLogicalSide getLogicalSide() {
        return this.logicalSide;
    }

    @Override
    public @Nullable CommandSourceStack createCommandSourceStack(@Nullable CommandSource commandSource) {
        return null;
    }

    @Override public String getTextName() {
        return "MeleeAttackEvent";
    }
    @Override public Component getDisplayName() {
        return Component.literal(getTextName());
    }

    private static final EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> _EVENT_DISPATCHER = CustomGun.getEventPoster().getEventDispatcher(MeleeAttackEvent.class);
    @Override public @NotNull EventDispatcher<ICustomEventHandler, ICustomEvent, CustomEventType> getEventDispatcher() {
        return _EVENT_DISPATCHER;
    }
}
