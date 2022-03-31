package com.acikek.voidcrafting;

import com.acikek.voidcrafting.recipe.VoidRecipe;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VoidCrafting implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger("voidcrafting");

    @Override
    public void onInitialize() {
        VoidRecipe.register();
    }
}
