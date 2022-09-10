package dev.damocles.vertigoes.item;

import dev.damocles.vertigoes.datagen.PearlsConfig;
import dev.damocles.vertigoes.setup.Registration;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

import static dev.damocles.vertigoes.setup.Registration.DEATH_PEARL;

public class DeathPearl extends AbstractElementalPearl {
    public DeathPearl(Properties properties) {
        super(properties, null);
    }

    public static void disablePearl(Player player) {
        if(PearlsConfig.deathCanBeDisabled.get())
            for(int i=0; i<player.getInventory().getContainerSize(); i++) {
                if(player.getInventory().getItem(i).is(DEATH_PEARL.get()))
                    player.getInventory().setItem(i, Registration.PRIMAL_PEARL.get().getDefaultInstance());
            }
    }

    public static boolean tryCancelUndeadAttackUponPlayer(Player player, DamageSource dmgSource) {
        if(dmgSource.getEntity() == null || !(dmgSource.getEntity() instanceof LivingEntity))
            return false;
        return ((LivingEntity) dmgSource.getEntity()).getMobType() == MobType.UNDEAD && player.getInventory().getSelected().is(DEATH_PEARL.get());
    }
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        tooltipComponents.add(new TextComponent("Anti-life").withStyle(ChatFormatting.GRAY));
    }
}
