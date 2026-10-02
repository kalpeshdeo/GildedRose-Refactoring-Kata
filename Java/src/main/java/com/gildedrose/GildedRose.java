package com.gildedrose;

class GildedRose {

    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String CONJURED_PREFIX = "Conjured";

    private static final int MIN_QUALITY = 0;
    private static final int MAX_QUALITY = 50;

    private static final int NORMAL_DEGRADATION_RATE = 1;
    private static final int CONJURED_DEGRADATION_RATE = 2;

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
        if (isLegendary(item)) {
            return;
        }

        applyInitialQualityChange(item);
        item.sellIn--;

        if (item.sellIn < 0) {
            applyExpiredQualityChange(item);
        }
    }

    private void applyInitialQualityChange(Item item) {
        if (isAgedBrie(item)) {
            increaseQuality(item);
        } else if (isBackstagePass(item)) {
            increaseQuality(item);
            if (item.sellIn < 11) {
                increaseQuality(item);
            }
            if (item.sellIn < 6) {
                increaseQuality(item);
            }
        } else {
            decreaseQuality(item, degradationRate(item));
        }
    }

    private void applyExpiredQualityChange(Item item) {
        if (isAgedBrie(item)) {
            increaseQuality(item);
        } else if (isBackstagePass(item)) {
            item.quality = MIN_QUALITY;
        } else {
            decreaseQuality(item, degradationRate(item));
        }
    }

    private void increaseQuality(Item item) {
        item.quality = Math.min(MAX_QUALITY, item.quality + 1);
    }

    private void decreaseQuality(Item item, int amount) {
        item.quality = Math.max(MIN_QUALITY, item.quality - amount);
    }

    private int degradationRate(Item item) {
        return isConjured(item) ? CONJURED_DEGRADATION_RATE : NORMAL_DEGRADATION_RATE;
    }

    private boolean isLegendary(Item item) {
        return SULFURAS.equals(item.name);
    }

    private boolean isAgedBrie(Item item) {
        return AGED_BRIE.equals(item.name);
    }

    private boolean isBackstagePass(Item item) {
        return BACKSTAGE_PASSES.equals(item.name);
    }

        private boolean isConjured(Item item) {
        return item.name != null && item.name.startsWith(CONJURED_PREFIX);
    }
}
