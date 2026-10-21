package dev.xcolorful.cgctactical.core.resource;

import com.google.common.collect.ImmutableMap;
import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.api.resource.INetworkCacheReloadListener;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.network.message.resource.S2CMessageSyncDataPack;
import dev.xcolorful.cgctactical.core.resource.data.DataManager;
import dev.xcolorful.cgctactical.core.resource.data.IndexManager;
import dev.xcolorful.cgctactical.core.resource.network.SyncDataType;
import dev.xcolorful.cgctactical.core.util.SendUtils;
import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.customgun.core.api.event.*;
import dev.xcolorful.customgun.core.api.resource.data.DataFolderType;
import dev.xcolorful.customgun.core.resource.ResourceFileManager;
import dev.xcolorful.customgun.core.resource.ResourcePojoManager;
import dev.xcolorful.customgun.core.util.ResourceUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Go to {@link ResourceApi}
 */
@ApiStatus.Internal
public class _AllDataManager implements IEventHandler {
    protected static final _AllDataManager INSTANCE = new _AllDataManager();
    private static volatile _AllDataManager CURRENT;
    /**
     * 仅单人游戏/专用服务端可用
     */
    public static @Nullable _AllDataManager getCurrent() {
        return CURRENT;
    }
    /**
     * 线程安全
     */
    public static void clearInstance() {
        if (_AllDataManager.CURRENT == null) return;
        _AllDataManager.CURRENT = null;
    }

    @Override public String getEventHandlerName() {
        return this.getClass().getName();
    }
    @Override public void handleEvent(EventType eventType, IEvent event) {
        switch (eventType) {
            case ADD_SERVER_RELOAD_LISTENER_EVENT -> onAddServerReloadListenerEvent((IAddServerReloadListenerEvent) event);
            case TAGS_UPDATED_EVENT-> onTagsUpdateEvent((ITagsUpdatedEvent) event);
            case DATAPACK_SYNC_EVENT -> onDatapackSyncEvent((IDatapackSyncEvent) event);
            default -> onReceiveWrongEvent(eventType);
        }
    }

    private final List<ResourcePojoManager<?>> reloadListeners;
    private final List<INetworkCacheReloadListener> networkCacheListeners;

    /**
     * ./datapacks/{datapack}/data/{namespace}/{@link DataFolderType#DATA}
     */
    public @Nullable DataManager.ConsumableDataManager consumableDataManager;
    public @Nullable DataManager.MeleeDataManager meleeDataManager;
    public @Nullable DataManager.ThrowableDataManager throwableDataManager;
    /**
     * ./datapacks/{datapack}/data/{namespace}/{@link DataFolderType#INDEX}
     */
    public @Nullable IndexManager.ConsumableIndexManager consumableIndexManager;
    public @Nullable IndexManager.MeleeIndexManager meleeIndexManager;
    public @Nullable IndexManager.ThrowableIndexManager throwableIndexManager;

    private _AllDataManager() {
        this.reloadListeners = new ArrayList<>();
        this.networkCacheListeners = new ArrayList<>();
    }

    protected void reloadAndRegister(IAddServerReloadListenerEvent event) {
        this.reloadListeners.clear();
        // 注册时按顺序重载
        this.consumableDataManager = addToListener(this.reloadListeners, new DataManager.ConsumableDataManager());
        this.meleeDataManager = addToListener(this.reloadListeners, new DataManager.MeleeDataManager());
        this.throwableDataManager = addToListener(this.reloadListeners, new DataManager.ThrowableDataManager());

        // index依赖data放在后面
        this.consumableIndexManager = addToListener(this.reloadListeners, new IndexManager.ConsumableIndexManager());
        this.meleeIndexManager = addToListener(this.reloadListeners, new IndexManager.MeleeIndexManager());
        this.throwableIndexManager = addToListener(this.reloadListeners, new IndexManager.ThrowableIndexManager());

        this.reloadListeners.forEach((pojoManager) -> event.addListener(pojoManager.getRegistryName(), pojoManager));
        this.networkCacheListeners.clear();
        this.networkCacheListeners.addAll(this.reloadListeners.stream()
                .filter(INetworkCacheReloadListener.class::isInstance)
                .map(INetworkCacheReloadListener.class::cast)
                .toList()
        );
        event.addListener(
                CustomGun.getMcRegistry().createResourceLocation(CgcTactical.MOD_ID + ":all_data_manager"),
                ResourceUtils.asyncReloadListener(() -> {})
        );
    }
    private <T extends ResourceFileManager<?>> T _registerListener(IAddServerReloadListenerEvent event, T listener) {
        event.addListener(listener.getRegistryName(), listener);
        return listener;
    }
    private <T extends ResourcePojoManager<?> & PreparableReloadListener> T addToListener(List<ResourcePojoManager<?>> reloadListeners, T listener) {
        reloadListeners.add(listener);
        return listener;
    }

    public Map<SyncDataType, Map<Identifier, String>> getNetworkCache() {
        ImmutableMap.Builder<SyncDataType, Map<Identifier, String>> builder = ImmutableMap.builder();
        for (INetworkCacheReloadListener listener : this.networkCacheListeners) {
            builder.put(listener.getSyncDataType(), listener.getNetworkCache());
        }
        return builder.build();
    }

    private static void onAddServerReloadListenerEvent(IAddServerReloadListenerEvent event) {
        _AllDataManager.INSTANCE.reloadAndRegister(event);
        _AllDataManager.CURRENT = _AllDataManager.INSTANCE;
    }

    /**
     * {@link ITagsUpdatedEvent} 会在 {@link #reloadAndRegister} PojoManager {@link ResourcePojoManager#apply} 全结束之后触发
     */
    private static void onTagsUpdateEvent(ITagsUpdatedEvent event) {
//        if (!event.getLogicalSide().isServer()) return;
        if (event.getUpdateCause() != ITagsUpdatedEvent.UpdateCause.SERVER_DATA_LOAD) return;

        // 需要等ResourcePojoManager#apply全结束才执行
        _DataInstanceManager.reload();
    }

    private static void onDatapackSyncEvent(IDatapackSyncEvent event) {
        if (getCurrent() == null) return;

        S2CMessageSyncDataPack message = new S2CMessageSyncDataPack(getCurrent().getNetworkCache());
        event.getRelevantPlayers().forEach(player -> SendUtils.sendMessageToPlayer(player, message));
    }

    public static void onServerStopped(MinecraftServer server) {
        _AllDataManager.clearInstance();
    }

    public static void reloadAllPack() {
        MinecraftServer server = CustomGun.getMinecraftServer();
        if (server == null) return;

        PackRepository packRepository = server.getPackRepository();
        packRepository.reload();

        Collection<String> collection = packRepository.getSelectedIds();
        server.reloadResources(collection);
    }

    // --------RegisterInit--------

    @ApiStatus.Internal
    public static IEventHandler _getInternal() {
        return _AllDataManager.INSTANCE;
    }
}
