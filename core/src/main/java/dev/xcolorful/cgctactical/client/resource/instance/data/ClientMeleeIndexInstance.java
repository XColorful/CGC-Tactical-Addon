package dev.xcolorful.cgctactical.client.resource.instance.data;

import dev.xcolorful.cgctactical.client.api.resource.ClientResourceApi;
import dev.xcolorful.cgctactical.client.resource.instance.assets.MeleeDisplayInstance;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.resource.data.data.MeleeData;
import dev.xcolorful.cgctactical.core.resource.data.index.MeleeIndex;
import dev.xcolorful.customgun.core.resource.instance.PojoInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ClientMeleeIndexInstance extends PojoInstance<MeleeIndex> {

    private @Nullable MeleeData meleeDataCache;
    private @Nullable MeleeDisplayInstance meleeDisplayInstanceCache;

    private ClientMeleeIndexInstance(@NotNull MeleeIndex pojo) {
        super(pojo);
    }

    public static @Nullable ClientMeleeIndexInstance fromPojo(MeleeIndex pojo) {
        if (pojo == null) return null;
        ClientMeleeIndexInstance instance = new ClientMeleeIndexInstance(pojo);
        if (!instance.isPojoValid()) return null;
        else return instance;
    }

    @Override public boolean resetCache() {
        this.getMeleeData();
        this.getMeleeDisplayInstance();
        return true;
    }

    // --------Getter--------

    public @Nullable MeleeData getMeleeData() {
        if (this.meleeDataCache == null) {
            MeleeData meleeData = ResourceApi.getMeleeData(this.getPojo().getDataLocation());
            if (meleeData != null && meleeData.isValid()) this.meleeDataCache = meleeData;
        }
        return this.meleeDataCache;
    }

    public @Nullable MeleeDisplayInstance getMeleeDisplayInstance() {
        if (this.meleeDisplayInstanceCache == null) this.meleeDisplayInstanceCache = ClientResourceApi.getMeleeDisplayInstance(this.getPojo().getDisplayIndexLocation());
        return this.meleeDisplayInstanceCache;
    }
}
