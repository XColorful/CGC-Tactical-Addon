package dev.xcolorful.cgctactical.client.resource.assets;

import dev.xcolorful.cgctactical.client.api.resource.assets.display.DisplaySubFolderType;
import dev.xcolorful.cgctactical.client.resource.assets.display.ConsumableDisplay;
import dev.xcolorful.cgctactical.client.resource.assets.display.MeleeDisplay;
import dev.xcolorful.cgctactical.client.resource.assets.display.ThrowableDisplay;
import dev.xcolorful.customgun.client.api.resource.assets.AssetsFolderType;
import dev.xcolorful.customgun.core.api.resource.FileExtensionType;
import dev.xcolorful.customgun.core.api.resource.assets.AssetsFolderName;
import dev.xcolorful.customgun.core.resource.ResourcePojo;
import dev.xcolorful.customgun.core.resource.ResourcePojoManager;
import dev.xcolorful.customgun.core.util.JsonUtils;
import net.minecraft.server.packs.PackType;
import org.jetbrains.annotations.ApiStatus;

import java.util.Arrays;

/**
 * 目录名称{@link AssetsFolderType} + 子目录名称{@link DisplaySubFolderType}
 */
public abstract class DisplayManager<T extends ResourcePojo<T>> extends ResourcePojoManager<T> {

    public DisplayManager(String subPrefix, String extension, JsonUtils.ReadFunction<T> fromJson) {
        super(PackType.CLIENT_RESOURCES, Arrays.asList(AssetsFolderType.DISPLAY.getFolderName() + "/" + subPrefix, AssetsFolderName.DISPLAY_OLD1 + "/" + subPrefix),
                extension, fromJson);
    }

    public static final class ConsumableDisplayManager extends DisplayManager<ConsumableDisplay> {
        @ApiStatus.Internal
        public ConsumableDisplayManager() {
            super(DisplaySubFolderType.CONSUMABLE.getFolderName(),
                    FileExtensionType.JSON.getExtensionNameWithDot(),
                    ConsumableDisplay::fromJson);
        }
    }

    public static final class MeleeDisplayManager extends DisplayManager<MeleeDisplay> {
        @ApiStatus.Internal
        public MeleeDisplayManager() {
            super(DisplaySubFolderType.MELEE.getFolderName(),
                    FileExtensionType.JSON.getExtensionNameWithDot(),
                    MeleeDisplay::fromJson);
        }
    }

    public static final class ThrowableDisplayManager extends DisplayManager<ThrowableDisplay> {
        @ApiStatus.Internal
        public ThrowableDisplayManager() {
            super(DisplaySubFolderType.THROWABLE.getFolderName(),
                    FileExtensionType.JSON.getExtensionNameWithDot(),
                    ThrowableDisplay::fromJson);
        }
    }
}
