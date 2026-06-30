package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    // NEW FAILING TEST: Conjured items degrade 2 before sellIn
    @Test
    void should_degradeConjuredByTwo_when_beforeSellIn() {
        Item[] items = new Item[]{new Item("Conjured Mana Cake", 5, 10)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(8, items[0].quality);
    }

    // Characterization tests (pass immediately)

    // Normal item before sellIn: quality -1
    @Test
    void should_degradeNormalByOne_when_beforeSellIn() {
        Item[] items = new Item[]{new Item("Normal Item", 5, 10)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(9, items[0].quality);
        assertEquals(4, items[0].sellIn);
    }

    // Normal item after sellIn: quality -2
    @Test
    void should_degradeNormalByTwo_when_afterSellIn() {
        Item[] items = new Item[]{new Item("Normal Item", 0, 10)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(8, items[0].quality);
        assertEquals(-1, items[0].sellIn);
    }

    // Normal item quality never below 0
    @Test
    void should_notDegradeBelowZero_when_alreadyZero() {
        Item[] items = new Item[]{new Item("Normal Item", 5, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, items[0].quality);
    }

    // Aged Brie before sellIn: quality +1
    @Test
    void should_increaseAgedBrieByOne_when_beforeSellIn() {
        Item[] items = new Item[]{new Item("Aged Brie", 5, 10)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(11, items[0].quality);
    }

    // Aged Brie after sellIn: quality +2
    @Test
    void should_increaseAgedBrieByTwo_when_afterSellIn() {
        Item[] items = new Item[]{new Item("Aged Brie", 0, 10)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(12, items[0].quality);
        assertEquals(-1, items[0].sellIn);
    }

    // Aged Brie quality never above 50
    @Test
    void should_notIncreaseAgedBrieAboveFifty_when_atFifty() {
        Item[] items = new Item[]{new Item("Aged Brie", 5, 50)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, items[0].quality);
    }

    // Backstage > 10 days: +1
    @Test
    void should_increaseBackstageByOne_when_moreThanTenDays() {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(21, items[0].quality);
    }

    // Backstage <= 10 days: +2
    @Test
    void should_increaseBackstageByTwo_when_tenDaysOrLess() {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(22, items[0].quality);
    }

    // Backstage <= 5 days: +3
    @Test
    void should_increaseBackstageByThree_when_fiveDaysOrLess() {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(23, items[0].quality);
    }

    // Backstage after concert (sellIn < 0): quality drops to 0
    @Test
    void should_dropBackstageToZero_when_afterSellIn() {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, items[0].quality);
        assertEquals(-1, items[0].sellIn);
    }

    // Sulfuras: quality and sellIn never change
    @Test
    void should_notChangeSulfuras_when_updateQuality() {
        Item[] items = new Item[]{new Item("Sulfuras, Hand of Ragnaros", 0, 80)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(80, items[0].quality);
        assertEquals(0, items[0].sellIn);
    }

    // Sulfuras with negative sellIn: also unchanged
    @Test
    void should_notChangeSulfuras_when_sellInNegative() {
        Item[] items = new Item[]{new Item("Sulfuras, Hand of Ragnaros", -1, 80)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(80, items[0].quality);
        assertEquals(-1, items[0].sellIn);
    }

    // Backstage capped at 50 before sellIn
    @Test
    void should_notIncreaseBackstageAboveFifty_when_atFifty() {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 5, 50)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, items[0].quality);
    }

    // Conjured after sellIn: -4
    @Test
    void should_degradeConjuredByFour_when_afterSellIn() {
        Item[] items = new Item[]{new Item("Conjured Mana Cake", 0, 10)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        // existing code: not a special item, so path for normal item before sellIn with quality > 0 && not Sulfuras -> quality -1
        // quality before sellIn decrement: 10 -> 9 (since not Aged Brie/Backstage)
        // sellIn: 0 -> -1
        // sellIn < 0: item name not Aged Brie, not Backstage, quality > 0, not Sulfuras -> quality -1: 9 -> 8
        // expected = 6 (refactored behavior: Conjured items degrade 2 before sellIn and 4 after)
        assertEquals(6, items[0].quality);
    }

    // Conjured quality never below 0
    @Test
    void should_notDegradeConjuredBelowZero_when_alreadyZero() {
        Item[] items = new Item[]{new Item("Conjured Mana Cake", 5, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, items[0].quality);
    }
}
