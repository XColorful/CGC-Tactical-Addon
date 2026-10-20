package dev.xcolorful.cgctactical.core.resource.data;

import dev.xcolorful.cgctactical.core.api.resource.INetworkCacheReloadListener;
import dev.xcolorful.cgctactical.core.api.resource.data.data.DataSubFolderType;
import dev.xcolorful.cgctactical.core.resource.data.data.ConsumableData;
import dev.xcolorful.cgctactical.core.resource.data.data.MeleeData;
import dev.xcolorful.cgctactical.core.resource.data.data.ThrowableData;
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
 * 目录名称{@link DataFolderType} + 子目录名称{@link DataSubFolderType}
 */
public abstract class DataManager<T extends ResourcePojo<T>> extends ResourcePojoManager<T> {

    public DataManager(String subPrefix, String extension, JsonUtils.ReadFunction<T> fromJson) {
        super(PackType.SERVER_DATA, Arrays.asList(DataFolderType.DATA.getFolderName() + "/" + subPrefix, DataFolderName.DATA_OLD1 + "/" + subPrefix),
                extension, fromJson);
    }

    public static final class MeleeDataManager extends DataManager<MeleeData> implements INetworkCacheReloadListener {
        @ApiStatus.Internal
        public MeleeDataManager() {
            super(DataSubFolderType.MELEE.getFolderName(),
                    FileExtensionType.JSON.getExtensionNameWithDot(),
                    MeleeData::fromJson);
        }
        @Override public SyncDataType getSyncDataType() {
            return SyncDataType.MELEE_DATA;
        }
    }

    public static final class ThrowableDataManager extends DataManager<ThrowableData> implements INetworkCacheReloadListener {
        @ApiStatus.Internal
        public ThrowableDataManager() {
            super(DataSubFolderType.THROWABLE.getFolderName(),
                    FileExtensionType.JSON.getExtensionNameWithDot(),
                    ThrowableData::fromJson);
        }
        @Override public SyncDataType getSyncDataType() {
            return SyncDataType.THROWABLE_DATA;
        }
    }

    public static final class ConsumableDataManager extends DataManager<ConsumableData> implements INetworkCacheReloadListener {
        @ApiStatus.Internal
        public ConsumableDataManager() {
            super(DataSubFolderType.CONSUMABLE.getFolderName(),
                    FileExtensionType.JSON.getExtensionNameWithDot(),
                    ConsumableData::fromJson);
        }
        @Override public SyncDataType getSyncDataType() {
            return SyncDataType.CONSUMABLE_DATA;
        }
    }
}
