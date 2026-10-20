package dev.xcolorful.cgctactical.core.resource.instance.data;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.resource.data.data.ThrowableData;
import dev.xcolorful.cgctactical.core.resource.data.index.ThrowableIndex;
import dev.xcolorful.customgun.core.resource.instance.PojoInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ThrowableIndexInstance extends PojoInstance<ThrowableIndex> {

    private ThrowableData throwableDataCache;

    private ThrowableIndexInstance(@NotNull ThrowableIndex pojo) {
        super(pojo);
    }

    public static @Nullable ThrowableIndexInstance fromPojo(ThrowableIndex pojo) {
        if (pojo == null) return null;
        ThrowableIndexInstance instance = new ThrowableIndexInstance(pojo);
        if (!instance.isPojoValid()) return null;
        else return instance;
    }

    @Override public boolean resetCache() {
        var pojo = this.getPojo();

        this.throwableDataCache = ResourceApi.getThrowableData(pojo.getDataLocation());
        if (this.throwableDataCache == null) {
            CgcTactical.LOGGER.debug("ThrowableIndexInstance: ThrowableData {} not found", pojo.getDataLocation());
            return false;
        } else if (!this.throwableDataCache.isValid()) {
            CgcTactical.LOGGER.debug("ThrowableIndexInstance: ThrowableData {} not valid", pojo.getDataLocation());
            return false;
        }

        return true;
    }

    // --------Getter--------

    public ThrowableData getThrowableData() {
        return this.throwableDataCache;
    }
}
