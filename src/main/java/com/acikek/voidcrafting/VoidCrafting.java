package com.acikek.voidcrafting;

import com.acikek.voidcrafting.advancement.ModCriteria;
import com.acikek.voidcrafting.recipe.VoidRecipe;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class VoidCrafting implements ModInitializer {

    public static Identifier id(String path) {
        return new Identifier("voidcrafting", path);
    }

    public static Logger LOGGER = LogManager.getLogger("voidcrafting");

    @Override
    public void onInitialize(ModContainer mod) {
        LOGGER.info("Initializing Void Crafting");
        ModCriteria.register();
        VoidRecipe.register();
    }
}
