package com.gildedrose;

import java.util.Arrays;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            if (!item.name.equals("Aged Brie")
                    && !item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                decreaseQuality(item);
            } else {
                increaseQuality(item);

                if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                    handleBackstagePasses(item);
                }
            }

            if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
                item.sellIn = item.sellIn - 1;
            }

            if (item.sellIn < 0) {
                if (!item.name.equals("Aged Brie")) {
                    if (!item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        decreaseQuality(item);
                    } else {
                        item.quality = 0;
                    }
                } else {
                    increaseQuality(item);
                }
            }

            // Ensure quality does not go below 0
            if (item.quality < 0) {
                item.quality = 0;
            }
        }
    }

    private void decreaseQuality(Item item) {
        if (item.name.startsWith("Conjured")) {
            item.quality -= 2;
        } else {
            item.quality--;
        }
    }

    private void increaseQuality(Item item) {
        if (item.quality < 50) {
            item.quality++;
        }
    }

    private void handleBackstagePasses(Item item) {
        if (item.sellIn < 11) {
            increaseQuality(item);
        }
        if (item.sellIn < 6) {
            increaseQuality(item);
        }
    }
}