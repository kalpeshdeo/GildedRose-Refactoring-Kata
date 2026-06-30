package com.gildedrose;

class GildedRose {
    static final String AGED_BRIE = "Aged Brie";
    static final String BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert";
    static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    static final String CONJURED_PREFIX = "Conjured ";
    static final int MIN_QUALITY = 0;
    static final int MAX_QUALITY = 50;

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (var item : items) {
            updateItem(item);
        }
    }

    private void updateItem(Item item) {
        if (item.name.equals(SULFURAS)) {
            return;
        }

        updateQualityBeforeSellIn(item);

        item.sellIn = item.sellIn - 1;

        if (item.sellIn < 0) {
            updateQualityAfterSellIn(item);
        }
    }

    private void updateQualityBeforeSellIn(Item item) {
        if (item.name.equals(AGED_BRIE)) {
            increase(item);
        } else if (item.name.equals(BACKSTAGE)) {
            increase(item);
            if (item.sellIn < 11) {
                increase(item);
            }
            if (item.sellIn < 6) {
                increase(item);
            }
        } else {
            decrease(item, degradeRate(item));
        }
    }

    private void updateQualityAfterSellIn(Item item) {
        if (item.name.equals(AGED_BRIE)) {
            increase(item);
        } else if (item.name.equals(BACKSTAGE)) {
            item.quality = 0;
        } else {
            decrease(item, degradeRate(item));
        }
    }

    private int degradeRate(Item item) {
        return item.name.startsWith(CONJURED_PREFIX) ? 2 : 1;
    }

    private void increase(Item item) {
        item.quality = Math.min(MAX_QUALITY, item.quality + 1);
    }

    private void decrease(Item item, int rate) {
        item.quality = Math.max(MIN_QUALITY, item.quality - rate);
    }
}
