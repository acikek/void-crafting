package com.acikek.voidcrafting.api;

import com.acikek.voidcrafting.recipe.Position;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;

public class PositionBuilder {

    public float x = 0.0f;
    public float z = 0.0f;
    public float radius = Position.DEFAULT_RADIUS;
    public RegistryKey<World> worldKey = Position.DEFAULT_WORLD_KEY;
    public boolean absolute = false;

    public PositionBuilder coordinates(float x, float z) {
        this.x = x;
        this.z = z;
        return this;
    }

    public PositionBuilder radius(float radius) {
        this.radius = radius;
        return this;
    }

    public PositionBuilder worldKey(RegistryKey<World> worldKey) {
        this.worldKey = worldKey;
        return this;
    }

    public PositionBuilder worldKey(String world) {
        return worldKey(Position.getKeyFromString(world));
    }

    public PositionBuilder absolute(boolean absolute) {
        this.absolute = absolute;
        return this;
    }

    public Position build() {
        return new Position(x, z, radius, worldKey, absolute);
    }
}
