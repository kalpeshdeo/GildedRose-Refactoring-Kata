package com.gildedrose;

class GildedRose {
    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String CONJURED_PREFIX = "Conjured";

    private static final int MIN_QUALITY = 0;
    private static final int MAX_QUALITY = 50;
    private static final int PASSES_DOUBLE_THRESHOLD = 11;
    private static final int PASSES_TRIPLE_THRESHOLD = 6;

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
        if (SULFURAS.equals(item.name)) {
            return;
        }

        ageDaily(item);
        item.sellIn--;
        if (item.sellIn < 0) {
            applyExpiry(item);
        }
    }

    private void ageDaily(Item item) {
        switch (item.name) {
            case AGED_BRIE:
                increaseQuality(item, 1);
                break;
            case BACKSTAGE_PASSES:
                increaseQuality(item, passesIncrement(item.sellIn));
                break;
            default:
                decreaseQuality(item, degradeRate(item));
        }
    }

    private void applyExpiry(Item item) {
        switch (item.name) {
            case AGED_BRIE:
                increaseQuality(item, 1);
                break;
            case BACKSTAGE_PASSES:
                item.quality = MIN_QUALITY;
                break;
            default:
                decreaseQuality(item, degradeRate(item));
        }
    }

    private int passesIncrement(int sellIn) {
        if (sellIn < PASSES_TRIPLE_THRESHOLD) {
            return 3;
        }
        if (sellIn < PASSES_DOUBLE_THRESHOLD) {
            return 2;
        }
        return 1;
    }

    private int degradeRate(Item item) {
        return item.name.startsWith(CONJURED_PREFIX) ? 2 : 1;
    }

    private void increaseQuality(Item item, int amount) {
        if (item.quality < MAX_QUALITY) {
            item.quality = Math.min(MAX_QUALITY, item.quality + amount);
        }
    }

    private void decreaseQuality(Item item, int amount) {
        if (item.quality > MIN_QUALITY) {
            item.quality = Math.max(MIN_QUALITY, item.quality - amount);
        }
    }
}
