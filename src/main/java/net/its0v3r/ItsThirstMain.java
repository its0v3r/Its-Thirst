package net.its0v3r;

import net.fabricmc.api.ModInitializer;
import net.its0v3r.itsthirst.registry.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItsThirstMain implements ModInitializer {
    public static final String MOD_ID = "itsthirst";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        NetworkRegistry.registerC2SPackets();
        ConfigRegistry.register();
        ServerEventRegistry.register();
        SoundRegistry.register();
        EffectRegistry.register();
    }
}
