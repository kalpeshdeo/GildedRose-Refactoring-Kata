package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            updateItem(items[i]);
        }
    }

    private void updateItem(Item item) {
        if (isLegendary(item)) {
            return;
        }

        updateQualityBeforeExpiration(item);
        updateSellIn(item);
        updateQualityAfterExpiration(item);
    }

    private boolean isLegendary(Item item) {
        String name = item.name;
        return name.equals("Sulfuras, Hand of Ragnaros");
    }

    private void updateQualityBeforeExpiration(Item item) {
        String name = item.name;
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
        String name = item.name;
        return name.equals("Conjured Mana Cake");
    }

    private boolean isAgedBrie(Item item) {
        String name = item.name;
        return name.equals("Aged Brie");
    }

    private boolean isBackstagePass(Item item) {
        String name = item.name;
        return name.equals("Backstage passes to a TAFKAL80ETC concert");
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
            String name = item.name;
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
