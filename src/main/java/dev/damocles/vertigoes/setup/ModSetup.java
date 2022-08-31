package dev.damocles.vertigoes.setup;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.NotNull;

import static dev.damocles.vertigoes.setup.Registration.MYOSOTIS;

public class ModSetup {

    public static final String TAB_NAME = "vertigoes";

    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(TAB_NAME) {
        @Override
        public @NotNull ItemStack makeIcon() { return new ItemStack(MYOSOTIS.get()); }
    };

    public static void init(FMLCommonSetupEvent event) {

    }
}
