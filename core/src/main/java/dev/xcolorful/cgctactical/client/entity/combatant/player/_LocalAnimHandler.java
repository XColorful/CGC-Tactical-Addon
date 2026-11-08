package dev.xcolorful.cgctactical.client.entity.combatant.player;

import dev.xcolorful.cgctactical.client.animation.statemachine.MeleeAnimStateContext;
import dev.xcolorful.cgctactical.client.api.resource.ClientResourceApi;
import dev.xcolorful.cgctactical.client.resource.instance.assets.MeleeDisplayInstance;
import dev.xcolorful.cgctactical.core.api.item.IMelee;
import dev.xcolorful.cgctactical.core.api.item.melee.IMeleeGetter;
import dev.xcolorful.customgun.client.animation.statemachine.LuaAnimStateMachine;
import dev.xcolorful.customgun.client.api.event.IClientTickEvent;
import dev.xcolorful.customgun.core.api.event.EventType;
import dev.xcolorful.customgun.core.api.event.IEvent;
import dev.xcolorful.customgun.core.api.event.IEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class _LocalAnimHandler implements IEventHandler {
    private static class _LocalAnimHandlerHolder {
        private static final _LocalAnimHandler INSTANCE = new _LocalAnimHandler();
    }
    public static _LocalAnimHandler get() {
        return _LocalAnimHandlerHolder.INSTANCE;
    }
    protected _LocalAnimHandler() {}
    @Override public String getEventHandlerName() {
        return this.getClass().getName();
    }
    @Override
    public void handleEvent(EventType eventType, IEvent event) {
        if (eventType == EventType.CLIENT_TICK_EVENT) {
            onClientTick((IClientTickEvent) event);
        } else {
            onReceiveWrongEvent(eventType);
        }
    }

    private void onClientTick(IClientTickEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        ItemStack meleeItem = player.getMainHandItem();
        @Nullable IMelee iMelee = IMeleeGetter.fromItemStack(meleeItem);
        if (iMelee == null) return;

        @Nullable MeleeDisplayInstance meleeDisplayInstance = ClientResourceApi.getMeleeDisplayInstance(meleeItem);
        if (meleeDisplayInstance == null) return;

        LuaAnimStateMachine<MeleeAnimStateContext> animStateMachine = meleeDisplayInstance.getAnimStateMachine();
        dev.xcolorful.customgun.client.entity.shooter.player._LocalAnimHandler._tickAnimState(player, animStateMachine);
    }
}
