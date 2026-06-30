package com.gildedrose;

class GildedRose {
    private static final String AGED_BRIE = "Aged Brie";
    private static final String BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String CONJURED_PREFIX = "Conjured";
    private static final int MIN_QUALITY = 0;
    private static final int MAX_QUALITY = 50;
    private static final int SULFURAS_QUALITY = 80;

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            Item item = items[i];
            if (item.name.equals(SULFURAS)) {
                continue;
            }
            item.sellIn = item.sellIn - 1;
            if (item.name.equals(AGED_BRIE)) {
                increase(item, item.sellIn < 0 ? 2 : 1);
            } else if (item.name.equals(BACKSTAGE)) {
                if (item.sellIn < 0) {
                    item.quality = 0;
                } else {
                    int rate = 1;
                    if (item.sellIn < 5) {
                        rate = 3;
                    } else if (item.sellIn < 10) {
                        rate = 2;
                    }
                    increase(item, rate);
                }
            } else {
                int rate = degradeRate(item.name);
                if (item.sellIn < 0) {
                    rate *= 2;
                }
                decrease(item, rate);
            }
        }
    }

    private int degradeRate(String name) {
        return name.startsWith(CONJURED_PREFIX) ? 2 : 1;
    }

    private void increase(Item item, int amount) {
        item.quality = Math.min(MAX_QUALITY, item.quality + amount);
    }

    private void decrease(Item item, int amount) {
        item.quality = Math.max(MIN_QUALITY, item.quality - amount);
    }
}
