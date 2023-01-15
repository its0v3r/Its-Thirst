package net.its0v3r.itsthirst.mixin;

import net.its0v3r.itsthirst.access.LeveledCauldronBlockMixinAccess;
import net.minecraft.block.*;

import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(LeveledCauldronBlock.class)
public abstract class LeveledCauldronBlockMixin extends AbstractCauldronBlock implements LeveledCauldronBlockMixinAccess {

    // Shadow the LEVEL property
    @Final
    @Shadow
    public static IntProperty LEVEL;


    // Default constructor
    public LeveledCauldronBlockMixin(Settings settings, Map<Item, CauldronBehavior> behaviorMap) {
        super(settings, behaviorMap);
    }


    // Create the SIP_LEVEL property
    private static final IntProperty SIP_LEVEL;
    static {
        SIP_LEVEL = IntProperty.of("sip_level", 0, 5);
    }


    // Method from the Duck Interface to update the Sip Level
    @Override
    public void updateSipLevel(BlockState state, World world, BlockPos blockPos) {
        ItsThirst$checkForSipLevelDecrement(state, world, blockPos);
    }


    // Add the SIP_LEVEL propertiy to the builder
    @Inject(method = "appendProperties", at = @At(value = "TAIL"))
    private void ItsThirs$appendSipLevelPropertieToPropertyBuilder(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(SIP_LEVEL);
    }


    // Add the SIP_LEVEL to the constructor
    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/LeveledCauldronBlock;setDefaultState(Lnet/minecraft/block/BlockState;)V"), index = 0)
    private BlockState ItsThirst$appendSipLevelPropertieToConstructor(BlockState original) {
        return original.with(SIP_LEVEL, 0);
    }


    // Set the SIP_LEVEL to 0 when the fluid level is lowered
    @Inject(method = "decrementFluidLevel", at = @At(value = "TAIL"))
    private static void ItsThirst$setSipLevelToZeroAfterLevelLowered(BlockState state, World world, BlockPos pos, CallbackInfo ci) {
        BlockState updatedState = state.with(SIP_LEVEL, 0);
        world.setBlockState(pos, updatedState);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(updatedState));
    }


    // Decrement the SipLevel by 1 or set it to 0 after reaches 5
    private void ItsThirst$checkForSipLevelDecrement(BlockState state, World world, BlockPos blockPos) {
        // Get the current SIP_LEVEL and ADD by 1
        int sipLevel = state.get(SIP_LEVEL);
        sipLevel++;

        // Create a variable for the updated state
        BlockState updatedState;

        // If the SIP_LEVEL is lower than the max (5), set the new SIP_LEVEL to the +1
        if (sipLevel < 5) {
            updatedState = state.with(SIP_LEVEL, sipLevel);
        }
        // If the SIP_LEVEL already reached the max (5), set it back to zero and remove 1 LEVEL from the cauldron
        else {
            sipLevel = 0;
            updatedState = state.with(SIP_LEVEL, sipLevel);

            int i = state.get(LEVEL) - 1;
            updatedState = i == 0 ? Blocks.CAULDRON.getDefaultState() : updatedState.with(LEVEL, i);
        }

        // Update de blockState with the new state
        world.setBlockState(blockPos, updatedState);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(updatedState));
    }
}
