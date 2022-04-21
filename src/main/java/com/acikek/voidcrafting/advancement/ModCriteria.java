package com.acikek.voidcrafting.advancement;

import net.fabricmc.fabric.mixin.object.builder.CriteriaAccessor;

public class ModCriteria {

    public static VoidCraftSuccessCriterion VOID_CRAFT_SUCCESS = new VoidCraftSuccessCriterion();

    public static void register() {
        CriteriaAccessor.callRegister(VOID_CRAFT_SUCCESS);
    }
}
