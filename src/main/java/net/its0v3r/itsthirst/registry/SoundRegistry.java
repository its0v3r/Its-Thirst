package net.its0v3r.itsthirst.registry;

import net.its0v3r.ItsThirstMain;
import net.its0v3r.itsthirst.identifier.SoundsIdentifiers;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;

public class SoundRegistry {

    public static SoundEvent PLACEHOLDER_SOUND = SoundEvent.of(SoundsIdentifiers.PLACEHOLDER_SOUND);
    public static SoundEvent SWALLOW_WATER_AFTER_DRINK = SoundEvent.of(SoundsIdentifiers.SWALLOW_WATER_AFTER_DRINK);

    public static void register(){
        Registry.register(Registries.SOUND_EVENT, SoundsIdentifiers.PLACEHOLDER_SOUND, PLACEHOLDER_SOUND);
        Registry.register(Registries.SOUND_EVENT, SoundsIdentifiers.SWALLOW_WATER_AFTER_DRINK, SWALLOW_WATER_AFTER_DRINK);
    }
}
