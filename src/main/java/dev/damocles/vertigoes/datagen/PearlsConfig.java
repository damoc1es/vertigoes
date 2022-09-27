package dev.damocles.vertigoes.datagen;

import dev.damocles.vertigoes.item.AnimalPearl;
import dev.damocles.vertigoes.item.AquaticPearl;
import dev.damocles.vertigoes.item.DeathPearl;
import net.minecraftforge.common.ForgeConfigSpec;

public class PearlsConfig {
    public static ForgeConfigSpec.IntValue animalRequirement;
    public static ForgeConfigSpec.BooleanValue animalCanBeDisabled;

    public static ForgeConfigSpec.IntValue deathRequirement;
    public static ForgeConfigSpec.BooleanValue deathCanBeDisabled;

    public static ForgeConfigSpec.IntValue aquaticRequirement;
    public static ForgeConfigSpec.BooleanValue aquaticCanBeDisabled;

    public static int getAnimalRequirement() {
        if(animalRequirement.get() == 0)
            return AnimalPearl.defaultAnimalRequirement;
        return animalRequirement.get();
    }

    public static int getDeathRequirement() {
        if(deathRequirement.get() == 0)
            return DeathPearl.defaultDeathRequirement;
        return deathRequirement.get();
    }

    public static int getAquaticRequirement() {
        if(aquaticRequirement.get() == 0)
            return AquaticPearl.defaultAquaticRequirement;
        return aquaticRequirement.get();
    }

    public static void registerServer(ForgeConfigSpec.Builder builder) {
        builder.comment("Settings for the Animal-life Pearl").push("animal_pearl");

        animalRequirement = builder
                .comment("How many animals you have to breed. 0 to use the default.")
                .defineInRange("animal_requirement", 0, 0, Integer.MAX_VALUE);

        animalCanBeDisabled = builder
                .comment("Animal-life pearl gets disabled if you kill a Villager or an animal")
                .define("animal_pearl_can_be_disabled", true);

        builder.pop();


        builder.comment("Settings for the Death Pearl").push("death_pearl");

        deathRequirement = builder
                .comment("How many Villagers you have to kill. 0 to use the default.")
                .defineInRange("death_requirement", 0, 0, Integer.MAX_VALUE);

        deathCanBeDisabled = builder
                .comment("Death pearl gets disabled if you cure a Zombie Villager")
                .define("death_pearl_can_be_disabled", true);

        builder.pop();


        builder.comment("Settings for the Aquatic Pearl").push("aquatic_pearl");

        aquaticRequirement = builder
                .comment("How many Drowned you have to kill. 0 to use the default.")
                .defineInRange("aquatic_requirement", 0, 0, Integer.MAX_VALUE);

        aquaticCanBeDisabled = builder
                .comment("Aquatic-life pearl gets disabled if you kill any fish/water friendly creature")
                .define("aquatic_pearl_can_be_disabled", true);

        builder.pop();
    }
}
