package com.acikek.voidcrafting;

import com.acikek.voidcrafting.advancement.ModCriteria;
import com.acikek.voidcrafting.recipe.VoidRecipe;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VoidCrafting implements ModInitializer {

    public static final String ID = "voidcrafting";

    public static Identifier id(String path) {
        return new Identifier(ID, path);
    }

    public static Logger LOGGER = LogManager.getLogger(ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Void Crafting");
        if (FabricLoader.getInstance().isModLoaded("roughlyenoughitems")) {
            LOGGER.warn("Void Crafting support on REI is limited; use EMI for better integration! https://modrinth.com/mod/emi");
        }
        ModCriteria.register();
        VoidRecipe.register();
    }
}
