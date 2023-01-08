package net.its0v3r.itsthirst.registry;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.its0v3r.ItsThirstMain;
import net.its0v3r.itsthirst.config.VanillaThirstConfig;

public class ConfigRegistry {
    public static VanillaThirstConfig CONFIG = new VanillaThirstConfig();

    public static void register() {
        AutoConfig.register(VanillaThirstConfig.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(VanillaThirstConfig.class).getConfig();
    }
}
