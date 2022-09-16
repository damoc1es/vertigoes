package dev.damocles.vertigoes;

import com.mojang.logging.LogUtils;
import dev.damocles.vertigoes.setup.ClientSetup; //
import dev.damocles.vertigoes.setup.ModSetup; //
import dev.damocles.vertigoes.setup.Registration; //
import net.minecraftforge.api.distmarker.Dist; //
import net.minecraftforge.eventbus.api.IEventBus; //
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Vertigoes.MODID)
public class Vertigoes {
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MODID = "vertigoes";

    public Vertigoes() {
        Registration.init();
        VertigoesConfig.setup();

        // Register the setup method for modloading
        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
        modbus.addListener(ModSetup::init);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modbus.addListener(ClientSetup::init));
    }
}
