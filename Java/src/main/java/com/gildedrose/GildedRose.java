package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            if (!isLegendary(item)) {
                updateQualityBeforeExpiration(item);
                updateSellIn(item);
                updateQualityAfterExpiration(item);
            }
        }
    }

    private boolean isLegendary(Item item) {
        return "Sulfuras, Hand of Ragnaros".equals(item.name);
    }

    private void updateQualityBeforeExpiration(Item item) {
        if (isAgedBrie(item) || isBackstagePass(item)) {
            increaseQuality(item);
            if (isBackstagePass(item)) {
                if (item.sellIn < 11) increaseQuality(item);
                if (item.sellIn < 6) increaseQuality(item);
            }
        } else {
            decreaseQuality(item, getQualityDegradationRate(item));
        }
    }

    private int getQualityDegradationRate(Item item) {
        return isConjured(item) ? 2 : 1;
    }

    private boolean isConjured(Item item) {
        return item.name != null && item.name.startsWith("Conjured");
    }

    private boolean isAgedBrie(Item item) {
        return "Aged Brie".equals(item.name);
    }

    private boolean isBackstagePass(Item item) {
        return "Backstage passes to a TAFKAL80ETC concert".equals(item.name);
    }

    private void increaseQuality(Item item) {
        if (item.quality < 50) {
            item.quality++;
        }
    }

    private void decreaseQuality(Item item, int amount) {
        item.quality = Math.max(0, item.quality - amount);
    }

    private void updateSellIn(Item item) {
        item.sellIn--;
    }

    private void updateQualityAfterExpiration(Item item) {
        if (item.sellIn < 0) {
            if (isAgedBrie(item)) {
                increaseQuality(item);
            } else if (isBackstagePass(item)) {
                item.quality = 0;
            } else {
                decreaseQuality(item, getQualityDegradationRate(item));
            }
        }
    }
}