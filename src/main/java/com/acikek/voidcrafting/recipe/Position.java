package com.acikek.voidcrafting.recipe;

import com.acikek.voidcrafting.VoidCrafting;
import com.acikek.voidcrafting.api.PositionBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.block.AirBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

import java.util.Random;

public record Position(float x, float z, float radius, RegistryKey<World> worldKey, boolean absolute) {

    public static final float DEFAULT_RADIUS = 5.0f;
    public static final RegistryKey<World> DEFAULT_WORLD_KEY = World.END;

    public static RegistryKey<World> getKeyFromString(String world) {
        return RegistryKey.of(Registry.WORLD_KEY, Identifier.tryParse(world));
    }

    public ItemStack getStack(ItemEntity itemEntity, boolean replicate, ItemStack result) {
        ItemStack stack = (replicate ? itemEntity.getStack() : result).copy();
        if (replicate) {
            stack.setCount(1);
        }
        return stack;
    }

    public static Vec2f getRandomCoordinates(float radius, Random random) {
        float angle = random.nextFloat(MathHelper.TAU);
        float cos = MathHelper.cos(angle);
        float sin = MathHelper.sin(angle);
        return new Vec2f(radius * cos, radius * -sin);
    }

    public boolean isVoid(int y, World world, float posX, float posZ) {
        return y == 0 && world.getBlockState(new BlockPos(posX, y, posZ)).getBlock() instanceof AirBlock;
    }

    public World getWorld(World world) {
        if (world.getServer() == null) {
            return null;
        }
        return world.getServer().getWorld(worldKey);
    }

    public World dropItems(World sourceWorld, ItemEntity itemEntity, boolean replicate, ItemStack result, Identifier id) {
        World world = getWorld(sourceWorld);
        if (world == null) {
            String recipeId = id != null ? ("recipe: " + id) : "void-resistant item";
            VoidCrafting.LOGGER.error("World '" + worldKey.getValue() + "' not found (" + recipeId + ")");
            return null;
        }
        for (int i = 0; i < itemEntity.getStack().getCount(); i++) {
            Vec2f pos = getRandomCoordinates(radius, world.random);
            float posX = pos.x + x + (absolute ? (float) itemEntity.getX() : 0.0f);
            float posZ = pos.y + z + (absolute ? (float) itemEntity.getZ() : 0.0f);
            int y = world.getTopY(Heightmap.Type.WORLD_SURFACE, (int) posX, (int) posZ);
            if (!isVoid(y, world, posX, posZ)) {
                ItemEntity drop = new ItemEntity(world, posX, y, posZ, getStack(itemEntity, replicate, result));
                world.spawnEntity(drop);
            }
        }
        return world;
    }

    public static Position read(PacketByteBuf buf) {
        float x = buf.readFloat();
        float z = buf.readFloat();
        float radius = buf.readFloat();
        RegistryKey<World> worldKey = getKeyFromString(buf.readString());
        boolean absolute = buf.readBoolean();
        return new Position(x, z, radius, worldKey, absolute);
    }

    public void write(PacketByteBuf buf) {
        buf.writeFloat(x);
        buf.writeFloat(z);
        buf.writeFloat(radius);
        buf.writeString(worldKey.getValue().toString());
        buf.writeBoolean(absolute);
    }

    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putFloat("X", x);
        nbt.putFloat("Z", z);
        nbt.putFloat("Radius", radius);
        nbt.putString("World", worldKey.getValue().toString());
        nbt.putBoolean("Absolute", absolute);
        return nbt;
    }

    public static Position fromNbt(NbtCompound nbt) {
        PositionBuilder builder = new PositionBuilder()
                .coordinates(nbt.getFloat("X"), nbt.getFloat("Z"))
                .absolute(nbt.getBoolean("Absolute"));
        if (nbt.contains("Radius")) {
            builder = builder.radius(nbt.getFloat("Radius"));
        }
        if (nbt.contains("World")) {
            builder = builder.worldKey(getKeyFromString(nbt.getString("World")));
        }
        return builder.build();
    }

    public void writeJson(JsonObject obj) {
        obj.add("x", new JsonPrimitive(x));
        obj.add("z", new JsonPrimitive(z));
        obj.add("radius", new JsonPrimitive(radius));
        obj.add("world", new JsonPrimitive(worldKey.getValue().toString()));
        obj.add("absolute", new JsonPrimitive(absolute));
    }

    @Override
    public String toString() {
        return x + ", " + z + ", r" + radius + ", " + worldKey.getValue() + ", " + absolute;
    }
}
