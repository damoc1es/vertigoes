package dev.damocles.vertigoes.setup;

import dev.damocles.vertigoes.Vertigoes;
import dev.damocles.vertigoes.item.AnimalPearl;
import dev.damocles.vertigoes.item.AquaticPearl;
import dev.damocles.vertigoes.item.DeathPearl;
import dev.damocles.vertigoes.item.PrimalPearl;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.util.UUID;

import static dev.damocles.vertigoes.setup.Registration.UNSTOPPABLE_FORCE;

@Mod.EventBusSubscriber(modid = Vertigoes.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class VertigoesEventHandler {
    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if(event.getEntity() instanceof Player) {
            PrimalPearl.tryTransformToPlantPearl((Player) event.getEntity(), event.getSource());
        }
    }

    @SubscribeEvent
    public static void onPlayerKill(LivingDeathEvent event) {
        if(event.getSource().getEntity() instanceof Player) {
            MobCategory killedCategory = event.getEntityLiving().getType().getCategory();
            if(event.getEntityLiving() instanceof Villager || killedCategory == MobCategory.CREATURE)
                AnimalPearl.disablePearl((Player)event.getSource().getEntity());

            if(killedCategory == MobCategory.WATER_CREATURE || killedCategory == MobCategory.WATER_AMBIENT)
                AquaticPearl.disablePearl((Player)event.getSource().getEntity());

            PrimalPearl.tryTransformToDeathPearl((Player) event.getSource().getEntity(), event.getEntity());
            PrimalPearl.tryTransformToAquaticPearl((Player) event.getSource().getEntity(), event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onPlayerDamaged(LivingDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            if(DeathPearl.tryCancelUndeadAttackUponPlayer((Player) event.getEntity(), event.getSource()))
                event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerCausingDamage(LivingDamageEvent event) {
        if(event.getSource().getEntity() instanceof Player) {
            float bonus = AnimalPearl.inHotbarGetUndeadDamage((Player)event.getSource().getEntity(), event.getEntityLiving(), event.getAmount());
            if(bonus != event.getAmount())
                event.setAmount(bonus);
        }
    }

    @SubscribeEvent
    public static void onPunchingWithForce(LivingKnockBackEvent event) {
        LivingEntity damaged = event.getEntityLiving();
        if(damaged.getLastDamageSource() != null && damaged.getLastDamageSource().getEntity() instanceof LivingEntity source) {
            if(source.getMainHandItem().is(UNSTOPPABLE_FORCE.get()) && event.getStrength() < 2F) {
                event.setStrength(2F); // equivalent of a Knockback IV enchantment
            }
        }
    }

    @SubscribeEvent
    public static void onAnimalsBreeding(BabyEntitySpawnEvent event) {
        if(event.getCausedByPlayer() != null) {
            PrimalPearl.tryTransformToAnimalPearl(event.getCausedByPlayer());
        }
    }

    @SubscribeEvent
    public static void onVillagersConversion(LivingConversionEvent.Post event) {
        if (event.getEntityLiving() instanceof ZombieVillager && event.getOutcome() instanceof Villager) {
            try {
                UUID playerID = ObfuscationReflectionHelper.getPrivateValue(ZombieVillager.class, (ZombieVillager) event.getEntityLiving(), "conversionStarter");
                if (playerID != null) {
                    Player player = event.getEntityLiving().getLevel().getPlayerByUUID(playerID);
                    if (player != null)
                        DeathPearl.disablePearl(player);
                }
            } catch(ObfuscationReflectionHelper.UnableToAccessFieldException ignored) {}
        }
    }
}
