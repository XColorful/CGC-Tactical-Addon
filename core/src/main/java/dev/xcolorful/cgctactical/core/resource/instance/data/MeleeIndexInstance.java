package dev.xcolorful.cgctactical.core.resource.instance.data;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.resource.data.data.MeleeData;
import dev.xcolorful.cgctactical.core.resource.data.data.melee._AttributeInfo;
import dev.xcolorful.cgctactical.core.resource.data.index.MeleeIndex;
import dev.xcolorful.customgun.core.resource.instance.PojoInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MeleeIndexInstance extends PojoInstance<MeleeIndex> {

    /**
     * 使用 {@code ResourceLocation#toString} 生成 {@link UUID}
     */
    private static final Map<ResourceLocation, UUID> attributeUuidCache = new HashMap<>();

    private MeleeData meleeDataCache;
    private Multimap<Attribute, AttributeModifier> attributeModifierCache;

    private MeleeIndexInstance(@NotNull MeleeIndex pojo) {
        super(pojo);
    }

    public static @Nullable MeleeIndexInstance fromPojo(MeleeIndex pojo) {
        if (pojo == null) return null;
        MeleeIndexInstance instance = new MeleeIndexInstance(pojo);
        if (!instance.isPojoValid()) return null;
        else return instance;
    }

    @Override public boolean resetCache() {
        var pojo = this.getPojo();

        this.meleeDataCache = ResourceApi.getMeleeData(pojo.getDataLocation());
        if (this.meleeDataCache == null) {
            CgcTactical.LOGGER.debug("MeleeIndexInstance: MeleeData {} not found", pojo.getDataLocation());
            return false;
        } else if (!this.meleeDataCache.isValid()) {
            CgcTactical.LOGGER.debug("MeleeIndexInstance: MeleeData {} not valid", pojo.getDataLocation());
            return false;
        }

        {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            Map<ResourceLocation, _AttributeInfo> attributeData = this.meleeDataCache.getAttributeData();
            if (attributeData != null) {
                for (Map.Entry<ResourceLocation, _AttributeInfo> entry : attributeData.entrySet()) {
                    var attributeLocation = entry.getKey();
                    @Nullable Attribute attribute = BuiltInRegistries.ATTRIBUTE.get(attributeLocation);
                    if (attribute == null) {
                        CgcTactical.LOGGER.debug("MeleeIndexInstance: Attribute {} not found", attributeLocation);
                        continue;
                    }

                    _AttributeInfo attributeInfo = entry.getValue();
                    builder.put(attribute, new AttributeModifier(
                            attributeUuidCache.computeIfAbsent(attributeLocation,
                                    location -> UUID.nameUUIDFromBytes(location.toString().getBytes(StandardCharsets.UTF_8))),
                            attributeLocation.toString(),
                            attributeInfo.getAmount(),
                            attributeInfo.getModifierOperation().getOperation()));
                }
            }
            this.attributeModifierCache = builder.build();
        }

        return true;
    }

    // --------Getter--------

    public MeleeData getMeleeData() {
        return this.meleeDataCache;
    }
    public Multimap<Attribute, AttributeModifier> getAttributeModifierCache() {
        return this.attributeModifierCache;
    }
}
