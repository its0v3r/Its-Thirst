package net.its0v3r.itsthirst.mixin;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.its0v3r.itsthirst.access.ThirstManagerAccess;
import net.its0v3r.itsthirst.registry.ConfigRegistry;
import net.its0v3r.itsthirst.thirst.ThirstManager;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HungerManager.class)
public class HungerManagerMixin {

    // Prevent healing if "only_regen_health_if_hunger_and_thirst_full" is true
    @WrapWithCondition(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;heal(F)V", ordinal = 0))
    private boolean vanillaThirst$disableHealing1(PlayerEntity playerEntity, float f) {
        ThirstManager thirstManager = ((ThirstManagerAccess) playerEntity).getThirstManager();

        if (ConfigRegistry.CONFIG.only_regen_health_if_hunger_and_thirst_full && thirstManager.getThirstLevel() >= 20 && playerEntity.getHungerManager().getFoodLevel() >= 20) {
            return true;
        } else if (!ConfigRegistry.CONFIG.only_regen_health_if_hunger_and_thirst_full && playerEntity.getHungerManager().getFoodLevel() >= 20) {
            return true;
        } else {
            return false;
        }
    }


    // Prevent healing if "only_regen_health_if_hunger_and_thirst_full" is true
    @WrapWithCondition(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;heal(F)V", ordinal = 1))
    private boolean vanillaThirst$disableHealing2(PlayerEntity playerEntity, float f) {
        ThirstManager thirstManager = ((ThirstManagerAccess) playerEntity).getThirstManager();

        if (ConfigRegistry.CONFIG.only_regen_health_if_hunger_and_thirst_full && thirstManager.getThirstLevel() >= 18 && playerEntity.getHungerManager().getFoodLevel() >= 18) {
            return true;
        } else if (!ConfigRegistry.CONFIG.only_regen_health_if_hunger_and_thirst_full && playerEntity.getHungerManager().getFoodLevel() >= 18) {
            return true;
        } else {
            return false;
        }
    }


    // Prevent healing if "only_regen_health_if_hunger_and_thirst_full" is true
    @WrapWithCondition(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;addExhaustion(F)V", ordinal = 0))
    private boolean vanillaThirst$disableHealing3(HungerManager instance, float f, PlayerEntity playerEntity) {
        ThirstManager thirstManager = ((ThirstManagerAccess) playerEntity).getThirstManager();

        if (ConfigRegistry.CONFIG.only_regen_health_if_hunger_and_thirst_full && thirstManager.getThirstLevel() >= 20 && playerEntity.getHungerManager().getFoodLevel() >= 20) {
            return true;
        } else if (!ConfigRegistry.CONFIG.only_regen_health_if_hunger_and_thirst_full && playerEntity.getHungerManager().getFoodLevel() >= 20) {
            return true;
        } else {
            return false;
        }
    }


    // Prevent healing if "only_regen_health_if_hunger_and_thirst_full" is true
    @WrapWithCondition(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;addExhaustion(F)V", ordinal = 1))
    private boolean vanillaThirst$disableHealing4(HungerManager instance, float f, PlayerEntity playerEntity) {
        ThirstManager thirstManager = ((ThirstManagerAccess) playerEntity).getThirstManager();

        if (ConfigRegistry.CONFIG.only_regen_health_if_hunger_and_thirst_full && thirstManager.getThirstLevel() >= 18 && playerEntity.getHungerManager().getFoodLevel() >= 18) {
            return true;
        } else if (!ConfigRegistry.CONFIG.only_regen_health_if_hunger_and_thirst_full && playerEntity.getHungerManager().getFoodLevel() >= 18) {
            return true;
        } else {
            return false;
        }
    }
}