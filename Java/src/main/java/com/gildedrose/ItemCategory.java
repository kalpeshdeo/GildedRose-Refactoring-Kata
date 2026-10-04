package com.gildedrose;

enum ItemCategory {
    NORMAL,
    AGED_BRIE,
    BACKSTAGE_PASSES,
    SULFURAS;

    static final String AGED_BRIE_NAME = "Aged Brie";
    static final String BACKSTAGE_PASSES_NAME = "Backstage passes to a TAFKAL80ETC concert";
    static final String SULFURAS_NAME = "Sulfuras, Hand of Ragnaros";

    static ItemCategory of(Item item) {
        switch (item.name) {
            case AGED_BRIE_NAME:
                return AGED_BRIE;
            case BACKSTAGE_PASSES_NAME:
                return BACKSTAGE_PASSES;
            case SULFURAS_NAME:
                return SULFURAS;
            default:
                return NORMAL;
        }
    }
}
