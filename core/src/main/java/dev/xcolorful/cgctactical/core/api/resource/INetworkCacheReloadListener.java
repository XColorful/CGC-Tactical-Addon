package dev.xcolorful.cgctactical.core.api.resource;

import dev.xcolorful.cgctactical.core.resource.network.SyncDataType;
import dev.xcolorful.customgun.core.api.resource.INetworkCacheManager;

public interface INetworkCacheReloadListener extends INetworkCacheManager {

    SyncDataType getSyncDataType();
}
