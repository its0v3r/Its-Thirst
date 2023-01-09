package net.its0v3r.itsthirst.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.its0v3r.ItsThirstMain;
import net.its0v3r.itsthirst.access.ThirstManagerAccess;
import net.its0v3r.itsthirst.identifier.HudTexturesIdentifiers;
import net.its0v3r.itsthirst.registry.ConfigRegistry;
import net.its0v3r.itsthirst.registry.EffectRegistry;
import net.its0v3r.itsthirst.thirst.ThirstManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class ThirstHud {
    public static void renderThirstHud(MatrixStack matrixStack, MinecraftClient client, PlayerEntity playerEntity, int ticks) {

        // Get the client width and height
        if (playerEntity != null && !playerEntity.isCreative() && !playerEntity.isSpectator()) {
            int width = client.getWindow().getScaledWidth() / 2;
            int height = client.getWindow().getScaledHeight();
            int bounceFactor = 0;

            // Defining the texture
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.setShaderTexture(0, HudTexturesIdentifiers.THIRST_ICONS);

            // Get the ThirstManager for the playerEntity
            ThirstManager thirstManager = ((ThirstManagerAccess) playerEntity).getThirstManager();

            // Get the thirstValue
            int thirstValue = thirstManager.getThirstLevel();

            // If the player currently is in Nether
            int netherXFactor = 0;
            int netherYFactor = 0;
            if (playerEntity.world.getRegistryKey() == World.NETHER && ConfigRegistry.CONFIG.nether_drains_more_thirst) {
                if (ConfigRegistry.CONFIG.nether_thirst_bar_texture == 1) {
                    netherYFactor = 9;
                } else if (ConfigRegistry.CONFIG.nether_thirst_bar_texture == 2) {
                    netherXFactor = 36;
                }
            }

            // If the player currently has the thirst effect
            int thirstEffectFactor = 0;
            if (playerEntity.hasStatusEffect(EffectRegistry.THIRST)) {
                thirstEffectFactor = 18;
                netherXFactor = 0;
            }


            // Create the Thirst Bar
            // Empty Thirst
            for (int i = 0; i < 10; i++) {
                bounceFactor = getBounceFactor(playerEntity, ticks, thirstManager);
                DrawableHelper.drawTexture(matrixStack,
                        (width + 82 - (i * 9) + i) + ConfigRegistry.CONFIG.hud_x,
                        (height - 49 + bounceFactor) + ConfigRegistry.CONFIG.hud_y,
                        0 + netherYFactor,
                        0,
                        9,
                        9,
                        256,
                        256);
            }

            // Half Thirst
            for (int i = 0; i < 20; i++) {
                if (thirstValue != 0) {
                    if (((thirstValue + 1) / 2) > i) {
                        bounceFactor = getBounceFactor(playerEntity, ticks, thirstManager);
                        DrawableHelper.drawTexture(matrixStack,
                                (width + 82 - (i * 9) + i) + ConfigRegistry.CONFIG.hud_x,
                                (height - 49 + bounceFactor)+ ConfigRegistry.CONFIG.hud_y,
                                9 + thirstEffectFactor + netherXFactor,
                                9,
                                9,
                                9,
                                256,
                                256);
                    } else {
                        break;
                    }
                }
            }

            // Full Thirst
            for (int i = 0; i < 20; i++) {
                if (thirstValue != 0) {
                    if ((thirstValue / 2) > i) {
                        bounceFactor = getBounceFactor(playerEntity, ticks, thirstManager);
                        DrawableHelper.drawTexture(matrixStack,
                                (width + 82 - (i * 9) + i) + ConfigRegistry.CONFIG.hud_x,
                                (height - 49 + bounceFactor) + ConfigRegistry.CONFIG.hud_y,
                                0 + thirstEffectFactor + netherXFactor,
                                9,
                                9,
                                9,
                                256,
                                256);
                    } else {
                        break;
                    }
                }
            }
            RenderSystem.setShaderTexture(0, DrawableHelper.GUI_ICONS_TEXTURE);
        }
    }

    // Bounce factor
    private static int getBounceFactor(PlayerEntity playerEntity, int ticks, ThirstManager thirstManager) {
        if (thirstManager.dehydrationLevel >= 4.0F && ticks % (thirstManager.thirstLevel * 3 + 1) == 0) {
            return playerEntity.world.random.nextInt(3) - 1;
        } else if (ticks % (thirstManager.thirstLevel * 8 + 3) == 0) {
            return playerEntity.world.random.nextInt(5) - 1;
        } else {
            return 0;
        }
    }
}
