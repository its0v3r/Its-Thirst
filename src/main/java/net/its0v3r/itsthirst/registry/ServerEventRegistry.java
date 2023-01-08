package net.its0v3r.itsthirst.registry;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.its0v3r.ItsThirstMain;
import net.its0v3r.itsthirst.event.ServerEntityWorldChangeEventsHandler;

public class ServerEventRegistry {
    public static void register() {
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(new ServerEntityWorldChangeEventsHandler());
    }
}
