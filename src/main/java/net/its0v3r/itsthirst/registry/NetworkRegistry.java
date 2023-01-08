package net.its0v3r.itsthirst.registry;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.its0v3r.ItsThirstMain;
import net.its0v3r.itsthirst.identifier.NetworkPacketsIdentifiers;
import net.its0v3r.itsthirst.network.packet.DrinkWaterC2SPacket;
import net.its0v3r.itsthirst.network.packet.SwingHandS2CPacket;
import net.its0v3r.itsthirst.network.packet.SyncThirstS2CPacket;

public class NetworkRegistry {
    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(NetworkPacketsIdentifiers.DRINK_WATER_ID, DrinkWaterC2SPacket::receive);
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(NetworkPacketsIdentifiers.SWING_HAND_ID, SwingHandS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(NetworkPacketsIdentifiers.SYNC_THIRST_ID, SyncThirstS2CPacket::receive);
    }
}
