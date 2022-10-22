package dev.damocles.vertigoes.block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

import static dev.damocles.vertigoes.setup.Registration.PLANT_ESSENCE;

public class MyosotisBlock extends FlowerBlock {
    public MyosotisBlock(MobEffect suspiciousStewEffect, int effectDuration, Properties properties) {
        super(suspiciousStewEffect, effectDuration, properties);
    }

    @Override
    public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos pos, @NotNull BlockState blockState, @Nullable BlockEntity blockEntity, @NotNull ItemStack tool) {
        player.awardStat(Stats.BLOCK_MINED.get(this));

        // Block.dropResources
        if (level instanceof ServerLevel) {
            getDrops(blockState, (ServerLevel)level, pos, blockEntity, player, tool).forEach((itemDrop) -> {
                // Set tag of drop with the coordinates and dimension of last placement
                CompoundTag tags = new CompoundTag();
                tags.putBoolean("vertigoes.wasPlaced", true);
                tags.putDouble("vertigoes.coordX", pos.getX());
                tags.putDouble("vertigoes.coordY", pos.getY());
                tags.putDouble("vertigoes.coordZ", pos.getZ());
                tags.putString("vertigoes.dim", level.dimension().location().toString());

                itemDrop.setTag(tags);
                popResource(level, pos, itemDrop);
            });
            blockState.spawnAfterBreak((ServerLevel)level, pos, tool, true);
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        // Setting the tooltip depending on if it was ever placed
        if(stack.hasTag() && stack.getTag() != null) {
            CompoundTag currentTags = stack.getTag();
            if(currentTags.contains("vertigoes.wasPlaced") && currentTags.getBoolean("vertigoes.wasPlaced")) { //
                double coordX = currentTags.getDouble("vertigoes.coordX");
                double coordY = currentTags.getDouble("vertigoes.coordY");
                double coordZ = currentTags.getDouble("vertigoes.coordZ");
                String dim = currentTags.getString("vertigoes.dim");
                if(dim.contains(":"))
                    dim = dim.substring(dim.indexOf(":")+1);

                tooltip.add(Component.literal(String.format("(x=%.2f, y=%.2f, z=%.2f) in %s", coordX, coordY, coordZ, dim)).withStyle(ChatFormatting.GRAY));
            }
        } else {
            tooltip.add(Component.literal("Forget me not..").withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public boolean mayPlaceOn(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return state.is(PLANT_ESSENCE.get()) || super.mayPlaceOn(state, level, pos);
    }
}
