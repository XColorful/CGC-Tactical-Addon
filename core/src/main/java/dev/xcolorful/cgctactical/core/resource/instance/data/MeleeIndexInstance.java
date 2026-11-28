package dev.xcolorful.cgctactical.core.resource.instance.data;

import com.google.common.collect.Multimap;
import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.resource.data.data.MeleeData;
import dev.xcolorful.cgctactical.core.resource.data.data.melee._AttributeInfo;
import dev.xcolorful.cgctactical.core.resource.data.index.MeleeIndex;
import dev.xcolorful.customgun.core.resource.instance.PojoInstance;
import dev.xcolorful.customgun.core.util.EntityUtils;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class MeleeIndexInstance extends PojoInstance<MeleeIndex> {

    private MeleeData meleeDataCache;
    private Multimap<Holder<Attribute>, AttributeModifier> attributeModifierCache;

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
            var builder = EntityUtils._Attribute.createAttributeModifierBuilder();
            Map<Identifier, _AttributeInfo> attributeData = this.meleeDataCache.getAttributeData();
            if (attributeData != null) {
                for (Map.Entry<Identifier, _AttributeInfo> entry : attributeData.entrySet()) {
                    var attributeLocation = entry.getKey();
                    @Nullable var attribute = EntityUtils._Attribute.resolveAttributeLocation(attributeLocation);
                    if (attribute == null) {
                        CgcTactical.LOGGER.debug("MeleeIndexInstance: Failed to read Attribute {}", attributeLocation);
                        continue;
                    }

                    _AttributeInfo attributeInfo = entry.getValue();
                    builder.put(attribute, EntityUtils._Attribute.createAttributeModifier(attributeLocation,
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
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifierCache() {
        return this.attributeModifierCache;
    }
}
