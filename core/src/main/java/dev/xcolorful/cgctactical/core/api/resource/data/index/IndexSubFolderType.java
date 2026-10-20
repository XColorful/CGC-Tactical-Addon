package dev.xcolorful.cgctactical.core.api.resource.data.index;

import dev.xcolorful.customgun.core.api.resource.ResourceTag;

public enum IndexSubFolderType implements ResourceTag {
    CONSUMABLE(IndexSubFolderTypeTag.CONSUMABLE),
    MELEE(IndexSubFolderTypeTag.MELEE),
    THROWABLE(IndexSubFolderTypeTag.THROWABLE);

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
