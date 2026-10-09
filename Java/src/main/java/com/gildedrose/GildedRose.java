package com.gildedrose;

class GildedRose {
    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String CONJURED_PREFIX = "Conjured";

    private static final int MIN_QUALITY = 0;
    private static final int MAX_QUALITY = 50;

    /** Pass quality rises by an extra point when sellIn is strictly below this. */
    private static final int PASS_DOUBLE_RATE_BELOW_DAYS = 11;
    /** Pass quality rises by another extra point when sellIn is strictly below this. */
    private static final int PASS_TRIPLE_RATE_BELOW_DAYS = 6;

    private static final int BRIE_INCREMENT = 1;
    private static final int BASE_PASS_INCREMENT = 1;
    private static final int NORMAL_DEGRADATION = 1;
    private static final int CONJURED_DEGRADATION = 2 * NORMAL_DEGRADATION;

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
        if (isSulfuras(item)) {
            return;
        }

        applyDailyChange(item);
        item.sellIn = item.sellIn - 1;
        if (isExpired(item)) {
            applyExpiredChange(item);
        }
    }

    private boolean isSulfuras(Item item) {
        return SULFURAS.equals(item.name);
    }

    private boolean isExpired(Item item) {
        return item.sellIn < 0;
    }

    private void applyDailyChange(Item item) {
        if (AGED_BRIE.equals(item.name)) {
            increaseQuality(item, BRIE_INCREMENT);
        } else if (BACKSTAGE_PASS.equals(item.name)) {
            increaseQuality(item, passIncrement(item.sellIn));
        } else {
            decreaseQuality(item, degradationRate(item));
        }
    }

    private void applyExpiredChange(Item item) {
        if (AGED_BRIE.equals(item.name)) {
            increaseQuality(item, BRIE_INCREMENT);
        } else if (BACKSTAGE_PASS.equals(item.name)) {
            item.quality = MIN_QUALITY;
        } else {
            decreaseQuality(item, degradationRate(item));
        }
    }

    private int passIncrement(int sellIn) {
        int increment = BASE_PASS_INCREMENT;
        if (sellIn < PASS_DOUBLE_RATE_BELOW_DAYS) {
            increment++;
        }
        if (sellIn < PASS_TRIPLE_RATE_BELOW_DAYS) {
            increment++;
        }
        return increment;
    }

    private int degradationRate(Item item) {
        return isConjured(item) ? CONJURED_DEGRADATION : NORMAL_DEGRADATION;
    }

    private boolean isConjured(Item item) {
        return item.name != null && item.name.startsWith(CONJURED_PREFIX);
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
