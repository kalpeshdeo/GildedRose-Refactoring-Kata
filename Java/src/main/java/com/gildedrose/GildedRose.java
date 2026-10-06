package com.gildedrose;

class GildedRose {
    private static final String BRIE = "Aged Brie";
    private static final String PASSES = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String CONJURED_PREFIX = "Conjured";

    private static final int MIN_QUALITY = 0;
    private static final int MAX_QUALITY = 50;
    private static final int NORMAL_DEGRADE_RATE = 1;
    private static final int CONJURED_DEGRADE_RATE = 2;
    private static final int PASSES_DOUBLE_RATE_BELOW = 11;
    private static final int PASSES_TRIPLE_RATE_BELOW = 6;

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            updateItem(item);
        }
    }

        private void updateItem(Item item) {
        if (item == null || item.name == null) {
            return;
        }
        if (item.name.equals(SULFURAS)) {
            return;
        }

        switch (item.name) {
            case BRIE:
                updateBrie(item);
                break;
            case PASSES:
                updatePasses(item);
                break;
            default:
                updateNormal(item);
                break;
        }
    }

    private static void updateBrie(Item item) {
        increaseQuality(item);
        item.sellIn--;
        if (isExpired(item)) {
            increaseQuality(item);
        }
    }

    private static void updatePasses(Item item) {
        increaseQuality(item);
        if (item.sellIn < PASSES_DOUBLE_RATE_BELOW) {
            increaseQuality(item);
        }
        if (item.sellIn < PASSES_TRIPLE_RATE_BELOW) {
            increaseQuality(item);
        }
        item.sellIn--;
        if (isExpired(item)) {
            item.quality = MIN_QUALITY;
        }
    }

    private static void updateNormal(Item item) {
        int rate = degradeRate(item);
        decreaseQuality(item, rate);
        item.sellIn--;
        if (isExpired(item)) {
            decreaseQuality(item, rate);
        }
    }

    private static int degradeRate(Item item) {
        return isConjured(item) ? CONJURED_DEGRADE_RATE : NORMAL_DEGRADE_RATE;
    }

    private static boolean isConjured(Item item) {
        return item.name.startsWith(CONJURED_PREFIX);
    }

    private static boolean isExpired(Item item) {
        return item.sellIn < 0;
    }

    private static void increaseQuality(Item item) {
        if (item.quality < MAX_QUALITY) {
            item.quality++;
        }
    }

    private static void decreaseQuality(Item item, int amount) {
        if (item.quality > MIN_QUALITY) {
            item.quality = Math.max(MIN_QUALITY, item.quality - amount);
        }
    }
}
