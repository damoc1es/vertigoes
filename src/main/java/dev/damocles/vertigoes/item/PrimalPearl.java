package dev.damocles.vertigoes.item;

import dev.damocles.vertigoes.setup.Registration;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
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

    public static void tryTransformToDeathPearl(Player player, Entity entity) {
        if(!(entity instanceof Villager))
            return;

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack item = player.getInventory().getItem(i);

            if (item.is(Registration.PRIMAL_PEARL.get())) {
                CompoundTag tags;

                if (item.getTag() != null) { // already has tags
                    tags = item.getTag();

                    if (tags.contains("vertigoes.deathprogress") && tags.getInt("vertigoes.deathprogress") >= 10) { // should transform
                        player.getInventory().removeItem(i, 1);
                        player.getInventory().setItem(i, Registration.DEATH_PEARL.get().getDefaultInstance());
                    } else { // shouldn't transform, just increase the progress
                        if (!tags.contains("vertigoes.deathprogress")) { // has tags but not the deathprogress one
                            tags.putInt("vertigoes.deathprogress", 0);
                        }
                        tags.putInt("vertigoes.deathprogress", tags.getInt("vertigoes.deathprogress") + 1);
                    }
                } else { // doesn't have any tag
                    tags = new CompoundTag();
                    tags.putInt("vertigoes.deathprogress", 1);
                }

                item.setTag(tags);
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

    // TODO: GENERALIZE TRANSFORMATION FUNCTION
    public static void tryTransformToAnimalPearl(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack item = player.getInventory().getItem(i);

            if (item.is(Registration.PRIMAL_PEARL.get())) {
                CompoundTag tags;

                if (item.getTag() != null) { // already has tags
                    tags = item.getTag();

                    if (tags.contains("vertigoes.animalprogress") && tags.getInt("vertigoes.animalprogress") >= 10) { // should transform
                        player.getInventory().removeItem(i, 1);
                        player.getInventory().setItem(i, Registration.ANIMAL_PEARL.get().getDefaultInstance());
                    } else { // shouldn't transform, just increase the progress
                        if (!tags.contains("vertigoes.animalprogress")) { // has tags but not the deathprogress one
                            tags.putInt("vertigoes.animalprogress", 0);
                        }
                        tags.putInt("vertigoes.animalprogress", tags.getInt("vertigoes.animalprogress") + 1);
                    }
                } else { // doesn't have any tag
                    tags = new CompoundTag();
                    tags.putInt("vertigoes.animalprogress", 1);
                }

                item.setTag(tags);
            }
        }
    }

    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        tooltipComponents.add(new TextComponent("Immaculate").withStyle(ChatFormatting.GRAY));
    }
}
