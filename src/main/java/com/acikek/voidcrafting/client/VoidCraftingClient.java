package com.acikek.voidcrafting.client;

import com.acikek.voidcrafting.api.VoidCraftingAPI;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.tooltip.api.client.ItemTooltipCallback;

public class VoidCraftingClient implements ClientModInitializer {

    @Override
    public void onInitializeClient(ModContainer mod) {
        ItemTooltipCallback.EVENT.register(VoidCraftingAPI::appendVoidResistanceTooltip);
    }
}
