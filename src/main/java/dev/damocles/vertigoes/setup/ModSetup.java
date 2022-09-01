package dev.damocles.vertigoes.setup;

import dev.damocles.vertigoes.item.PrimalPearl;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.NotNull;
import net.minecraft.world.entity.player.Player;

import static dev.damocles.vertigoes.setup.Registration.MYOSOTIS;

public class ModSetup {

    public static final String TAB_NAME = "vertigoes";

    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(TAB_NAME) {
        @Override
        public @NotNull ItemStack makeIcon() { return new ItemStack(MYOSOTIS.get()); }
    };

    public static void setup() {
        IEventBus bus = MinecraftForge.EVENT_BUS;
        bus.addListener((LivingDeathEvent e) -> {
            if(e.getSource().getEntity() instanceof Player) {
                PrimalPearl.tryTransformToDeathPearl((Player) e.getSource().getEntity(), e.getEntity());
            }
            if(e.getEntity() instanceof Player) {
                PrimalPearl.tryTransformToPlantPearl((Player) e.getEntity(), e.getSource());
            }
        });
    }

    public static void init(FMLCommonSetupEvent event) {

    }
}
