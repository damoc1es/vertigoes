package dev.damocles.vertigoes.item;

import dev.damocles.vertigoes.datagen.PearlsConfig;
import dev.damocles.vertigoes.setup.Registration;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

import static net.minecraftforge.common.Tags.Biomes.IS_SWAMP;

public class PrimalPearl extends Item {
    public PrimalPearl(Properties properties) {
        super(properties);
    }

    public static void tryTransformToGeneralPearl(Player player, String progressTag, int requirement, ItemStack replacement) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack item = player.getInventory().getItem(i);

            if (item.is(Registration.PRIMAL_PEARL.get())) {
                CompoundTag tags;

                if (item.getTag() != null) { // has tags
                    tags = item.getTag();

                    if(tags.contains(progressTag)) { // has the progress tag
                        tags.putInt(progressTag, tags.getInt(progressTag) + 1);

                        if(tags.getInt(progressTag) >= requirement) { // requirement met
                            player.getInventory().setItem(i, replacement);
                        } else item.setTag(tags);
                    } else {
                        tags.putInt(progressTag, 1);
                        item.setTag(tags);
                    }

                } else { // doesn't have any tag
                    tags = new CompoundTag();
                    tags.putInt(progressTag, 1);
                    item.setTag(tags);
                }
            }
        }
    }

    public static void tryTransformToPlantPearl(Player player, DamageSource damage) {
        // if player died in swamp while drowning
        if(player.getLevel().getBiome(new BlockPos(player.getX(), player.getY(), player.getZ())).is(IS_SWAMP) && damage.getMsgId().equals("drown")) {
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack item = player.getInventory().getItem(i);
                if (item.is(Registration.PRIMAL_PEARL.get())) {
                    player.getInventory().removeItem(i, 1);
                    player.getInventory().setItem(i, Registration.PLANT_PEARL.get().getDefaultInstance());
                }
            }
        }
    }

    public static void tryTransformToAnimalPearl(Player player) {
        tryTransformToGeneralPearl(player, "vertigoes.animalprogress", PearlsConfig.getAnimalRequirement(), Registration.ANIMAL_PEARL.get().getDefaultInstance());
    }

    public static void tryTransformToDeathPearl(Player player, Entity entity) {
        if(entity instanceof Villager) {
            tryTransformToGeneralPearl(player, "vertigoes.deathprogress", PearlsConfig.getDeathRequirement(), Registration.DEATH_PEARL.get().getDefaultInstance());
        }
    }

    public static void tryTransformToAquaticPearl(Player player, Entity entity) {
        if(entity instanceof Drowned) {
            tryTransformToGeneralPearl(player, "vertigoes.aquaticprogress", PearlsConfig.getAquaticRequirement(), Registration.AQUATIC_PEARL.get().getDefaultInstance());
        }
    }

    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        tooltipComponents.add(new TextComponent("Immaculate").withStyle(ChatFormatting.GRAY));

        if(stack.getTag() != null) {
            CompoundTag tags = stack.getTag();
            if(tags.contains("vertigoes.animalprogress"))
                tooltipComponents.add(new TextComponent(String.format("%d / %d Bred animals", tags.getInt("vertigoes.animalprogress"), PearlsConfig.getAnimalRequirement())).withStyle(ChatFormatting.RED));

            if(tags.contains("vertigoes.deathprogress"))
                tooltipComponents.add(new TextComponent(String.format("%d / %d Villagers killed", tags.getInt("vertigoes.deathprogress"), PearlsConfig.getDeathRequirement())).withStyle(ChatFormatting.DARK_GRAY));

            if(tags.contains("vertigoes.aquaticprogress"))
                tooltipComponents.add(new TextComponent(String.format("%d / %d Drowned killed", tags.getInt("vertigoes.aquaticprogress"), PearlsConfig.getAquaticRequirement())).withStyle(ChatFormatting.DARK_AQUA));
        }
    }
}
