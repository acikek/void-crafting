package com.acikek.voidcrafting.mixin;

import com.acikek.voidcrafting.api.VoidCraftingAPI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.UUID;

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
                UUID throwerUuid = ((ItemEntityAccessor) itemEntity).getThrower();
                PlayerEntity thrower = throwerUuid != null
                        ? world.getPlayerByUuid(throwerUuid)
                        : null;
                VoidCraftingAPI.STACK_VOIDED.invoker().onStackVoided(world, itemEntity, stack, thrower);
            }
        }
    }
}
