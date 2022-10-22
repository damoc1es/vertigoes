package dev.damocles.vertigoes.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.world.level.Level.END;
import static net.minecraft.world.level.block.Blocks.BEACON;
import static net.minecraft.world.level.block.Blocks.BEDROCK;

public class UnstoppableForce extends Item {
    public UnstoppableForce(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if(level.getBlockState(context.getClickedPos()).is(BEDROCK) && level.getServer() != null) {
            ServerLevel end = level.getServer().getLevel(END);
            if(end != null && context.getPlayer() != null) {
                context.getPlayer().changeDimension(end);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return stack.copy();
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public float getDestroySpeed(@NotNull ItemStack stack, BlockState state) {
        if(state.is(BEACON))
            return 6.0F;
        return super.getDestroySpeed(stack, state);
    }

    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        tooltipComponents.add(Component.literal("What would happen if it meets the immovable object?").withStyle(ChatFormatting.GRAY));
    }
}

