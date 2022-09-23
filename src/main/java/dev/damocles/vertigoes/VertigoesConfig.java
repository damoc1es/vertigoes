package dev.damocles.vertigoes;

import dev.damocles.vertigoes.datagen.GlassHeartConfig;
import dev.damocles.vertigoes.datagen.PearlsConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class VertigoesConfig {
    public static void setup() {
        ForgeConfigSpec.Builder serverBuilder = new ForgeConfigSpec.Builder();
        PearlsConfig.registerServer(serverBuilder);
        GlassHeartConfig.registerServer(serverBuilder);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, serverBuilder.build());
    }
}
