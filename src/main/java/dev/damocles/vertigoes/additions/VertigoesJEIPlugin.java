package dev.damocles.vertigoes.additions;

import dev.damocles.vertigoes.datagen.GlassHeartConfig;
import dev.damocles.vertigoes.datagen.PearlsConfig;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static dev.damocles.vertigoes.setup.Registration.*;

@JeiPlugin
public class VertigoesJEIPlugin implements IModPlugin {

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation("vertigoes", "default");
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registry) {
        IIngredientType<ItemStack> itemType = registry.getIngredientManager().getIngredientType(ItemStack.class);

        registry.addIngredientInfo(new ItemStack(MYOSOTIS_ITEM.get()), itemType,
                Component.literal("Retains the coordinates of the last time it was placed."));
        registry.addIngredientInfo(new ItemStack(ENDER_MYOSOTIS.get()), itemType,
                Component.literal("One-use teleport with the condition that you are in the same dimension as the saved coordinates."));
        registry.addIngredientInfo(new ItemStack(PRIMAL_PEARL.get()), itemType,
                Component.literal(String.format("""
                        While in inventory, completing certain tasks may transform this and give you some benefits:
                        Plant life Pearl - drown in a swamp biome
                        Animal life Pearl - breed %d animals
                        Aquatic life Pearl - kill %d Drowned
                        Death Pearl - kill %d Villagers""", PearlsConfig.getAnimalRequirement(), PearlsConfig.getAquaticRequirement(), PearlsConfig.getDeathRequirement())));
        registry.addIngredientInfo(new ItemStack(PLANT_PEARL.get()), itemType,
                Component.literal("""
                        Obtained by drowning in a swamp biome with a Primal Pearl in inventory.
                        On right-click you can put a special Plant Essence block with the pearl indefinitely."""));
        registry.addIngredientInfo(new ItemStack(ANIMAL_PEARL.get()), itemType,
                Component.literal(String.format("""
                        Obtained by breeding %d animals with a Primal Pearl in inventory.
                        While in hotbar, you deal extra damage to undead mobs (equal to Smite IV).
                        Turns back into a Primal Pearl if you kill a Villager or an animal.""", PearlsConfig.getAnimalRequirement())));
        registry.addIngredientInfo(new ItemStack(DEATH_PEARL.get()), itemType,
                Component.literal(String.format("""
                        Obtained by killing %d Villagers with a Primal Pearl in inventory.
                        While in main hand, undead creatures can't damage you (still take knockback).
                        Turns back into a Primal Pearl if you cure a Zombie Villager.""", PearlsConfig.getDeathRequirement())));
        registry.addIngredientInfo(new ItemStack(AQUATIC_PEARL.get()), itemType,
                Component.literal(String.format("""
                        Obtained by killing %d Drowned with a Primal Pearl in inventory.
                        While in main hand, gain Water Breathing.
                        Turns back into a Primal Pearl if you kill any fish/water friendly creature.""", PearlsConfig.getAquaticRequirement())));
        registry.addIngredientInfo(new ItemStack(PLANT_ESSENCE_ITEM.get()), itemType,
                Component.literal("Only obtained by using the Plant life Pearl."));
        registry.addIngredientInfo(new ItemStack(UNSTOPPABLE_FORCE.get()), itemType,
                Component.literal("""
                        Using it on Bedrock teleports you to The End.
                        Breaking a Beacon with it drops instead a Heart of Glass."""));

        int n = GlassHeartConfig.getGlassHeartRadius()*2+1;

        registry.addIngredientInfo(new ItemStack(GLASS_HEART_ITEM.get()), itemType,
                Component.literal(String.format("""
                        In a %dx%dx%d area of effect every player receives Regeneration II. Glows and can be waterlogged.
                        The potion effect can be changed by using a Lingering Potion of the desired effect on it.""", n, n, n)));
    }
}
