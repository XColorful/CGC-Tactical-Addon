package dev.xcolorful.cgctactical.client.event.custom;

import dev.xcolorful.cgctactical.client.resource._AllAssetsManager;
import dev.xcolorful.cgctactical.core.event.custom.CoreEventHandlers;
import dev.xcolorful.customgun.core.api.event.EventPriority;
import dev.xcolorful.customgun.core.api.event.EventType;
import dev.xcolorful.customgun.core.api.event.ICustomEventRegister;

public class ClientEventHandlers {

    public static void registerAll(ICustomEventRegister customEventRegister) {
        CoreEventHandlers.register(customEventRegister, _AllAssetsManager.INSTANCE, EventType.ADD_CLIENT_RELOAD_LISTENER_EVENT, EventPriority.LOW, false);
    }
}
