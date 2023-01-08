package net.its0v3r.itsthirst.effect;

import net.its0v3r.itsthirst.access.ThirstManagerAccess;
import net.its0v3r.itsthirst.registry.ConfigRegistry;
import net.its0v3r.itsthirst.thirst.ThirstManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class ThirstEffect extends StatusEffect {

    // Constructor
    public ThirstEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }


    // Thirst effect consequence
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity playerEntity) {
            ThirstManager thirstManager = ((ThirstManagerAccess) playerEntity).getThirstManager();
            thirstManager.addDehydration(ConfigRegistry.CONFIG.bad_water_drain_thirst_factor_factor * (float) (amplifier + 1));
        }
    }


    // Can apply the effect
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
