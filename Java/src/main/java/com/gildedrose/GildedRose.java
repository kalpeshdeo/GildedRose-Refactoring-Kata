package com.gildedrose;

class GildedRose {
    private static final String BRIE = "Aged Brie";
    private static final String PASSES = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String CONJURED_PREFIX = "Conjured";

    private static final int MIN_QUALITY = 0;
    private static final int MAX_QUALITY = 50;
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
        if (item.name.equals(SULFURAS)) {
            return;
        }

        switch (item.name) {
            case BRIE:
                increaseQuality(item);
                item.sellIn--;
                if (isExpired(item)) {
                    increaseQuality(item);
                }
                break;
            case PASSES:
                increaseQuality(item);
                if (item.quality < MAX_QUALITY + 1 && item.sellIn < PASSES_DOUBLE_RATE_BELOW) {
                    increaseQuality(item);
                }
                if (item.sellIn < PASSES_TRIPLE_RATE_BELOW) {
                    increaseQuality(item);
                }
                item.sellIn--;
                if (isExpired(item)) {
                    item.quality = MIN_QUALITY;
                }
                break;
            default:
                int rate = item.name.startsWith(CONJURED_PREFIX) ? 2 : 1;
                decreaseQuality(item, rate);
                item.sellIn--;
                if (isExpired(item)) {
                    decreaseQuality(item, rate);
                }
                break;
        }
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
        item.quality = Math.max(MIN_QUALITY, item.quality - amount);
    }
}
