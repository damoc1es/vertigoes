package dev.damocles.vertigoes.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;


public class AbstractElementalPearl extends Item {
    Item elemBlock;

    public AbstractElementalPearl(Properties properties, Item placeableBlock) {
        super(properties);
        elemBlock = placeableBlock;
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        if(elemBlock == null)
            return InteractionResult.PASS;

        BlockHitResult hitResult = new BlockHitResult(context.getClickLocation(), context.getClickedFace(), context.getClickedPos(), context.isInside());
        BlockPlaceContext newContext = new BlockPlaceContext(context.getLevel(), context.getPlayer(), context.getHand(), elemBlock.getDefaultInstance(), hitResult);

        return elemBlock.useOn(newContext);
    }
}
