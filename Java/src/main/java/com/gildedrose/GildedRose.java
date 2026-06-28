package com.gildedrose;

class GildedRose {
    Item[] items;

    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String CONJURED_PREFIX = "Conjured";
    private static final int MIN_QUALITY = 0;
    private static final int MAX_QUALITY = 50;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            Item item = items[i];
            if (item.name.equals(SULFURAS)) {
                continue;
            }
            updateItem(item);
        }
    }

    private void updateItem(Item item) {
        if (item.name.equals(AGED_BRIE)) {
            increaseQuality(item, 1);
            if (item.sellIn <= 0) {
                increaseQuality(item, 1);
            }
        } else if (item.name.equals(BACKSTAGE)) {
            if (item.sellIn <= 0) {
                item.quality = 0;
            } else {
                increaseQuality(item, 1);
                if (item.sellIn <= 10) {
                    increaseQuality(item, 1);
                }
                if (item.sellIn <= 5) {
                    increaseQuality(item, 1);
                }
            }
        } else {
            decreaseQuality(item, degradeRate(item));
            if (item.sellIn <= 0) {
                decreaseQuality(item, degradeRate(item));
            }
        }
        item.sellIn = item.sellIn - 1;
    }

    private void increaseQuality(Item item, int amount) {
        item.quality = Math.min(MAX_QUALITY, item.quality + amount);
    }

    private void decreaseQuality(Item item, int amount) {
        item.quality = Math.max(MIN_QUALITY, item.quality - amount);
    }

    private int degradeRate(Item item) {
        if (item.name.startsWith(CONJURED_PREFIX)) {
            return 2;
        }
        return 1;
    }
}