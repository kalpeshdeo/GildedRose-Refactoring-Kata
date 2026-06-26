package com.gildedrose;

class GildedRose {
    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String CONJURED_PREFIX = "Conjured";
    private static final int MIN_QUALITY = 0;
    private static final int MAX_QUALITY = 50;

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
            return; // legendary: sellIn and quality never change
        }

        applyDailyChange(item, item.sellIn);

        item.sellIn--;

        if (item.sellIn < 0) {
            applyExpiredChange(item);
        }
    }

    private void applyDailyChange(Item item, int sellIn) {
        if (item.name.equals(AGED_BRIE)) {
            increase(item, 1);
        } else if (item.name.equals(BACKSTAGE)) {
            increase(item, 1);
            if (sellIn < 11) {
                increase(item, 1);
            }
            if (sellIn < 6) {
                increase(item, 1);
            }
        } else {
            decrease(item, degradeRate(item));
        }
    }

    private void applyExpiredChange(Item item) {
        if (item.name.equals(AGED_BRIE)) {
            increase(item, 1);
        } else if (item.name.equals(BACKSTAGE)) {
            item.quality = MIN_QUALITY;
        } else {
            decrease(item, degradeRate(item));
        }
    }

    private int degradeRate(Item item) {
        return item.name.startsWith(CONJURED_PREFIX) ? 2 : 1;
    }

    private void increase(Item item, int amount) {
        item.quality = Math.min(MAX_QUALITY, item.quality + amount);
    }

    private void decrease(Item item, int amount) {
        item.quality = Math.max(MIN_QUALITY, item.quality - amount);
    }
}
