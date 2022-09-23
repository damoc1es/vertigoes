package dev.damocles.vertigoes.datagen;

import net.minecraftforge.common.ForgeConfigSpec;

public class GlassHeartConfig {
    public static ForgeConfigSpec.IntValue glassHeartRadius;
    public static void registerServer(ForgeConfigSpec.Builder builder) {
        builder.comment("Settings for the Glass Heart").push("glass_heart");

        glassHeartRadius = builder
                .comment("The Area of Effect of the Glass Heart (radius)")
                .defineInRange("glass_heart_radius", 4, 0, 30);

        builder.pop();
    }
}
