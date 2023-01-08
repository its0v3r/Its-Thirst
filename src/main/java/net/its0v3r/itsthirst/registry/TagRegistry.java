package net.its0v3r.itsthirst.registry;

import net.its0v3r.itsthirst.identifier.TagIdentifiers;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;


public class TagRegistry {
    public static final TagKey<Item> HYDRATING_FOOD = TagKey.of(RegistryKeys.ITEM, TagIdentifiers.HYDRATING_FOOD);
    public static final TagKey<Item> HYDRATING_DRINK = TagKey.of(RegistryKeys.ITEM, TagIdentifiers.HYDRATING_DRINK);
    public static final TagKey<Item> HYDRATING_STEW = TagKey.of(RegistryKeys.ITEM, TagIdentifiers.HYDRATING_STEW);
}
