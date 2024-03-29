package dev.damocles.vertigoes.setup;

import dev.damocles.vertigoes.block.GlassHeart;
import dev.damocles.vertigoes.block.MyosotisBlock;
import dev.damocles.vertigoes.block.GlassHeartBlockEntity;
import dev.damocles.vertigoes.item.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.effect.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static dev.damocles.vertigoes.Vertigoes.MODID;

public class Registration {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
        FlowerPotBlock pot = (FlowerPotBlock) Blocks.FLOWER_POT;
        pot.addPlant(MYOSOTIS.getId(), POTTED_MYOSOTIS);
    }

    public static final Item.Properties DEFAULT_ITEM_PROPERTIES = new Item.Properties().tab(ModSetup.ITEM_GROUP);
    public static final RegistryObject<Block> MYOSOTIS = BLOCKS.register("myosotis", () -> new MyosotisBlock(MobEffects.NIGHT_VISION, 5, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Item> MYOSOTIS_ITEM = fromBlock(MYOSOTIS, DEFAULT_ITEM_PROPERTIES);
    public static final RegistryObject<FlowerPotBlock> POTTED_MYOSOTIS = BLOCKS.register("potted_myosotis", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, MYOSOTIS, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));

    public static final RegistryObject<Item> ENDER_MYOSOTIS = ITEMS.register("ender_myosotis", () -> new EnderMyosotisItem(DEFAULT_ITEM_PROPERTIES));

    public static final RegistryObject<Block> PLANT_ESSENCE = BLOCKS.register("plant_essence", () -> new Block(BlockBehaviour.Properties.of(Material.PLANT).strength(0.4F).sound(SoundType.GRASS)));
    public static final RegistryObject<Item> PLANT_ESSENCE_ITEM = fromBlock(PLANT_ESSENCE, DEFAULT_ITEM_PROPERTIES);

    public static final RegistryObject<Item> PRIMAL_PEARL = ITEMS.register("primal_pearl", () -> new PrimalPearl(DEFAULT_ITEM_PROPERTIES.stacksTo(1)));
    public static final RegistryObject<Item> PLANT_PEARL = ITEMS.register("plant_pearl", () -> new PlantPearl(DEFAULT_ITEM_PROPERTIES.stacksTo(1)));
    public static final RegistryObject<Item> ANIMAL_PEARL = ITEMS.register("animal_pearl", () -> new AnimalPearl(DEFAULT_ITEM_PROPERTIES.stacksTo(1)));
    public static final RegistryObject<Item> DEATH_PEARL = ITEMS.register("death_pearl", () -> new DeathPearl(DEFAULT_ITEM_PROPERTIES.stacksTo(1)));
    public static final RegistryObject<Item> AQUATIC_PEARL = ITEMS.register("aquatic_pearl", () -> new AquaticPearl(DEFAULT_ITEM_PROPERTIES.stacksTo(1)));

    public static final RegistryObject<Item> UNSTOPPABLE_FORCE = ITEMS.register("unstoppable_force", () -> new UnstoppableForce(DEFAULT_ITEM_PROPERTIES.stacksTo(1)));

    public static final RegistryObject<Block> GLASS_HEART = BLOCKS.register("glass_heart", () -> new GlassHeart(BlockBehaviour.Properties.of(Material.PLANT).noCollission().sound(SoundType.GLASS).lightLevel((emission) -> 15)));
    public static final RegistryObject<Item> GLASS_HEART_ITEM = fromBlock(GLASS_HEART, DEFAULT_ITEM_PROPERTIES);
    public static final RegistryObject<BlockEntityType<GlassHeartBlockEntity>> GLASS_HEART_BLOCK_ENTITY = BLOCK_ENTITIES.register("glass_heart", () -> BlockEntityType.Builder.of(GlassHeartBlockEntity::new, GLASS_HEART.get()).build(null));

    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block, Item.Properties properties) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), properties));
    }
}
