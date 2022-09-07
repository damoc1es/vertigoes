package dev.damocles.vertigoes.additions;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.TextComponent;
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
                new TextComponent("Retains the coordinates of the last time it was placed."));
        registry.addIngredientInfo(new ItemStack(ENDER_MYOSOTIS.get()), itemType,
                new TextComponent("One-use teleport with the condition that you are in the same dimension as the saved coordinates."));
        registry.addIngredientInfo(new ItemStack(PRIMAL_PEARL.get()), itemType,
                new TextComponent("""
                        While in inventory, completing certain tasks may transform this and give you some benefits:
                        Plant life Pearl - drown in a swamp biome
                        Animal life Pearl - breed 10 animals
                        Aquatic life Pearl - kill 25 Drowned
                        Death Pearl - kill 10 Villagers"""));
        registry.addIngredientInfo(new ItemStack(PLANT_PEARL.get()), itemType,
                new TextComponent("""
                        Obtained by drowning in a swamp biome with a Primal Pearl in inventory.
                        On right-click you can put a special Plant Essence block with the pearl indefinitely."""));
        registry.addIngredientInfo(new ItemStack(ANIMAL_PEARL.get()), itemType,
                new TextComponent("""
                        Obtained by breeding 10 animals with a Primal Pearl in inventory.
                        While in hotbar, you deal extra damage to undead mobs (equal to Smite IV).
                        Turns back into a Primal Pearl if you kill a Villager or an animal."""));
        registry.addIngredientInfo(new ItemStack(DEATH_PEARL.get()), itemType,
                new TextComponent("""
                        Obtained by killing 10 Villagers with a Primal Pearl in inventory.
                        While in main hand, undead creatures can't damage you (still take knockback).
                        Turns back into a Primal Pearl if you cure a Zombie Villager."""));
        registry.addIngredientInfo(new ItemStack(AQUATIC_PEARL.get()), itemType,
                new TextComponent("""
                        Obtained by killing 25 Drowned with a Primal Pearl in inventory.
                        While in main hand, gain Water Breathing.
                        Turns back into a Primal Pearl if you kill any fish/water friendly creature."""));
        registry.addIngredientInfo(new ItemStack(PLANT_ESSENCE_ITEM.get()), itemType,
                new TextComponent("Only obtained by using the Plant life Pearl."));
    }
}
