package dev.xcolorful.cgctactical.core.resource.instance.data;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.resource.data.data.ConsumableData;
import dev.xcolorful.cgctactical.core.resource.data.index.ConsumableIndex;
import dev.xcolorful.customgun.core.resource.instance.PojoInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConsumableIndexInstance extends PojoInstance<ConsumableIndex> {

    private ConsumableData consumableDataCache;

    private ConsumableIndexInstance(@NotNull ConsumableIndex pojo) {
        super(pojo);
    }

    public static @Nullable ConsumableIndexInstance fromPojo(ConsumableIndex pojo) {
        if (pojo == null) return null;
        ConsumableIndexInstance instance = new ConsumableIndexInstance(pojo);
        if (!instance.isPojoValid()) return null;
        else return instance;
    }

    @Override public boolean resetCache() {
        var pojo = this.getPojo();

        this.consumableDataCache = ResourceApi.getConsumableData(pojo.getDataLocation());
        if (this.consumableDataCache == null) {
            CgcTactical.LOGGER.debug("ConsumableIndexInstance: ConsumableData {} not found", pojo.getDataLocation());
            return false;
        } else if (!this.consumableDataCache.isValid()) {
            CgcTactical.LOGGER.debug("ConsumableIndexInstance: ConsumableData {} not valid", pojo.getDataLocation());
            return false;
        }

        return true;
    }

    // --------Getter--------

    public ConsumableData getConsumableData() {
        return this.consumableDataCache;
    }
}
