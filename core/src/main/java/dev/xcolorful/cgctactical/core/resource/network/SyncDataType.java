package dev.xcolorful.cgctactical.core.resource.network;

import dev.xcolorful.customgun.core.api.resource.data.DataFolderType;

/**
 * 需要同步到客户端的数据类型
 */
public enum SyncDataType {
    MELEE_DATA(DataFolderType.DATA),
    THROWABLE_DATA(DataFolderType.DATA),
    CONSUMABLE_DATA(DataFolderType.DATA),

    MELEE_INDEX(DataFolderType.INDEX),
    THROWABLE_INDEX(DataFolderType.INDEX),
    CONSUMABLE_INDEX(DataFolderType.INDEX);

    public final DataFolderType dataFolderType;
    SyncDataType(DataFolderType dataFolderType) {
        this.dataFolderType = dataFolderType;
    }
}
