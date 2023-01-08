package net.its0v3r.itsthirst.registry;

import net.minecraft.entity.damage.DamageSource;

public class DamageSourceRegistry {
    public static final DamageSource THIRST = new DamageSource("thirst") {

        @Override
        public boolean bypassesArmor() {
            return true;
        }

        @Override
        public boolean isUnblockable() {
            return true;
        }

        @Override
        public float getExhaustion() {
            return 0F;
        }
    };
}
