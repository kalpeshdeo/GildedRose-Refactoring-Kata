package com.gildedrose;

class GildedRose {
    Item[] items;
    private final ItemUpdaterRegistry registry;

    public GildedRose(Item[] items) {
        this(items, ItemUpdaterRegistry.createDefault());
    }

    GildedRose(Item[] items, ItemUpdaterRegistry registry) {
        this.items = items;
        this.registry = registry;
    }

    public void updateQuality() {
        for (Item item : items) {
            registry.updaterFor(ItemCategory.of(item)).update(item);
        }
    }
}
