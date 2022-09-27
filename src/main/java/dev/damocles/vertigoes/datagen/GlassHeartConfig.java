package dev.damocles.vertigoes.datagen;

import dev.damocles.vertigoes.block.GlassHeart;
import net.minecraftforge.common.ForgeConfigSpec;

public class GlassHeartConfig {
    public static ForgeConfigSpec.IntValue glassHeartRadius;

    public static int getGlassHeartRadius() {
        if(glassHeartRadius.get() == 0)
            return GlassHeart.defaultGlassHeartRadius;
        return glassHeartRadius.get();
    }

    public static void registerServer(ForgeConfigSpec.Builder builder) {
        builder.comment("Settings for the Glass Heart").push("glass_heart");

        glassHeartRadius = builder
                .comment("The Area of Effect of the Glass Heart (radius). 0 to use the default.")
                .defineInRange("glass_heart_radius", 0, 0, 30);

        builder.pop();
    }
}
