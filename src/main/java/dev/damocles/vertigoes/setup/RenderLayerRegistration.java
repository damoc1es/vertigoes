package dev.damocles.vertigoes.setup;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;

public class RenderLayerRegistration {
    public static void init() {
        RenderType cutout = RenderType.cutout();
        ItemBlockRenderTypes.setRenderLayer(Registration.MYOSOTIS.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(Registration.POTTED_MYOSOTIS.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(Registration.GLASS_HEART.get(), cutout);
    }
}
