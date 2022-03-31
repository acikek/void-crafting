# Void Crafting

This mod adds a recipe type for throwing items into the void.

## Example

```json
{
  "type": "voidcrafting:void_crafting",
  "input": {
    "item": "minecraft:gold_ingot"
  },
  "radius": 10.2,
  "result": {
    "item": "minecraft:gold_block",
    "count": 3
  }
}
```
When a gold ingot falls into the void, a stack of 3 gold blocks will spawn in a 10.2-block radius around the End dimension's `0, 0`.

See the [wiki](https://github.com/acikek/void-crafting/wiki) for complete documentation.

## License

MIT © 2022 Skye P.
