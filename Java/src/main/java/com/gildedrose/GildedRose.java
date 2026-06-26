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

        switch (item.name) {
            case AGED_BRIE -> increase(item, 1);
            case BACKSTAGE -> {
                increase(item, 1);
                if (item.sellIn < 11) {
                    increase(item, 1);
                }
                if (item.sellIn < 6) {
                    increase(item, 1);
                }
            }
            default -> decrease(item, degradeRate(item));
        }

        item.sellIn--;

        if (item.sellIn < 0) {
            switch (item.name) {
                case AGED_BRIE -> increase(item, 1);
                case BACKSTAGE -> item.quality = MIN_QUALITY;
                default -> decrease(item, degradeRate(item));
            }
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
