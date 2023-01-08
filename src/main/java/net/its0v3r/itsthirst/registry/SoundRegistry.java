package net.its0v3r.itsthirst.registry;

import net.its0v3r.itsthirst.identifier.SoundsIdentifiers;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

public class SoundRegistry {

    public static SoundEvent PLACEHOLDER_SOUND = new SoundEvent(SoundsIdentifiers.PLACEHOLDER_SOUND);
    public static SoundEvent SWALLOW_WATER_AFTER_DRINK = new SoundEvent(SoundsIdentifiers.SWALLOW_WATER_AFTER_DRINK);

    public static void register() {
        Registry.register(Registry.SOUND_EVENT, SoundsIdentifiers.PLACEHOLDER_SOUND, PLACEHOLDER_SOUND);
        Registry.register(Registry.SOUND_EVENT, SoundsIdentifiers.SWALLOW_WATER_AFTER_DRINK, SWALLOW_WATER_AFTER_DRINK);
    }
}
