package com.acikek.voidcrafting.api.event;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@FunctionalInterface
public interface StackVoided {

    void onStackVoided(World world, ItemEntity entity, ItemStack stack);
}
