package dev.xcolorful.cgctactical.core.resource.instance.data;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.core.api.resource.ResourceApi;
import dev.xcolorful.cgctactical.core.resource.data.data.MeleeData;
import dev.xcolorful.cgctactical.core.resource.data.data.melee._AttributeInfo;
import dev.xcolorful.cgctactical.core.resource.data.index.MeleeIndex;
import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.customgun.core.api.minecraft.IMcRegistry;
import dev.xcolorful.customgun.core.resource.instance.PojoInstance;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class MeleeIndexInstance extends PojoInstance<MeleeIndex> {

    /**
     * 使用 {@code ResourceLocation#toString} 生成 {@code Identifier} (UUID功能)
     * <li>攻击伤害/攻速直接取原版物品默认属性修饰符里的实例，因为原版 tooltip 以 {@code ==} 判断这两项</li>
     */
    private static final Map<Identifier, Identifier> attributeModifierIdCache = new HashMap<>();
    static {
        IMcRegistry mcRegistry = CustomGun.getMcRegistry();
        ItemAttributeModifiers vanillaModifiers = new ItemStack(Items.IRON_SWORD).getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
        // [1.21.4, ) 移除了 "generic." 前缀
        attributeModifierIdCache.put(mcRegistry.createResourceLocation("minecraft:attack_damage"),
                _getVanillaModifierId(vanillaModifiers, Attributes.ATTACK_DAMAGE));
        attributeModifierIdCache.put(mcRegistry.createResourceLocation("minecraft:attack_speed"),
                _getVanillaModifierId(vanillaModifiers, Attributes.ATTACK_SPEED));
    }
    private static Identifier _getVanillaModifierId(ItemAttributeModifiers modifiers, Holder<Attribute> attribute) {
        for (ItemAttributeModifiers.Entry entry : modifiers.modifiers()) {
            if (entry.attribute().equals(attribute)) return entry.modifier().id();
        }
        throw new IllegalStateException("Vanilla iron sword has no attribute modifier for " + attribute);
    }
    /**
     * 1.21.4 起原版属性 ID 去掉了 {@code generic.} 前缀，旧版数据包仍可能使用旧 ID
     */
    private static @Nullable Identifier _resolveAttributeLocation(Identifier location) {
        if (BuiltInRegistries.ATTRIBUTE.containsKey(location)) return location;
        if ("minecraft".equals(location.getNamespace()) && location.getPath().startsWith("generic.")) {
            var stripped = CustomGun.getMcRegistry().createResourceLocation(
                    "minecraft:" + location.getPath().substring("generic.".length()));
            if (BuiltInRegistries.ATTRIBUTE.containsKey(stripped)) return stripped;
        }
        return null;
    }

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
            ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder = ImmutableMultimap.builder();
            Map<Identifier, _AttributeInfo> attributeData = this.meleeDataCache.getAttributeData();
            if (attributeData != null) {
                for (Map.Entry<Identifier, _AttributeInfo> entry : attributeData.entrySet()) {
                    var dataLocation = entry.getKey();
                    @Nullable var attributeLocation = _resolveAttributeLocation(dataLocation);
                    @Nullable Holder<Attribute> attribute = attributeLocation == null ? null : BuiltInRegistries.ATTRIBUTE.get(attributeLocation).orElse(null);
                    if (attribute == null) {
                        CgcTactical.LOGGER.debug("MeleeIndexInstance: Attribute {} not found", dataLocation);
                        continue;
                    }

                    _AttributeInfo attributeInfo = entry.getValue();
                    builder.put(attribute, new AttributeModifier(
                            attributeModifierIdCache.computeIfAbsent(attributeLocation, location -> location),
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
