package net.its0v3r;

import net.fabricmc.api.ClientModInitializer;
import net.its0v3r.itsthirst.registry.ClientEventRegistry;
import net.its0v3r.itsthirst.registry.NetworkRegistry;

public class ItsThirstClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        ClientEventRegistry.register();
        NetworkRegistry.registerS2CPackets();
    }
}
