package com.fpsoverlay.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class FPSConfig {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    // Position
    public static final ForgeConfigSpec.IntValue POS_X;
    public static final ForgeConfigSpec.IntValue POS_Y;

    // Text formatting
    public static final ForgeConfigSpec.IntValue COLOR_R;
    public static final ForgeConfigSpec.IntValue COLOR_G;
    public static final ForgeConfigSpec.IntValue COLOR_B;
    public static final ForgeConfigSpec.IntValue COLOR_A;

    public static final ForgeConfigSpec.BooleanValue BOLD;
    public static final ForgeConfigSpec.BooleanValue ITALIC;
    public static final ForgeConfigSpec.BooleanValue STRIKETHROUGH;
    public static final ForgeConfigSpec.BooleanValue UNDERLINE;
    public static final ForgeConfigSpec.BooleanValue OBFUSCATED;

    // Scale
    public static final ForgeConfigSpec.DoubleValue SCALE;

    // Bracket / prefix / suffix symbols
    public static final ForgeConfigSpec.ConfigValue<String> PREFIX;
    public static final ForgeConfigSpec.ConfigValue<String> SUFFIX;
    public static final ForgeConfigSpec.ConfigValue<String> LABEL;

    // Shadow
    public static final ForgeConfigSpec.BooleanValue SHADOW;

    // Visibility
    public static final ForgeConfigSpec.BooleanValue VISIBLE;

    static {
        BUILDER.push("position");
        POS_X = BUILDER.comment("X position of the overlay in pixels").defineInRange("posX", 2, 0, 10000);
        POS_Y = BUILDER.comment("Y position of the overlay in pixels").defineInRange("posY", 2, 0, 10000);
        BUILDER.pop();

        BUILDER.push("color");
        COLOR_R = BUILDER.comment("Red channel (0-255)").defineInRange("r", 255, 0, 255);
        COLOR_G = BUILDER.comment("Green channel (0-255)").defineInRange("g", 255, 0, 255);
        COLOR_B = BUILDER.comment("Blue channel (0-255)").defineInRange("b", 255, 0, 255);
        COLOR_A = BUILDER.comment("Alpha channel (0-255)").defineInRange("a", 255, 0, 255);
        BUILDER.pop();

        BUILDER.push("style");
        BOLD          = BUILDER.comment("Bold text").define("bold", false);
        ITALIC        = BUILDER.comment("Italic text").define("italic", false);
        STRIKETHROUGH = BUILDER.comment("Strikethrough text").define("strikethrough", false);
        UNDERLINE     = BUILDER.comment("Underline text").define("underline", false);
        OBFUSCATED    = BUILDER.comment("Obfuscated (matrix) text").define("obfuscated", false);
        SHADOW        = BUILDER.comment("Text drop shadow").define("shadow", true);
        SCALE         = BUILDER.comment("Text scale multiplier").defineInRange("scale", 1.0, 0.25, 5.0);
        BUILDER.pop();

        BUILDER.push("format");
        PREFIX = BUILDER.comment("Characters shown before FPS value (e.g. '[ FPS: ')").define("prefix", "[ FPS: ");
        SUFFIX = BUILDER.comment("Characters shown after FPS value (e.g. ' ]')").define("suffix", " ]");
        LABEL  = BUILDER.comment("Alias for the FPS label — leave empty to hide label").define("label", "");
        BUILDER.pop();

        BUILDER.push("visibility");
        VISIBLE = BUILDER.comment("Whether the overlay is visible").define("visible", true);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }

    public static void init() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, SPEC, "fpsoverlay-client.toml");
    }

    // ---- Helper: assemble ARGB int from config values ----
    public static int getARGB() {
        int a = COLOR_A.get();
        int r = COLOR_R.get();
        int g = COLOR_G.get();
        int b = COLOR_B.get();
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}
