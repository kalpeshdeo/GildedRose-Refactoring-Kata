package com.gildedrose;

import java.util.EnumMap;
import java.util.Map;

class ItemUpdaterRegistry {
    private final Map<ItemCategory, ItemUpdater> updaters = new EnumMap<>(ItemCategory.class);

    void register(ItemCategory category, ItemUpdater updater) {
        updaters.put(category, updater);
    }

    ItemUpdater updaterFor(ItemCategory category) {
        ItemUpdater updater = updaters.get(category);
        if (updater == null) {
            throw new IllegalStateException("No updater registered for category " + category);
        }
        return updater;
    }

    static ItemUpdaterRegistry createDefault() {
        ItemUpdaterRegistry registry = new ItemUpdaterRegistry();
        registry.register(ItemCategory.NORMAL, ItemUpdaters::updateNormal);
        registry.register(ItemCategory.AGED_BRIE, ItemUpdaters::updateAgedBrie);
        registry.register(ItemCategory.BACKSTAGE_PASSES, ItemUpdaters::updateBackstagePasses);
        registry.register(ItemCategory.SULFURAS, ItemUpdaters::updateSulfuras);
        return registry;
    }
}
