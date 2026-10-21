package dev.xcolorful.cgctactical.core.api.resource.data.index;

import dev.xcolorful.customgun.core.api.resource.ResourceTag;

public enum IndexSubFolderType implements ResourceTag {
    MELEE(IndexSubFolderTypeTag.MELEE),
    THROWABLE(IndexSubFolderTypeTag.THROWABLE),
    CONSUMABLE(IndexSubFolderTypeTag.CONSUMABLE);

    public final String folderName;
    IndexSubFolderType(String folderName) {
        this.folderName = folderName;
    }

    @Override public String getTagName() {
        return this.folderName.toLowerCase();
    }

    public String getFolderName() {
        return this.folderName;
    }
}
