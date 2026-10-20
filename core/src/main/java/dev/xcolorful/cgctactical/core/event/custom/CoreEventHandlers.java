package dev.xcolorful.cgctactical.core.event.custom;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.resource._AllDataManager;
import dev.xcolorful.customgun.core.api.event.*;

public class CoreEventHandlers {

    public static void registerAll(ICustomEventRegister customEventRegister) {
        register(customEventRegister, _AllDataManager._getInternal(), EventType.ADD_SERVER_RELOAD_LISTENER_EVENT, EventPriority.NORMAL, false);
        register(customEventRegister, _AllDataManager._getInternal(), EventType.TAGS_UPDATED_EVENT, EventPriority.NORMAL, false);
        register(customEventRegister, _AllDataManager._getInternal(), EventType.DATAPACK_SYNC_EVENT, EventPriority.NORMAL, false);
    }

    public static void register(ICustomEventRegister customEventRegister, ICustomEventHandler eventHandler, CustomEventType customEventType, EventPriority priority, boolean receiveCanceled) {
        if (customEventRegister.register(eventHandler, customEventType, priority, receiveCanceled)) {
            CgcTactical.LOGGER.debug("{} registered to {}", eventHandler.getEventHandlerName(), customEventType);
        } else {
            CgcTactical.LOGGER.debug("Failed to register {} to {}", eventHandler.getEventHandlerName(), customEventType);
        }
    }
    public static void register(ICustomEventRegister customEventRegister, IEventHandler eventHandler, EventType eventType, EventPriority priority, boolean receiveCanceled) {
        if (customEventRegister.register(eventHandler, eventType, priority, receiveCanceled)) {
            CgcTactical.LOGGER.debug("{} registered to {}", eventHandler.getEventHandlerName(), eventType);
        } else {
            CgcTactical.LOGGER.debug("Failed to register {} to {}", eventHandler.getEventHandlerName(), eventType);
        }
    }
}
