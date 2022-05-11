package com.acikek.voidcrafting.mixin;

import com.acikek.voidcrafting.api.VoidCraftingAPI;
import com.acikek.voidcrafting.recipe.Position;
import com.acikek.voidcrafting.recipe.VoidRecipe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    public World world;

    @Inject(method = "tickInVoid", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(value = "HEAD"))
    private void dropPreservedDust(CallbackInfo ci) {
        if (!world.isClient()) {
            Entity entity = (Entity) (Object) this;
            if (entity instanceof ItemEntity itemEntity) {
                ItemStack stack = itemEntity.getStack();
                if (VoidCraftingAPI.isVoidResistant(stack)) {
                    Position position = VoidCraftingAPI.getVoidResistancePosition(stack);
                    position.dropItems(world, itemEntity, true, null, null);
                }
                else {
                    SimpleInventory inventory = new SimpleInventory(stack);
                    world.getRecipeManager()
                            .getFirstMatch(VoidRecipe.Type.INSTANCE, inventory, world)
                            .ifPresent(match -> match.activate(world, itemEntity));
                }
            }
        }
    }
}
