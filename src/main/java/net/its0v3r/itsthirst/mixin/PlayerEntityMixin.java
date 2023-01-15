package net.its0v3r.itsthirst.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.its0v3r.ItsThirstMain;
import net.its0v3r.itsthirst.access.ThirstManagerAccess;
import net.its0v3r.itsthirst.registry.ConfigRegistry;
import net.its0v3r.itsthirst.registry.TagRegistry;
import net.its0v3r.itsthirst.thirst.ThirstManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements ThirstManagerAccess {

    // Constructor to have access to the player world (LivingEntity)
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }


    // Thirst Manager defined for this PlayerEntity
    private ThirstManager thirstManager = new ThirstManager();


    // Mehthod to get this player Thirst Manager
    @Override
    public ThirstManager getThirstManager() {
        return this.thirstManager;
    }


    // Update the thirst manager at each tick and change the hot biomes ticks
    @Inject(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;update(Lnet/minecraft/entity/player/PlayerEntity;)V", shift = At.Shift.AFTER))
    private void vanillaThirst$updateThirst(CallbackInfo info) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        this.thirstManager.update(player);

        // Change hot biome ticks value
        if (!this.thirstManager.isBiomeTicksAtMaxValue() && player.world.getBiome(player.getBlockPos()).isIn(TagRegistry.HOT_BIOME)) {
            this.thirstManager.biomeTicks++;
        } else if (!player.world.getBiome(player.getBlockPos()).isIn(TagRegistry.HOT_BIOME)) {
            if (this.thirstManager.biomeTicks > 0) {
                this.thirstManager.biomeTicks--;
            }
        }
    }


    // Auto regen health
    @Inject(method = "tickMovement()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;updateItems()V", shift = At.Shift.BEFORE))
    private void vanillaThirst$autoRegenThirstIfPeaceful(CallbackInfo info) {
        // Regenerate hearts if on peaceful and natural regenration is turned on
        if (this.world.getDifficulty() == Difficulty.PEACEFUL && this.world.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION)) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            this.thirstManager.update(player);

            if (this.thirstManager.isNotFull() && this.age % 10 == 0) {
                this.thirstManager.setThirstLevel(this.thirstManager.getThirstLevel() + 1);
            }
        }
    }


    // Read custom data to NBT
    @Inject(method = "readCustomDataFromNbt", at = @At(value = "TAIL"))
    private void vanillaThirst$readCustomDataFromTag(NbtCompound nbt, CallbackInfo info) {
        this.thirstManager.readNbt(nbt);
    }


    // Write custom data to NBT
    @Inject(method = "writeCustomDataToNbt", at = @At(value = "TAIL"))
    private void vanillaThirst$writeCustomDataToTag(NbtCompound nbt, CallbackInfo info) {
        this.thirstManager.writeNbt(nbt);
    }


    // Add Dehydration based on the player exhaustion
    @Inject(method = "addExhaustion", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;addExhaustion(F)V", shift = At.Shift.AFTER))
    public void vanillaThirst$addExhaustion(float exhaustion, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        // Add more exhaustion in the player is in the nether and the option in active
        if (ConfigRegistry.CONFIG.nether_drains_more_thirst && this.world.getRegistryKey() == World.NETHER) {
            exhaustion *= ConfigRegistry.CONFIG.nether_drains_more_thirst_value;
        }

        // Add more exhaustion in the player is in a hot biome and the option in active
        if (ConfigRegistry.CONFIG.hot_biomes_drains_more_thirst && this.thirstManager.isInHotBiomeForEnoughtTime) {
            exhaustion *= ConfigRegistry.CONFIG.hot_biomes_drains_more_thirst_value;
        }

        // Add exhaustion
        this.thirstManager.addDehydration(exhaustion / ConfigRegistry.CONFIG.general_thirst_drain_speed);
    }
}

