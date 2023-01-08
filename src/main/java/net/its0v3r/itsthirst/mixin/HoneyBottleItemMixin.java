package net.its0v3r.itsthirst.mixin;

import net.its0v3r.itsthirst.access.ThirstManagerAccess;
import net.its0v3r.itsthirst.registry.ConfigRegistry;
import net.its0v3r.itsthirst.registry.TagRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HoneyBottleItem.class)
public abstract class HoneyBottleItemMixin {

    // Add thirstLevel after drinking a honey bottle
    @Inject(method = "finishUsing", at = @At(value = "HEAD"))
    public void vanillaThirst$hydratingHoneyBottle(ItemStack stack, World world, LivingEntity livingEntity, CallbackInfoReturnable<ItemStack> cir) {
        if (livingEntity instanceof PlayerEntity player && !world.isClient()) {
            int thirst_value = 0;

            if (stack.isIn(TagRegistry.HYDRATING_DRINK))
                thirst_value = ConfigRegistry.CONFIG.hydrating_drink_value;

            if (thirst_value > 0) {
                ((ThirstManagerAccess) player).getThirstManager().add(thirst_value);
            }
        }
    }
}
