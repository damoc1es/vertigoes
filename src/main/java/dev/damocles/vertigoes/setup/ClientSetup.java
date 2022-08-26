package dev.damocles.vertigoes.setup;

import dev.damocles.vertigoes.Vertigoes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Vertigoes.MODID, value= Dist.CLIENT, bus=Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    public static void init(FMLClientSetupEvent event) {
        RenderLayerRegistration.init();
    }
}
