package dev.damocles.vertigoes.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

import static dev.damocles.vertigoes.setup.Registration.*;

public class PlantPearl extends AbstractElementalPearl {

    public PlantPearl(Properties properties) {
        super(properties, PLANT_ESSENCE_ITEM.get());
    }

    /*@Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        BlockHitResult hitResult = new BlockHitResult(context.getClickLocation(), context.getClickedFace(), context.getClickedPos(), context.isInside());
        BlockPlaceContext newContext = new BlockPlaceContext(context.getLevel(), context.getPlayer(), context.getHand(), PLANT_ESSENCE_ITEM.get().getDefaultInstance(), hitResult);

        return PLANT_ESSENCE_ITEM.get().useOn(newContext);
    }*/

    @Override
    public float getDestroySpeed(@NotNull ItemStack stack, BlockState state) {
        if(state.is(PLANT_ESSENCE.get()))
            return 16.0F;
        return super.getDestroySpeed(stack, state);
    }

    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        tooltipComponents.add(new TextComponent("Plant Life").withStyle(ChatFormatting.GRAY));
    }
}
