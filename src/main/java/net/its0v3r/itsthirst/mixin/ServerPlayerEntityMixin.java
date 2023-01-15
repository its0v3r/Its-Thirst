package net.its0v3r.itsthirst.mixin;

import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.its0v3r.itsthirst.access.ServerPlayerAccess;
import net.its0v3r.itsthirst.access.ThirstManagerAccess;
import net.its0v3r.itsthirst.identifier.NetworkPacketsIdentifiers;
import net.its0v3r.itsthirst.network.utils.defaultBuffers;
import net.its0v3r.itsthirst.thirst.ThirstManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity implements ServerPlayerAccess {
    // Access the thirst manager
    private final ThirstManager thirstManager = ((ThirstManagerAccess) this).getThirstManager();

    // Variable to check for the syncronyzhation of th thirst
    private int syncedThirstLevel = -1;

    // Variable to check for the syncronyzhation of th thirst
    private boolean syncedIsInHotBiomeForEnoughTime = false;

    // Variable to sync the thirst after the player is not null anymore
    private int syncThirstAfterChangeDimensionValue = 0;

    // Constructor
    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }


    // Sync the thirst level from server to client
    @Inject(method = "playerTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;tick()V", shift = At.Shift.AFTER))
    public void vanillaThirst$syncThirstServerClient(CallbackInfo info) {
        // ServerPlayerEntity
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) (Object) this;

        // Check if the syncedThirstLevel is differente from the thirst level defined on the ServerPlayer
        if (this.syncedThirstLevel != this.thirstManager.getThirstLevel() && this.thirstManager.isModEnabled() || syncThirstAfterChangeDimensionValue > 0 && this.thirstManager.isModEnabled()) {
            // Send a sync packet
            PacketByteBuf buffer = defaultBuffers.createSyncThirstDefaultBuffer(serverPlayerEntity);
            ServerPlayNetworking.send(serverPlayerEntity, NetworkPacketsIdentifiers.SYNC_THIRST_ID, buffer);

            // Sync current thirst with the sync syncedThirstLevel
            this.syncedThirstLevel = this.thirstManager.getThirstLevel();

            // Change the syncThirstAfterChangeDimension
            if (syncThirstAfterChangeDimensionValue > 0) {
                syncThirstAfterChangeDimensionValue--;
            }
        }

        // Sync if the player is in a hot biome for enough time with the client HUD
        if (this.syncedIsInHotBiomeForEnoughTime != this.thirstManager.getIsInHotBiomeForEnoughtTime()) {
            PacketByteBuf buffer = PacketByteBufs.create();
            buffer.writeBoolean(this.thirstManager.getIsInHotBiomeForEnoughtTime());
            ServerPlayNetworking.send(serverPlayerEntity, NetworkPacketsIdentifiers.SYNC_HUD_ID, buffer);
            this.syncedIsInHotBiomeForEnoughTime = this.thirstManager.getIsInHotBiomeForEnoughtTime();
        }
    }


    // Set the thirst sync to -1
    @Inject(method = "copyFrom(Lnet/minecraft/server/network/ServerPlayerEntity;Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;setHealth(F)V"))
    public void vanillaThirst$copyFrom1(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo cir) {
        this.thirstManager.setThirstLevel(((ThirstManagerAccess) oldPlayer).getThirstManager().getThirstLevel());
    }


    // Set the thirst sync to -1
    @Inject(method = "copyFrom(Lnet/minecraft/server/network/ServerPlayerEntity;Z)V", at = @At(value = "TAIL"))
    public void vanillaThirst$copyFrom2(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo cir) {
        this.syncedThirstLevel = -1;
    }


    // Set the thirst sync to -1
    @Inject(method = "teleport", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;setWorld(Lnet/minecraft/server/world/ServerWorld;)V"))
    void vanillaThirst$teleportFix(ServerWorld targetWorld, double x, double y, double z, float yaw, float pitch, CallbackInfo cir) {
        this.syncedThirstLevel = -1;
    }


    // Set the thirst sync to -1
    @Inject(method = "moveToWorld", at = @At(value = "FIELD", target = "Lnet/minecraft/server/network/ServerPlayerEntity;syncedFoodLevel:I", ordinal = 0))
    private void vanillaThirst$moveToWorld(ServerWorld destination, CallbackInfoReturnable<Entity> cir) {
        this.syncedThirstLevel = -1;
    }

    @Override
    public void syncThirstAfterChangeDimension() {
        syncThirstAfterChangeDimensionValue = 5;
    }
}

