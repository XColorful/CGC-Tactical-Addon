package dev.xcolorful.cgctactical.client.api.resource.assets.display;

import dev.xcolorful.cgctactical.core.api.resource.assets.display.DisplaySubFolderTypeTag;
import dev.xcolorful.customgun.core.api.resource.ResourceTag;

public enum DisplaySubFolderType implements ResourceTag {
    CONSUMABLE(DisplaySubFolderTypeTag.CONSUMABLE),
    MELEE(DisplaySubFolderTypeTag.MELEE),
    THROWABLE(DisplaySubFolderTypeTag.THROWABLE);

    public final String folderName;
    DisplaySubFolderType(String folderName) {
        this.folderName = folderName;
    }

    @Override public String getTagName() {
        return this.folderName.toLowerCase();
    }

    public String getFolderName() {
        return this.folderName;
    }
}
