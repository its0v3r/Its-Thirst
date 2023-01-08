package net.its0v3r.itsthirst.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "itsthirst")
@Config.Gui.Background("minecraft:textures/block/stone.png")
public class VanillaThirstConfig implements ConfigData {

    // Damage from thirst
    @ConfigEntry.Category("general_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public float damage_from_thirst = 1.0F;

    // Thirst speed
    @ConfigEntry.Category("general_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public float general_thirst_drain_speed = 2.25F;

    // Drank from water source cooldown
    @ConfigEntry.Category("general_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public int drank_from_water_source_cooldown = 1;

    // Drank from rain cooldown
    @ConfigEntry.Category("general_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public int drank_from_rain_cooldown = 4;

    // Only regen if health and hunger is full
    @ConfigEntry.Category("general_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public boolean only_regen_health_if_hunger_and_thirst_full = false;

    // Bad water
    @ConfigEntry.Category("bad_water_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public boolean bad_water = true;

    // Bad water
    @ConfigEntry.Category("bad_water_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public boolean bad_water_applies_to_rain = false;

    // Bad water chance from water source
    @ConfigEntry.Category("bad_water_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public float bad_water_from_water_source_chance = 0.75f;

    // Bad water chance from rain
    @ConfigEntry.Category("bad_water_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public float bad_water_from_rain_chance = 0.01f;

    // Bad water thirst effect duration
    @ConfigEntry.Category("bad_water_thirst_effect_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public int bad_water_thirst_effect_duration = 30;

    // Bad water drain thirst factor
    @ConfigEntry.Category("bad_water_thirst_effect_settings")
    @ConfigEntry.Gui.Tooltip(count = 4)
    public float bad_water_drain_thirst_factor_factor = 0.05f;

    // Thirst apply haste
    @ConfigEntry.Category("bad_water_thirst_effect_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public boolean should_thirst_apply_haste_effect = true;

    // Minimum thirst level to apply hates
    @ConfigEntry.Category("bad_water_thirst_effect_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
    public int minimum_thirst_level_to_apply_haste_effect = 4;

    // Thirst apply mining fatigue
    @ConfigEntry.Category("bad_water_thirst_effect_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public boolean should_thirst_apply_mining_fatigue_effect = true;

    // Minimum thirst level to apply mining fatigue
    @ConfigEntry.Category("bad_water_thirst_effect_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
    public int minimum_thirst_level_to_apply_mining_fatigue_effect = 2;

    // Can stack bottle
    @ConfigEntry.Category("stack_settings")
    @ConfigEntry.Gui.Tooltip(count = 4)
    public boolean can_stack_glass = false;

    // How many bottles to stack
    @ConfigEntry.Category("stack_settings")
    @ConfigEntry.Gui.Tooltip(count = 4)
    @ConfigEntry.BoundedDiscrete(min = 1, max = 64)
    public int can_stack_glass_factor = 1;

    // Can stack bottle
    @ConfigEntry.Category("stack_settings")
    @ConfigEntry.Gui.Tooltip(count = 5)
    public boolean can_stack_potion = false;

    // How many bottles to stack
    @ConfigEntry.Category("stack_settings")
    @ConfigEntry.Gui.Tooltip(count = 5)
    @ConfigEntry.BoundedDiscrete(min = 1, max = 64)
    public int can_stack_potion_factor = 1;

    // Can eat if the the thirst is not full
    @ConfigEntry.Category("food_and_drink_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public boolean can_eat_if_thirst_bar_not_full = true;

    // Hydrating food factor
    @ConfigEntry.Category("food_and_drink_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public int hydrating_food_value = 2;

    // Hydrating drink factor
    @ConfigEntry.Category("food_and_drink_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public int hydrating_drink_value = 6;

    // Hydrating stew factor
    @ConfigEntry.Category("food_and_drink_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public int hydrating_stew_value = 2;

    // Harder nether
    @ConfigEntry.Category("nether_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public boolean nether_drains_more_thirst = true;

    // Harder nether factor
    @ConfigEntry.Category("nether_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public float nether_drains_more_thirst_value = 1.10F;

    // Thirst bar texture
    @ConfigEntry.Category("nether_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    @ConfigEntry.BoundedDiscrete(min = 1, max = 3)
    public int nether_thirst_bar_texture = 1;
}
