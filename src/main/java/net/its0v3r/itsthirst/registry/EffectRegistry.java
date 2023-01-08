package net.its0v3r.itsthirst.registry;

import net.its0v3r.itsthirst.effect.ThirstEffect;
import net.its0v3r.itsthirst.identifier.EffectIdentifiers;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.registry.Registry;

public class EffectRegistry {
    public static StatusEffect THIRST;

    public static void register() {
        THIRST = Registry.register(Registry.STATUS_EFFECT, EffectIdentifiers.THIRST, new ThirstEffect(StatusEffectCategory.HARMFUL, 11983920));
    }
}
