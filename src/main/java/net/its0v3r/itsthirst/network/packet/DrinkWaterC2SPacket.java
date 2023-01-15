package net.its0v3r.itsthirst.network.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.its0v3r.ItsThirstMain;
import net.its0v3r.itsthirst.access.LeveledCauldronBlockMixinAccess;
import net.its0v3r.itsthirst.access.ThirstManagerAccess;
import net.its0v3r.itsthirst.identifier.NetworkPacketsIdentifiers;
import net.its0v3r.itsthirst.registry.ConfigRegistry;
import net.its0v3r.itsthirst.registry.EffectRegistry;
import net.its0v3r.itsthirst.thirst.ThirstManager;
import net.minecraft.block.BlockState;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;

public class DrinkWaterC2SPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buffer, PacketSender responseSender) {

        // ThirstManager
        ThirstManager thirstManager = ((ThirstManagerAccess) player).getThirstManager();

        // Change the boolean value for drink cooldown
        String drinkSource = buffer.readString();

        // Get the block pos
        BlockPos blockPos = buffer.readBlockPos();


        // Drink water from water source action
        if (Objects.equals(drinkSource, "water_source") && thirstManager.isNotFull() && !thirstManager.hasDrankFromSomeSource()) {
            // If bad water is active
            if (ConfigRegistry.CONFIG.bad_water && ConfigRegistry.CONFIG.bad_water_applies_to_water_source) {
                applyThirstEffectChance(player, blockPos, "water_source", thirstManager);
            }

            // Drink water logic
            drinkWater(player, thirstManager, ConfigRegistry.CONFIG.water_source_restore_thirst_factor);

            // Set has drank from water source for the cooldown
            thirstManager.hasDrankFromWaterSource = true;
        }


        // Drink water from rain action
        if (Objects.equals(drinkSource, "rain") && thirstManager.isNotFull() && !thirstManager.hasDrankFromSomeSource()) {
            // If bad water is active
            if (ConfigRegistry.CONFIG.bad_water && ConfigRegistry.CONFIG.bad_water_applies_to_rain) {
                applyThirstEffectChance(player, blockPos, "rain", thirstManager);
            }

            // Drink water logic
            drinkWater(player, thirstManager, ConfigRegistry.CONFIG.rain_restore_thirst_factor);

            // Set has drank from rain for the cooldown
            thirstManager.hasDrankFromRain = true;
        }


        // Drink water from cauldron action
        if (Objects.equals(drinkSource, "cauldron") && thirstManager.isNotFull() && !thirstManager.hasDrankFromSomeSource()) {
            // If bad water is active
            if (ConfigRegistry.CONFIG.bad_water && ConfigRegistry.CONFIG.bad_water_applies_to_cauldron) {
                applyThirstEffectChance(player, blockPos, "cauldron", thirstManager);
            }

            // Drink water logic
            drinkWater(player, thirstManager, ConfigRegistry.CONFIG.cauldron_restore_thirst_factor);

            // Use the Duck Interface to update the SipLevel
            World world = player.getWorld();
            BlockState cauldronBlockState = world.getBlockState(blockPos);
            ((LeveledCauldronBlockMixinAccess) cauldronBlockState.getBlock()).updateSipLevel(cauldronBlockState, world, blockPos);

            // Set has drank from water source for the cooldown
            thirstManager.hasDrankFromCauldron = true;
        }

    }


    // Check if the player will receive the thirst effect
    private static void applyThirstEffectChance(ServerPlayerEntity player, BlockPos blockPos, String drinkSource, ThirstManager thirstManager) {
        // Get the world
        ServerWorld world = player.getWorld();

        // Get the bad water chance
        float bad_water_chance = 0;

        if (Objects.equals(drinkSource, "water_source")) {
            bad_water_chance = ConfigRegistry.CONFIG.bad_water_from_water_source_chance;
        } else if (Objects.equals(drinkSource, "rain")) {
            bad_water_chance = ConfigRegistry.CONFIG.bad_water_from_rain_chance;
        } else if (Objects.equals(drinkSource, "cauldron")) {
            bad_water_chance = ConfigRegistry.CONFIG.bad_water_from_cauldron_chance;
        }

        // Calculate the bad water chance
        if (world.random.nextFloat() <= bad_water_chance) {
            player.addStatusEffect(new StatusEffectInstance(EffectRegistry.THIRST, ConfigRegistry.CONFIG.bad_water_thirst_effect_duration * 20, 0, false, false, true));
        }

    }


    // Drink water
    private static void drinkWater(ServerPlayerEntity player, ThirstManager thirstManager, int thirstRestorationFactor) {
        // Get the world
        ServerWorld world = player.getWorld();

        // Play the drink sound
        world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_GENERIC_DRINK,
                SoundCategory.PLAYERS, 0.5f, world.random.nextFloat() * 0.1f + 0.9f);

        // Add a thirstLevel
        thirstManager.add(thirstRestorationFactor);

        //Swing hand on server
        player.swingHand(player.getActiveHand());

        // Make the server tell the client player to swing his hand
        ServerPlayNetworking.send(player, NetworkPacketsIdentifiers.SWING_HAND_ID, PacketByteBufs.create());
    }
}
