package net.its0v3r.itsthirst.registry;

import net.its0v3r.itsthirst.identifier.TagIdentifiers;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;


public class TagRegistry {
    public static final TagKey<Item> HYDRATING_FOOD = TagKey.of(Registry.ITEM_KEY, TagIdentifiers.HYDRATING_FOOD);
    public static final TagKey<Item> HYDRATING_DRINK = TagKey.of(Registry.ITEM_KEY, TagIdentifiers.HYDRATING_DRINK);
    public static final TagKey<Item> HYDRATING_STEW = TagKey.of(Registry.ITEM_KEY, TagIdentifiers.HYDRATING_STEW);
}