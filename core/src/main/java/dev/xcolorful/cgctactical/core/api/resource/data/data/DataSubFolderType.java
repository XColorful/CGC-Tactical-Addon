package dev.xcolorful.cgctactical.core.api.resource.data.data;

import dev.xcolorful.customgun.core.api.resource.ResourceTag;

public enum DataSubFolderType implements ResourceTag {
    MELEE(DataSubFolderTypeTag.MELEE),
    THROWABLE(DataSubFolderTypeTag.THROWABLE),
    CONSUMABLE(DataSubFolderTypeTag.CONSUMABLE);

    public final String folderName;
    DataSubFolderType(String folderName) {
        this.folderName = folderName;
    }

    @Override public String getTagName() {
        return this.folderName.toLowerCase();
    }

    public String getFolderName() {
        return this.folderName;
    }
}
