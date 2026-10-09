package com.gildedrose;

class GildedRose {
    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String CONJURED_PREFIX = "Conjured";

    private static final int MIN_QUALITY = 0;
    private static final int MAX_QUALITY = 50;
    private static final int PASS_DOUBLE_RATE_DAYS = 11;
    private static final int PASS_TRIPLE_RATE_DAYS = 6;

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

        updateQualityBeforeSellDateChange(item);
        item.sellIn = item.sellIn - 1;
        if (item.sellIn < 0) {
            updateExpiredQuality(item);
        }
    }

    private void updateQualityBeforeSellDateChange(Item item) {
        if (AGED_BRIE.equals(item.name)) {
            changeQuality(item, 1);
        } else if (BACKSTAGE_PASS.equals(item.name)) {
            changeQuality(item, passIncrement(item.sellIn));
        } else {
            changeQuality(item, -degradationRate(item));
        }
    }

    private void updateExpiredQuality(Item item) {
        if (AGED_BRIE.equals(item.name)) {
            changeQuality(item, 1);
        } else if (BACKSTAGE_PASS.equals(item.name)) {
            item.quality = MIN_QUALITY;
        } else {
            changeQuality(item, -degradationRate(item));
        }
    }

    private int passIncrement(int sellIn) {
        int increment = 1;
        if (sellIn < PASS_DOUBLE_RATE_DAYS) {
            increment++;
        }
        if (sellIn < PASS_TRIPLE_RATE_DAYS) {
            increment++;
        }
        return increment;
    }

    private int degradationRate(Item item) {
        return item.name.startsWith(CONJURED_PREFIX) ? 2 : 1;
    }

    private void changeQuality(Item item, int delta) {
        item.quality = Math.max(MIN_QUALITY, Math.min(MAX_QUALITY, item.quality + delta));
    }
}
