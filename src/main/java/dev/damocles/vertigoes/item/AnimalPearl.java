package dev.damocles.vertigoes.item;

import dev.damocles.vertigoes.datagen.PearlsConfig;
import dev.damocles.vertigoes.setup.Registration;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class AnimalPearl extends AbstractElementalPearl {
    public static final int defaultAnimalRequirement = 20;

    public AnimalPearl(Properties properties) {
        super(properties, null);
    }

    public static float inHotbarGetUndeadDamage(Player player, LivingEntity entity, float amount) {
        if(entity.getMobType() == MobType.UNDEAD) {
            for(int i=0; i<10; i++) {
                if(player.getInventory().getItem(i).is(Registration.ANIMAL_PEARL.get()))
                    return amount + (4 * 2.5F);
            }
            if(player.getInventory().getItem(Inventory.SLOT_OFFHAND).is(Registration.ANIMAL_PEARL.get()))
                return amount + (4 * 2.5F);
        }
        return amount;
    }

    public static void disablePearl(Player player) {
        if(PearlsConfig.animalCanBeDisabled.get())
            for(int i=0; i<player.getInventory().getContainerSize(); i++) {
                if(player.getInventory().getItem(i).is(Registration.ANIMAL_PEARL.get()))
                    player.getInventory().setItem(i, Registration.PRIMAL_PEARL.get().getDefaultInstance());
            }
    }

    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        tooltipComponents.add(Component.literal("Animal Life").withStyle(ChatFormatting.GRAY));
    }
}
