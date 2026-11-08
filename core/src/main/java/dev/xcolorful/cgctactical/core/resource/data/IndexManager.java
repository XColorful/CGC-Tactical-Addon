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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import org.jetbrains.annotations.ApiStatus;

import java.util.Arrays;
import java.util.Map;

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
        @Override protected void onApplyPojoMap(Map<ResourceLocation, MeleeIndex> newPojoMap) {
            super.onApplyPojoMap(newPojoMap);
            /**
             * 旧格式内嵌数据需在写网络缓存前迁移，否则 index 缓存里的 data_location 仍是 back-compat 填的占位值
             */
            MeleeIndex._reloadLegacyData();
            /**
             * 旧格式省略 display 字段 (与索引位置同名同路径) 需要在缓存前迁移，否则 display_location 仍是 back-compat 填的占位值
             */
            for (var entry : newPojoMap.entrySet()) {
                entry.getValue()._reloadLegacyDisplay(entry.getKey());
            }
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
        @Override protected void onApplyPojoMap(Map<ResourceLocation, ThrowableIndex> newPojoMap) {
            super.onApplyPojoMap(newPojoMap);
            /**
             * 旧格式内嵌数据需在写网络缓存前迁移，否则 index 缓存里的 data_location 仍是 back-compat 填的占位值
             */
            ThrowableIndex._reloadLegacyData();
            /**
             * 旧格式省略 display 字段 (与索引位置同名同路径) 需要在缓存前迁移，否则 display_location 仍是 back-compat 填的占位值
             */
            for (var entry : newPojoMap.entrySet()) {
                entry.getValue()._reloadLegacyDisplay(entry.getKey());
            }
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
        @Override protected void onApplyPojoMap(Map<ResourceLocation, ConsumableIndex> newPojoMap) {
            super.onApplyPojoMap(newPojoMap);
            /**
             * 旧格式内嵌数据需在写网络缓存前迁移，否则 index 缓存里的 data_location 仍是 back-compat 填的占位值
             */
            ConsumableIndex._reloadLegacyData();
            /**
             * 旧格式省略 display 字段 (与索引位置同名同路径) 需要在缓存前迁移，否则 display_location 仍是 back-compat 填的占位值
             */
            for (var entry : newPojoMap.entrySet()) {
                entry.getValue()._reloadLegacyDisplay(entry.getKey());
            }
        }
        @Override public SyncDataType getSyncDataType() {
            return SyncDataType.CONSUMABLE_INDEX;
        }
    }
}
