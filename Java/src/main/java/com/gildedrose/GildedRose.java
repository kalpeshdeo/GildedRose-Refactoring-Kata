package com.gildedrose;

class GildedRose {
    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String CONJURED_PREFIX = "Conjured";

    private static final int MIN_QUALITY = 0;
    private static final int MAX_QUALITY = 50;

    private static final int NORMAL_DEGRADE_RATE = 1;
    private static final int CONJURED_DEGRADE_RATE = 2 * NORMAL_DEGRADE_RATE;
    private static final int BRIE_INCREMENT = 1;

    private static final int PASSES_DOUBLE_THRESHOLD = 11;
    private static final int PASSES_TRIPLE_THRESHOLD = 6;
    private static final int PASSES_DEFAULT_INCREMENT = 1;
    private static final int PASSES_DOUBLE_INCREMENT = 2;
    private static final int PASSES_TRIPLE_INCREMENT = 3;

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
        if (isMalformed(item) || isLegendary(item)) {
            return;
        }

        applyQualityRule(item, false);
        ageOneDay(item);
        if (isExpired(item)) {
            applyQualityRule(item, true);
        }
    }

    private boolean isMalformed(Item item) {
        return item == null || item.name == null;
    }

    private boolean isLegendary(Item item) {
        return SULFURAS.equals(item.name);
    }

    private boolean isExpired(Item item) {
        return item.sellIn < 0;
    }

    private boolean isConjured(Item item) {
        return item.name.startsWith(CONJURED_PREFIX);
    }

    /** Decrements sellIn, never wrapping around below Integer.MIN_VALUE. */
    private void ageOneDay(Item item) {
        if (item.sellIn > Integer.MIN_VALUE) {
            item.sellIn--;
        }
    }

    private void applyQualityRule(Item item, boolean expired) {
        switch (item.name) {
            case AGED_BRIE:
                increaseQuality(item, BRIE_INCREMENT);
                break;
            case BACKSTAGE_PASSES:
                if (expired) {
                    item.quality = MIN_QUALITY;
                } else {
                    increaseQuality(item, passesIncrement(item.sellIn));
                }
                break;
            default:
                decreaseQuality(item, degradeRate(item));
        }
    }

    private int passesIncrement(int sellIn) {
        if (sellIn < PASSES_TRIPLE_THRESHOLD) {
            return PASSES_TRIPLE_INCREMENT;
        }
        if (sellIn < PASSES_DOUBLE_THRESHOLD) {
            return PASSES_DOUBLE_INCREMENT;
        }
        return PASSES_DEFAULT_INCREMENT;
    }

    private int degradeRate(Item item) {
        return isConjured(item) ? CONJURED_DEGRADE_RATE : NORMAL_DEGRADE_RATE;
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
