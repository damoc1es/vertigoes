package dev.damocles.vertigoes.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
                double coordX = currentTags.getDouble("vertigoes.coordX");
                double coordY = currentTags.getDouble("vertigoes.coordY");
                double coordZ = currentTags.getDouble("vertigoes.coordZ");
                String dim = currentTags.getString("vertigoes.dim");
                if(dim.contains(":"))
                    dim = dim.substring(dim.indexOf(":")+1);

                tooltipComponents.add(Component.literal(String.format("RIGHT-CLICK to teleport to (x=%.2f, y=%.2f, z=%.2f)", coordX, coordY, coordZ)).withStyle(ChatFormatting.GRAY));
                tooltipComponents.add(Component.literal(String.format("IF you're in %s", dim)).withStyle(ChatFormatting.GRAY));
                tooltipComponents.add(Component.literal("SNEAK+RIGHT-CLICK to change coords").withStyle(ChatFormatting.GRAY));
            }
        } else {
            tooltipComponents.add(Component.literal("Forget me not..").withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        if(player.isShiftKeyDown()) {
            CompoundTag tags = new CompoundTag();
            tags.putBoolean("vertigoes.wasPlaced", true);
            tags.putDouble("vertigoes.coordX", player.getX());
            tags.putDouble("vertigoes.coordY", player.getY());
            tags.putDouble("vertigoes.coordZ", player.getZ());
            tags.putString("vertigoes.dim", level.dimension().location().toString());
            ItemStack itemstack = player.getItemInHand(hand);
            itemstack.setTag(tags);
            return InteractionResultHolder.success(itemstack);
        }
        else {
            ItemStack itemstack = player.getItemInHand(hand);
            CompoundTag currentTags = itemstack.getTag();
            if(currentTags != null) {
                if (!level.isClientSide) {
                    String dim = currentTags.getString("vertigoes.dim");

                    if(player.getLevel().dimension().location().toString().equals(dim)) {
                        double coordX = currentTags.getDouble("vertigoes.coordX");
                        double coordY = currentTags.getDouble("vertigoes.coordY");
                        double coordZ = currentTags.getDouble("vertigoes.coordZ");
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
}
