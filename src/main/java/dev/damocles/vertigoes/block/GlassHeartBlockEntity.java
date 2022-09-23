package dev.damocles.vertigoes.block;

import dev.damocles.vertigoes.datagen.GlassHeartConfig;
import dev.damocles.vertigoes.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;


public class GlassHeartBlockEntity extends BlockEntity {
    public int tickCount;

    public GlassHeartBlockEntity(BlockPos pos, BlockState blockState) {
        super(Registration.GLASS_HEART_BLOCK_ENTITY.get(), pos, blockState);
    }

    private static void applyEffects(Level level, BlockPos pos, MobEffect effect, int effectLevel) {
        if (!level.isClientSide) {
            int duration = 9 * 20;
            int radius = GlassHeartConfig.glassHeartRadius.get();

            AABB aabb = (new AABB(pos)).inflate(radius).expandTowards(0.0D, 0.0D, 0.0D);
            List<Player> list = level.getEntitiesOfClass(Player.class, aabb);

            for(Player player : list) {
                player.addEffect(new MobEffectInstance(effect, duration, effectLevel, true, true));
            }
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, GlassHeartBlockEntity blockEntity) {
        ++blockEntity.tickCount;
        long i = level.getGameTime();
        if (i % 40L == 0L) {
            applyEffects(level, pos, MobEffects.REGENERATION, 1);
        }
    }
}
