package dev.damocles.vertigoes.item;

import dev.damocles.vertigoes.setup.Registration;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

import static dev.damocles.vertigoes.setup.Registration.AQUATIC_PEARL;
import static net.minecraft.world.effect.MobEffects.WATER_BREATHING;
import static net.minecraft.world.item.Items.WATER_BUCKET;

public class AquaticPearl extends AbstractElementalPearl {

    public AquaticPearl(Properties properties) {
        super(properties, null);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        if(isSelected) {
            Player player = (Player)entity;
            player.addEffect(new MobEffectInstance(WATER_BREATHING, 260));
        }
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        if(context.getPlayer() == null)
            return InteractionResult.FAIL;
        return WATER_BUCKET.getDefaultInstance().use(context.getLevel(), context.getPlayer(), context.getHand()).getResult();
    }

    public static void disablePearl(Player player) {
        for(int i=0; i<player.getInventory().getContainerSize(); i++) {
            if(player.getInventory().getItem(i).is(AQUATIC_PEARL.get()))
                player.getInventory().setItem(i, Registration.PRIMAL_PEARL.get().getDefaultInstance());
        }
    }

    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        tooltipComponents.add(new TextComponent("Aquatic Life").withStyle(ChatFormatting.GRAY));
    }
}
