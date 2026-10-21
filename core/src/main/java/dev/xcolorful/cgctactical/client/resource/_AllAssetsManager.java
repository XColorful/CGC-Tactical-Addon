package dev.xcolorful.cgctactical.client.resource;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.client.api.resource.ClientResourceApi;
import dev.xcolorful.cgctactical.client.resource.assets.DisplayManager;
import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.customgun.client.api.event.IAddClientReloadListenerEvent;
import dev.xcolorful.customgun.core.api.event.EventType;
import dev.xcolorful.customgun.core.api.event.IEvent;
import dev.xcolorful.customgun.core.api.event.IEventHandler;
import dev.xcolorful.customgun.core.resource.ResourcePojoManager;
import dev.xcolorful.customgun.core.util.ResourceUtils;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Go to {@link ClientResourceApi}
 */
@ApiStatus.Internal
public class _AllAssetsManager implements IEventHandler {
    public static final _AllAssetsManager INSTANCE = new _AllAssetsManager();

    @Override public String getEventHandlerName() {
        return this.getClass().getName();
    }
    @Override public void handleEvent(EventType eventType, IEvent event) {
        switch (eventType) {
            case ADD_CLIENT_RELOAD_LISTENER_EVENT -> onAddClientReloadListenerEvent((IAddClientReloadListenerEvent) event);
            default -> onReceiveWrongEvent(eventType);
        }
    }

    private final List<ResourcePojoManager<?>> reloadListeners;

    /**
     * ./resourcepacks/{resourcepack}/assets/{namespace}/{@link dev.xcolorful.customgun.client.api.resource.assets.AssetsFolderType#DISPLAY}
     */
    public @Nullable DisplayManager.ConsumableDisplayManager consumableDisplayManager;
    public @Nullable DisplayManager.MeleeDisplayManager meleeDisplayManager;
    public @Nullable DisplayManager.ThrowableDisplayManager throwableDisplayManager;

    private _AllAssetsManager() {
        this.reloadListeners = new ArrayList<>();
    }

    protected void reloadAndRegister(IAddClientReloadListenerEvent event) {
        this.reloadListeners.clear();
        // 注册时按顺序重载
        this.consumableDisplayManager = addToListener(this.reloadListeners, new DisplayManager.ConsumableDisplayManager());
        this.meleeDisplayManager = addToListener(this.reloadListeners, new DisplayManager.MeleeDisplayManager());
        this.throwableDisplayManager = addToListener(this.reloadListeners, new DisplayManager.ThrowableDisplayManager());

        this.reloadListeners.forEach((pojoManager) -> event.addListener(pojoManager.getRegistryName(), pojoManager));
        event.addListener(
                CustomGun.getMcRegistry().createResourceLocation(CgcTactical.MOD_ID + ":all_assets_manager"),
                ResourceUtils.asyncReloadListener(_AssetsInstanceManager::reload)
        );
    }
    private <T extends ResourcePojoManager<?> & PreparableReloadListener> T addToListener(List<ResourcePojoManager<?>> reloadListeners, T listener) {
        reloadListeners.add(listener);
        return listener;
    }

    private static void onAddClientReloadListenerEvent(IAddClientReloadListenerEvent event) {
        _AllAssetsManager.INSTANCE.reloadAndRegister(event);
    }
}
