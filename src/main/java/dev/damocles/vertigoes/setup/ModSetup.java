package dev.damocles.vertigoes.setup;

import dev.damocles.vertigoes.item.AnimalPearl;
import dev.damocles.vertigoes.item.DeathPearl;
import dev.damocles.vertigoes.item.PrimalPearl;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.event.entity.living.LivingConversionEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.jetbrains.annotations.NotNull;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

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
                if(e.getEntityLiving() instanceof Villager || e.getEntityLiving().getType().getCategory() == MobCategory.CREATURE)
                    AnimalPearl.disablePearl((Player)e.getSource().getEntity());

                PrimalPearl.tryTransformToDeathPearl((Player) e.getSource().getEntity(), e.getEntity());
            }
            if(e.getEntity() instanceof Player) {
                PrimalPearl.tryTransformToPlantPearl((Player) e.getEntity(), e.getSource());
            }
        });

        bus.addListener((BabyEntitySpawnEvent e) -> {
           if(e.getCausedByPlayer() != null) {
               PrimalPearl.tryTransformToAnimalPearl(e.getCausedByPlayer());
           }
        });

        bus.addListener((LivingDamageEvent e) -> {
            if(e.getSource().getEntity() instanceof Player) {
                float bonus = AnimalPearl.inHotbarGetUndeadDamage((Player)e.getSource().getEntity(), e.getEntityLiving(), e.getAmount());
                if(bonus != e.getAmount())
                    e.setAmount(bonus);
            }
            if(e.getEntity() instanceof Player) {
                if(DeathPearl.tryCancelUndeadAttackUponPlayer((Player) e.getEntity(), e.getSource()))
                    e.setCanceled(true);
            }
        });

        bus.addListener((LivingConversionEvent.Post e) -> {
            if (e.getEntityLiving() instanceof ZombieVillager && e.getOutcome() instanceof Villager) {
                UUID playerID = ObfuscationReflectionHelper.getPrivateValue(ZombieVillager.class, (ZombieVillager)e.getEntityLiving(), "conversionStarter");
                if(playerID != null) {
                    Player player = e.getEntityLiving().getLevel().getPlayerByUUID(playerID);
                    if(player != null)
                        DeathPearl.disablePearl(player);
                }
            }
        });
    }

    public static void init(FMLCommonSetupEvent event) {

    }
}
