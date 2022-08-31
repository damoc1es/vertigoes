package dev.damocles.vertigoes.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class EnderMyosotisItem extends Item {

    public EnderMyosotisItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        if(stack.hasTag() && stack.getTag() != null) {
            CompoundTag currentTags = stack.getTag();
            if(currentTags.contains("vertigoes.wasPlaced") && currentTags.getBoolean("vertigoes.wasPlaced")) { //
                int coordX = currentTags.getInt("vertigoes.coordX");
                int coordY = currentTags.getInt("vertigoes.coordY");
                int coordZ = currentTags.getInt("vertigoes.coordZ");
                String dim = currentTags.getString("vertigoes.dim");
                if(dim.contains(":"))
                    dim = dim.substring(dim.indexOf(":")+1);

                tooltipComponents.add(new TextComponent(String.format("RIGHT-CLICK to teleport to (x=%d, y=%d, z=%d)", coordX, coordY, coordZ)).withStyle(ChatFormatting.GRAY));
                tooltipComponents.add(new TextComponent(String.format("IF you're in %s", dim)).withStyle(ChatFormatting.GRAY));
            }
        } else {
            tooltipComponents.add(new TextComponent("Forget me not..").withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        CompoundTag currentTags = itemstack.getTag();
        if(currentTags != null) {
            if (!level.isClientSide) {
                String dim = currentTags.getString("vertigoes.dim");

                if(player.getLevel().dimension().location().toString().equals(dim)) {
                    int coordX = currentTags.getInt("vertigoes.coordX");
                    int coordY = currentTags.getInt("vertigoes.coordY");
                    int coordZ = currentTags.getInt("vertigoes.coordZ");
                    player.teleportTo(coordX, coordY, coordZ);
                }
            }

            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        }
        return InteractionResultHolder.fail(itemstack);
    }
}
