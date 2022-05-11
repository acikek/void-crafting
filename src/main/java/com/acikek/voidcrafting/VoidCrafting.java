package com.acikek.voidcrafting;

import com.acikek.voidcrafting.advancement.ModCriteria;
import com.acikek.voidcrafting.api.VoidCraftingAPI;
import com.acikek.voidcrafting.recipe.VoidRecipe;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
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
        ModCriteria.register();
        VoidRecipe.register();
        ItemTooltipCallback.EVENT.register(VoidCraftingAPI::appendVoidResistanceTooltip);
    }
}
