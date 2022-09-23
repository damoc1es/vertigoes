package dev.damocles.vertigoes.block;

import dev.damocles.vertigoes.datagen.GlassHeartConfig;
import dev.damocles.vertigoes.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class GlassHeartBlockEntity extends BlockEntity {
    public int tickCount;
    MobEffect effect;
    int effectAmplifier;

    public GlassHeartBlockEntity(BlockPos pos, BlockState blockState) {
        super(Registration.GLASS_HEART_BLOCK_ENTITY.get(), pos, blockState);
    }

    private static void applyEffects(Level level, BlockPos pos, MobEffect effect, int effectAmplifier) {
        if (!level.isClientSide) {
            if(effect == null) {
                effect = MobEffects.REGENERATION;
                effectAmplifier = 1;
            }

            int radius = GlassHeartConfig.glassHeartRadius.get();

            AABB aabb = (new AABB(pos)).inflate(radius).expandTowards(0.0D, 0.0D, 0.0D);
            List<Player> list = level.getEntitiesOfClass(Player.class, aabb);

            for(Player player : list) {
                player.addEffect(new MobEffectInstance(effect, 10*20, effectAmplifier, true, true));
            }
        }
    }

    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("Effect", MobEffect.getId(this.effect));
    }

    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        this.effect = MobEffect.byId(tag.getInt("Effect"));
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, GlassHeartBlockEntity blockEntity) {
        ++blockEntity.tickCount;
        long i = level.getGameTime();
        if (i % 40L == 0L) {
            applyEffects(level, pos, blockEntity.effect, blockEntity.effectAmplifier);
        }
    }
}
