package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            String name = items[i].name;
            if (name.equals("Sulfuras, Hand of Ragnaros")) {
                continue;
            }

            int qualityChange = 1;
            if (name.equals("Conjured Mana Cake")) {
                qualityChange = 2;
            }

            if (name.equals("Aged Brie") || name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                if (items[i].quality < 50) {
                    items[i].quality++;
                }
                if (name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                    if (items[i].sellIn < 11 && items[i].quality < 50) {
                        items[i].quality++;
                    }
                    if (items[i].sellIn < 6 && items[i].quality < 50) {
                        items[i].quality++;
                    }
                }
            } else {
                items[i].quality = Math.max(0, items[i].quality - qualityChange);
            }

            items[i].sellIn--;

            if (items[i].sellIn < 0) {
                if (name.equals("Aged Brie")) {
                    if (items[i].quality < 50) {
                        items[i].quality++;
                    }
                } else if (name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                    items[i].quality = 0;
                } else {
                    items[i].quality = Math.max(0, items[i].quality - qualityChange);
                }
            }
        }
    }
}
