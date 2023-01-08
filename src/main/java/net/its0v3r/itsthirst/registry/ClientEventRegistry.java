package net.its0v3r.itsthirst.registry;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.its0v3r.ItsThirstMain;
import net.its0v3r.itsthirst.event.ClientTicksEventHandler;

public class ClientEventRegistry {
    public static void register() {
        // When right clicks a block
        ClientTickEvents.START_CLIENT_TICK.register(new ClientTicksEventHandler());
    }
}