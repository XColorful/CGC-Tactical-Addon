package dev.xcolorful.cgctactical.core.resource.data;

import dev.xcolorful.cgctactical.core.api.resource.INetworkCacheReloadListener;
import dev.xcolorful.cgctactical.core.api.resource.data.index.IndexSubFolderType;
import dev.xcolorful.cgctactical.core.resource.data.index.ConsumableIndex;
import dev.xcolorful.cgctactical.core.resource.data.index.MeleeIndex;
import dev.xcolorful.cgctactical.core.resource.data.index.ThrowableIndex;
import dev.xcolorful.cgctactical.core.resource.network.SyncDataType;
import dev.xcolorful.customgun.core.api.resource.FileExtensionType;
import dev.xcolorful.customgun.core.api.resource.data.DataFolderName;
import dev.xcolorful.customgun.core.api.resource.data.DataFolderType;
import dev.xcolorful.customgun.core.resource.ResourcePojo;
import dev.xcolorful.customgun.core.resource.ResourcePojoManager;
import dev.xcolorful.customgun.core.util.JsonUtils;
import net.minecraft.server.packs.PackType;
import org.jetbrains.annotations.ApiStatus;

import java.util.Arrays;

/**
 * 目录名称{@link DataFolderType} + 子目录名称{@link IndexSubFolderType}
 */
public abstract class IndexManager<T extends ResourcePojo<T>> extends ResourcePojoManager<T> {

    public IndexManager(String subPrefix, String extension, JsonUtils.ReadFunction<T> fromJson) {
        super(PackType.SERVER_DATA, Arrays.asList(DataFolderType.INDEX.getFolderName() + "/" + subPrefix, DataFolderName.INDEX_OLD1 + "/" + subPrefix),
                extension, fromJson);
    }

    public static final class MeleeIndexManager extends IndexManager<MeleeIndex> implements INetworkCacheReloadListener {
        @ApiStatus.Internal
        public MeleeIndexManager() {
            super(IndexSubFolderType.MELEE.getFolderName(),
                    FileExtensionType.JSON.getExtensionNameWithDot(),
                    MeleeIndex::fromJson);
        }
        @Override public SyncDataType getSyncDataType() {
            return SyncDataType.MELEE_INDEX;
        }
    }

    public static final class ThrowableIndexManager extends IndexManager<ThrowableIndex> implements INetworkCacheReloadListener {
        @ApiStatus.Internal
        public ThrowableIndexManager() {
            super(IndexSubFolderType.THROWABLE.getFolderName(),
                    FileExtensionType.JSON.getExtensionNameWithDot(),
                    ThrowableIndex::fromJson);
        }
        @Override public SyncDataType getSyncDataType() {
            return SyncDataType.THROWABLE_INDEX;
        }
    }

    public static final class ConsumableIndexManager extends IndexManager<ConsumableIndex> implements INetworkCacheReloadListener {
        @ApiStatus.Internal
        public ConsumableIndexManager() {
            super(IndexSubFolderType.CONSUMABLE.getFolderName(),
                    FileExtensionType.JSON.getExtensionNameWithDot(),
                    ConsumableIndex::fromJson);
        }
        @Override public SyncDataType getSyncDataType() {
            return SyncDataType.CONSUMABLE_INDEX;
        }
    }
}
