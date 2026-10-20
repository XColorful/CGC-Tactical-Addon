package dev.xcolorful.cgctactical.client.resource.instance.data;

import dev.xcolorful.cgctactical.client.api.resource.ClientResourceApi;
import dev.xcolorful.cgctactical.client.resource.instance.assets.ThrowableDisplayInstance;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.resource.data.data.ThrowableData;
import dev.xcolorful.cgctactical.core.resource.data.index.ThrowableIndex;
import dev.xcolorful.customgun.core.resource.instance.PojoInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ClientThrowableIndexInstance extends PojoInstance<ThrowableIndex> {

    private @Nullable ThrowableData throwableDataCache;
    private @Nullable ThrowableDisplayInstance throwableDisplayInstanceCache;

    private ClientThrowableIndexInstance(@NotNull ThrowableIndex pojo) {
        super(pojo);
    }

    public static @Nullable ClientThrowableIndexInstance fromPojo(ThrowableIndex pojo) {
        if (pojo == null) return null;
        ClientThrowableIndexInstance instance = new ClientThrowableIndexInstance(pojo);
        if (!instance.isPojoValid()) return null;
        else return instance;
    }

    @Override public boolean resetCache() {
        this.getThrowableData();
        this.getThrowableDisplayInstance();
        return true;
    }

    // --------Getter--------

    public @Nullable ThrowableData getThrowableData() {
        if (this.throwableDataCache == null) {
            ThrowableData throwableData = ResourceApi.getThrowableData(this.getPojo().getDataLocation());
            if (throwableData != null && throwableData.isValid()) this.throwableDataCache = throwableData;
        }
        return this.throwableDataCache;
    }

    public @Nullable ThrowableDisplayInstance getThrowableDisplayInstance() {
        if (this.throwableDisplayInstanceCache == null) this.throwableDisplayInstanceCache = ClientResourceApi.getThrowableDisplayInstance(this.getPojo().getDisplayIndexLocation());
        return this.throwableDisplayInstanceCache;
    }
}
