package net.its0v3r.itsthirst.mixin;

import net.its0v3r.ItsThirstMain;
import net.its0v3r.itsthirst.access.ItemMaxCount;
import net.its0v3r.itsthirst.access.ThirstManagerAccess;
import net.its0v3r.itsthirst.registry.ConfigRegistry;
import net.its0v3r.itsthirst.registry.TagRegistry;
import net.its0v3r.itsthirst.thirst.ThirstManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin implements ItemMaxCount {

    @Final
    @Mutable
    @Shadow
    private int maxCount;


    @Override
    public void setMaxCount(int value) {
        this.maxCount = value;
    }


    // After eating or drinking an item
    @Inject(method = "finishUsing", at = @At(value = "HEAD"))
    private void vanillaThirst$addThirstIfItemIsHydrating(ItemStack stack, World world, LivingEntity livingEntity, CallbackInfoReturnable<ItemStack> cir) {
        if (stack.isFood() && livingEntity instanceof PlayerEntity player && !world.isClient()) {
            int thirst_value = 0;

            if (stack.isIn(TagRegistry.HYDRATING_DRINK))
                thirst_value = ConfigRegistry.CONFIG.hydrating_drink_value;
            if (stack.isIn(TagRegistry.HYDRATING_FOOD))
                thirst_value = ConfigRegistry.CONFIG.hydrating_food_value;
            if (stack.isIn(TagRegistry.HYDRATING_STEW))
                thirst_value = ConfigRegistry.CONFIG.hydrating_food_value;


            if (thirst_value > 0) {
                ((ThirstManagerAccess) player).getThirstManager().add(thirst_value);
            }
        }
    }


    // Check if the player can consume hydrating items when the hunger bar is full but the thirst bar isn't
    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/TypedActionResult;fail(Ljava/lang/Object;)Lnet/minecraft/util/TypedActionResult;", ordinal = 0, shift = At.Shift.BEFORE), cancellable = true)
    private void vanillaThirst$canEatIfThirstBarNotFull(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        ThirstManager thirstManager = ((ThirstManagerAccess) user).getThirstManager();
        ItemStack itemStack = user.getStackInHand(Hand.MAIN_HAND);

        if (ConfigRegistry.CONFIG.can_eat_if_thirst_bar_not_full && thirstManager.isNotFull()) {
            if (itemStack.isIn(TagRegistry.HYDRATING_FOOD)) {
                user.setCurrentHand(hand);
                cir.setReturnValue(TypedActionResult.consume(itemStack));
            }
            if (itemStack.isIn(TagRegistry.HYDRATING_DRINK)) {
                user.setCurrentHand(hand);
                cir.setReturnValue(TypedActionResult.consume(itemStack));
            }
            if (itemStack.isIn(TagRegistry.HYDRATING_STEW)) {
                user.setCurrentHand(hand);
                cir.setReturnValue(TypedActionResult.consume(itemStack));
            }
        }
    }
}