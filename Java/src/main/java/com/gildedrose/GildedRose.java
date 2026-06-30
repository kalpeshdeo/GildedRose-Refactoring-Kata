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
        for (int i = 0; i < items.length; i++) {
            Item item = items[i];

            if (item.name.equals(SULFURAS)) {
                continue;
            }

            if (item.name.equals(AGED_BRIE)) {
                increase(item, 1);
            } else if (item.name.equals(BACKSTAGE_PASSES)) {
                if (item.sellIn <= 0) {
                    item.quality = 0;
                } else {
                    int rate = 1;
                    if (item.sellIn <= 5) {
                        rate = 3;
                    } else if (item.sellIn <= 10) {
                        rate = 2;
                    }
                    increase(item, rate);
                }
            } else {
                degrade(item, degradeRate(item));
            }

            item.sellIn--;

            if (item.sellIn < 0 && !item.name.equals(AGED_BRIE) && !item.name.equals(BACKSTAGE_PASSES)) {
                degrade(item, degradeRate(item));
            }
        }
    }

    private void increase(Item item, int amount) {
        item.quality = Math.min(MAX_QUALITY, item.quality + amount);
    }

    private void degrade(Item item, int amount) {
        item.quality = Math.max(MIN_QUALITY, item.quality - amount);
    }

    private int degradeRate(Item item) {
        return item.name.startsWith(CONJURED_PREFIX) ? 2 : 1;
    }
}
