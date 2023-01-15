package net.its0v3r.itsthirst.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.its0v3r.itsthirst.identifier.NetworkPacketsIdentifiers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ClientTicksEventHandler implements ClientTickEvents.StartTick {
    private static BlockPos blockPos;
    private static World world;
    private static PlayerEntity player;


    // Check the player right click
    @Override
    public void onStartTick(MinecraftClient client) {
        if (client.player != null && client.world != null) {
            if (client.options.useKey.wasPressed()) {
                player = client.player;
                world = client.world;
                drinKCheck();
            }
        }
    }


    // Functionality to right click any water when holding shift with empty hand
    private static void drinKCheck() {

        // Get wheres the player is looking at with raycast
        HitResult hitResult = player.raycast(3.0, 0.0f, true);

        // Get the raycast cordinates go get a BlockPos in the world
        blockPos = ((BlockHitResult) hitResult).getBlockPos();


        // If the player is sneaking, and the active hand is the main, and the hand is empty, and the fluidState in the blockPos is presente om the Water FluidTags
        if (!player.isSpectator() && player.isSneaking() && player.getActiveHand() == Hand.MAIN_HAND && player.getMainHandStack().isEmpty()) {

            // Check if the player can drink from a water source
            if (canDrinkFromWater()) {
                // Write the buffer to send data to the server
                PacketByteBuf buffer = PacketByteBufs.create();
                buffer.writeString("water_source");
                buffer.writeBlockPos(blockPos);
                ClientPlayNetworking.send(NetworkPacketsIdentifiers.DRINK_WATER_ID, buffer);
                return;
            }

            // If the player can't drink from water, check if he can drink from rain
            if (canDrinkFromRain()) {
                // Write the buffer to send data to the server
                PacketByteBuf buffer = PacketByteBufs.create();
                buffer.writeString("rain");
                buffer.writeBlockPos(blockPos);
                ClientPlayNetworking.send(NetworkPacketsIdentifiers.DRINK_WATER_ID, buffer);
                return;
            }

            // If the player can't drink from water or rain, check if he can drink from cauldron
            if (canDrinKFromWaterCauldron()) {
                // Write the buffer to send data to the server
                PacketByteBuf buffer = PacketByteBufs.create();
                buffer.writeString("cauldron");
                buffer.writeBlockPos(blockPos);
                ClientPlayNetworking.send(NetworkPacketsIdentifiers.DRINK_WATER_ID, buffer);
                return;
            }
        }
    }


    // Check if the player is looking at an water source block
    private static boolean canDrinkFromWater() {
        return world.getFluidState(blockPos).isIn(FluidTags.WATER);
    }


    // Check if the world is raining and the player is looking at the sky
    private static boolean canDrinkFromRain() {
        return player.getPitch() < -80.0f && world.hasRain(player.getBlockPos().add(0, 2, 0));
    }


    // Check if the player is looking at a cauldron block with water
    private static boolean canDrinKFromWaterCauldron() {
        BlockState blockState = world.getBlockState(blockPos);
        if (!(blockState.getBlock() == Blocks.WATER_CAULDRON)) {
            return false;
        }
        if (blockState.get(LeveledCauldronBlock.LEVEL) != 0) {
            return true;
        }
        return false;
    }
}
