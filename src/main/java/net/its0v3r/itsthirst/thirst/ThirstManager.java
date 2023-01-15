package net.its0v3r.itsthirst.thirst;

import net.its0v3r.ItsThirstMain;
import net.its0v3r.itsthirst.registry.ConfigRegistry;
import net.its0v3r.itsthirst.registry.DamageSourceRegistry;
import net.its0v3r.itsthirst.registry.SoundRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.Difficulty;

public class ThirstManager {
    public boolean isModEnabled = true;
    public int thirstLevel = 20;
    public float dehydrationLevel;
    public int dehydrationTickTimer;
    public boolean hasDrankFromWaterSource = false;
    public int drankFromWaterSourceTickTimer;
    public boolean hasDrankFromRain = false;
    public int drankFromRainTickTimer;
    public boolean hasDrankFromCauldron = false;
    public int drankFromCauldronTickTimer;
    public int biomeTicks = 0;
    public boolean isInHotBiomeForEnoughtTime = false;

    // Add thirst level
    public void add(int thirst) {
        this.thirstLevel = Math.min(thirst + this.thirstLevel, 20);
    }


    // Update thirst level
    public void update(PlayerEntity player) {
        Difficulty difficulty = player.world.getDifficulty();

        // If the player has a dehydration level above 4.0f, remove 1 from the thirstLevel
        if (this.dehydrationLevel > 4.0f) {
            this.dehydrationLevel -= 4.0f;

            if (difficulty != Difficulty.PEACEFUL) {
                this.thirstLevel = Math.max(this.thirstLevel - 1, 0);
            }
        }

        // If the player has 0 thirstLevel give damageevery 4s
        if (this.thirstLevel <= 0) {
            ++this.dehydrationTickTimer;
            if (this.dehydrationTickTimer >= 80) {
                if (player.getHealth() > 10.0F || difficulty == Difficulty.HARD || (player.getHealth() > 1.0F && difficulty == Difficulty.NORMAL)) {
                    player.damage(DamageSourceRegistry.THIRST, 1.0f);
                }
                this.dehydrationTickTimer = 0;
            }
        } else {
            this.dehydrationTickTimer = 0;
        }

        // Applu negative effects if thirst to low
        if (!player.isCreative()) {
            if (thirstLevel <= ConfigRegistry.CONFIG.minimum_thirst_level_to_apply_haste_effect && !player.hasStatusEffect(StatusEffects.HASTE) && ConfigRegistry.CONFIG.should_thirst_apply_haste_effect) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 40, 0, false, false, false));
            }
            if (thirstLevel <= ConfigRegistry.CONFIG.minimum_thirst_level_to_apply_mining_fatigue_effect && !player.hasStatusEffect(StatusEffects.MINING_FATIGUE) && ConfigRegistry.CONFIG.should_thirst_apply_mining_fatigue_effect) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 40, 0, false, false, false));
            }
        }

        // Check if the player has drank water from an water source and add a cooldown
        if (hasDrankFromWaterSource) {
            ++this.drankFromWaterSourceTickTimer;
            if (this.drankFromWaterSourceTickTimer >= (ConfigRegistry.CONFIG.drank_from_water_source_cooldown * 20)) {
                this.drankFromWaterSourceTickTimer = 0;
                this.hasDrankFromWaterSource = false;
                player.getWorld().playSound(null, player.getBlockPos(), SoundRegistry.SWALLOW_WATER_AFTER_DRINK,
                        SoundCategory.PLAYERS, 0.5f, player.getWorld().random.nextFloat() * 0.1f + 0.9f);
            }
        }

        // Check if the player has drank water from rain and add a cooldown
        if (hasDrankFromRain) {
            ++this.drankFromRainTickTimer;
            if (this.drankFromRainTickTimer >= (ConfigRegistry.CONFIG.drank_from_rain_cooldown * 20)) {
                this.drankFromRainTickTimer = 0;
                this.hasDrankFromRain = false;
                player.getWorld().playSound(null, player.getBlockPos(), SoundRegistry.SWALLOW_WATER_AFTER_DRINK,
                        SoundCategory.PLAYERS, 0.5f, player.getWorld().random.nextFloat() * 0.1f + 0.9f);
            }
        }

        // Check if the player has drank water from cauldron and add a cooldown
        if (hasDrankFromCauldron) {
            ++this.drankFromCauldronTickTimer;
            if (this.drankFromCauldronTickTimer >= (ConfigRegistry.CONFIG.drank_from_cauldron_cooldown * 20)) {
                this.drankFromCauldronTickTimer = 0;
                this.hasDrankFromCauldron = false;
                player.getWorld().playSound(null, player.getBlockPos(), SoundRegistry.SWALLOW_WATER_AFTER_DRINK,
                        SoundCategory.PLAYERS, 0.5f, player.getWorld().random.nextFloat() * 0.1f + 0.9f);
            }
        }

        // Check for hot biome ticks
        if (biomeTicks >= 100) {
            isInHotBiomeForEnoughtTime = true;
        } else {
            isInHotBiomeForEnoughtTime = false;
        }

    }


    // Return if the player has drank from any water source recently
    public boolean hasDrankFromSomeSource() {
        if (this.hasDrankFromWaterSource) {
            return true;
        } else if (this.hasDrankFromRain) {
            return true;
        } else if (this.hasDrankFromCauldron) {
            return true;
        } else {
            return false;
        }
    }


    // Return if the mod is enabled
    public boolean isModEnabled() {
        return this.isModEnabled;
    }


    // Check if the thirst level is full
    public boolean isNotFull() {
        return this.thirstLevel < 20;
    }


    // Get the current thirst level
    public int getThirstLevel() {
        return this.thirstLevel;
    }


    // Set the current thirst level
    public void setThirstLevel(int thirstLevel) {
        this.thirstLevel = thirstLevel;
    }


    // Add dehydration based on a exhaustion float
    public void addDehydration(float dehydration) {
        this.dehydrationLevel = Math.min(this.dehydrationLevel + dehydration, 40.0F);
    }

    // Check if the biome ticks is at max value
    public boolean isBiomeTicksAtMaxValue() {
        return this.biomeTicks >= 200;
    }


    // Get if the player is enought time in a biome
    public boolean getIsInHotBiomeForEnoughtTime() {
        return this.isInHotBiomeForEnoughtTime;
    }


    // Set if the player is enought time in a biome
    public boolean setIsInHotBiomeForEnoughtTime(boolean value) {
        return this.isInHotBiomeForEnoughtTime = value;
    }


    // Read nbt
    public void readNbt(NbtCompound nbt) {
        this.thirstLevel = nbt.getInt("vanillathirst.thirst_level");
        this.dehydrationLevel = nbt.getFloat("vanillathirst.dehydration_level");
        this.dehydrationTickTimer = nbt.getInt("vanillathirst.dehydration_tick_timer");
    }


    // Write nbt
    public void writeNbt(NbtCompound nbt) {
        nbt.putInt("vanillathirst.thirst_level", this.thirstLevel);
        nbt.putFloat("vanillathirst.dehydration_level", this.dehydrationLevel);
        nbt.putInt("vanillathirst.dehydration_tick_time", this.dehydrationTickTimer);
    }
}
