package com.gildedrose;

final class ItemUpdaters {
    private static final int MIN_QUALITY = 0;
    private static final int MAX_QUALITY = 50;
    private static final int BACKSTAGE_DOUBLE_THRESHOLD = 11;
    private static final int BACKSTAGE_TRIPLE_THRESHOLD = 6;

    private ItemUpdaters() {
    }

    static void updateNormal(Item item) {
        decreaseQuality(item);
        item.sellIn--;
        if (isExpired(item)) {
            decreaseQuality(item);
        }
    }

    static void updateAgedBrie(Item item) {
        increaseQuality(item);
        item.sellIn--;
        if (isExpired(item)) {
            increaseQuality(item);
        }
    }

    static void updateBackstagePasses(Item item) {
        increaseQuality(item);
        if (item.sellIn < BACKSTAGE_DOUBLE_THRESHOLD) {
            increaseQuality(item);
        }
        if (item.sellIn < BACKSTAGE_TRIPLE_THRESHOLD) {
            increaseQuality(item);
        }
        item.sellIn--;
        if (isExpired(item)) {
            item.quality = MIN_QUALITY;
        }
    }

    static void updateSulfuras(Item item) {
        // Legendary item: never changes.
    }

    private static boolean isExpired(Item item) {
        return item.sellIn < 0;
    }

    private static void increaseQuality(Item item) {
        if (item.quality < MAX_QUALITY) {
            item.quality++;
        }
    }

    private static void decreaseQuality(Item item) {
        if (item.quality > MIN_QUALITY) {
            item.quality--;
        }
    }
}
