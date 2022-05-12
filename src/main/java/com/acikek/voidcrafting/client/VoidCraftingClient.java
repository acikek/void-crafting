package com.acikek.voidcrafting.client;

import com.acikek.voidcrafting.api.VoidCraftingAPI;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;

public class VoidCraftingClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register(VoidCraftingAPI::appendVoidResistanceTooltip);
    }
}
