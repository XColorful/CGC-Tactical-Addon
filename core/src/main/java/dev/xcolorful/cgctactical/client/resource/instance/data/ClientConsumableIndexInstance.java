package dev.xcolorful.cgctactical.client.resource.instance.data;

import dev.xcolorful.cgctactical.client.api.resource.ClientResourceApi;
import dev.xcolorful.cgctactical.client.resource.instance.assets.ConsumableDisplayInstance;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.resource.data.data.ConsumableData;
import dev.xcolorful.cgctactical.core.resource.data.index.ConsumableIndex;
import dev.xcolorful.customgun.core.resource.instance.PojoInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ClientConsumableIndexInstance extends PojoInstance<ConsumableIndex> {

    private @Nullable ConsumableData consumableDataCache;
    private @Nullable ConsumableDisplayInstance consumableDisplayInstanceCache;

    private ClientConsumableIndexInstance(@NotNull ConsumableIndex pojo) {
        super(pojo);
    }

    public static @Nullable ClientConsumableIndexInstance fromPojo(ConsumableIndex pojo) {
        if (pojo == null) return null;
        ClientConsumableIndexInstance instance = new ClientConsumableIndexInstance(pojo);
        if (!instance.isPojoValid()) return null;
        else return instance;
    }

    @Override public boolean resetCache() {
        this.getConsumableData();
        this.getConsumableDisplayInstance();
        return true;
    }

    // --------Getter--------

    public @Nullable ConsumableData getConsumableData() {
        if (this.consumableDataCache == null) {
            ConsumableData consumableData = ResourceApi.getConsumableData(this.getPojo().getDataLocation());
            if (consumableData != null && consumableData.isValid()) this.consumableDataCache = consumableData;
        }
        return this.consumableDataCache;
    }

    public @Nullable ConsumableDisplayInstance getConsumableDisplayInstance() {
        if (this.consumableDisplayInstanceCache == null) this.consumableDisplayInstanceCache = ClientResourceApi.getConsumableDisplayInstance(this.getPojo().getDisplayIndexLocation());
        return this.consumableDisplayInstanceCache;
    }
}
