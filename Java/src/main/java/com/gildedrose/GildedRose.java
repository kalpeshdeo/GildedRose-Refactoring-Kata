package com.gildedrose;

class GildedRose {
    Item[] items;

    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String CONJURED_PREFIX = "Conjured";
    private static final int MIN_QUALITY = 0;
    private static final int MAX_QUALITY = 50;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            if (item.name.equals(SULFURAS)) {
                continue;
            }

            if (item.name.equals(AGED_BRIE)) {
                increase(item, 1);
            } else if (item.name.equals(BACKSTAGE_PASSES)) {
                backstagePassUpdate(item);
            } else {
                decrease(item, degradeRate(item));
            }

            item.sellIn = item.sellIn - 1;

            if (item.sellIn < 0) {
                if (item.name.equals(AGED_BRIE)) {
                    increase(item, 1);
                } else if (item.name.equals(BACKSTAGE_PASSES)) {
                    item.quality = 0;
                } else {
                    decrease(item, degradeRate(item));
                }
            }
        }
    }

    private int degradeRate(Item item) {
        if (item.name.startsWith(CONJURED_PREFIX)) {
            return 2;
        }
        return 1;
    }

    private void increase(Item item, int amount) {
        item.quality = Math.min(MAX_QUALITY, item.quality + amount);
    }

    private void decrease(Item item, int amount) {
        item.quality = Math.max(MIN_QUALITY, item.quality - amount);
    }

    private void backstagePassUpdate(Item item) {
        int increaseAmount = 1;
        if (item.sellIn <= 10) {
            increaseAmount = 2;
        }
        if (item.sellIn <= 5) {
            increaseAmount = 3;
        }
        increase(item, increaseAmount);
    }
}