package com.acikek.voidcrafting.advancement;

import net.minecraft.advancement.criterion.Criteria;

public class ModCriteria {

    public static VoidCraftSuccessCriterion VOID_CRAFT_SUCCESS = new VoidCraftSuccessCriterion();

    public static void register() {
        Criteria.register(VOID_CRAFT_SUCCESS);
    }
}
