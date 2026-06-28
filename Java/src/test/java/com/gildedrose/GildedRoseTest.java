package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void foo() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("foo", app.items[0].name);
    }

    @Test
    void should_decrease_quality_by_one_for_normal_item_before_sellIn() {
        Item[] items = new Item[] { new Item("Elixir of the Mongoose", 5, 7) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(6, app.items[0].quality);
        assertEquals(4, app.items[0].sellIn);
    }

    @Test
    void should_decrease_quality_by_two_for_normal_item_after_sellIn() {
        Item[] items = new Item[] { new Item("Elixir of the Mongoose", 0, 7) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(5, app.items[0].quality);
        assertEquals(-1, app.items[0].sellIn);
    }

    @Test
    void should_not_decrease_quality_below_zero_for_normal_item() {
        Item[] items = new Item[] { new Item("Elixir of the Mongoose", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
        assertEquals(-1, app.items[0].sellIn);
    }

    @Test
    void should_increase_quality_for_aged_brie_before_sellIn() {
        Item[] items = new Item[] { new Item("Aged Brie", 2, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(1, app.items[0].quality);
        assertEquals(1, app.items[0].sellIn);
    }

    @Test
    void should_increase_quality_by_two_for_aged_brie_after_sellIn() {
        Item[] items = new Item[] { new Item("Aged Brie", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(2, app.items[0].quality);
        assertEquals(-1, app.items[0].sellIn);
    }

    @Test
    void should_not_increase_quality_above_50_for_aged_brie() {
        Item[] items = new Item[] { new Item("Aged Brie", 2, 50) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
        assertEquals(1, app.items[0].sellIn);
    }

    @Test
    void should_increase_quality_by_one_for_backstage_when_sellIn_greater_than_10() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(21, app.items[0].quality);
        assertEquals(14, app.items[0].sellIn);
    }

    @Test
    void should_increase_quality_by_two_for_backstage_when_sellIn_less_than_or_equal_to_10() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(22, app.items[0].quality);
        assertEquals(9, app.items[0].sellIn);
    }

    @Test
    void should_increase_quality_by_three_for_backstage_when_sellIn_less_than_or_equal_to_5() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(23, app.items[0].quality);
        assertEquals(4, app.items[0].sellIn);
    }

    @Test
    void should_drop_quality_to_zero_for_backstage_after_sellIn() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
        assertEquals(-1, app.items[0].sellIn);
    }

    @Test
    void should_not_change_sulfuras_quality_or_sellIn() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 0, 80) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(80, app.items[0].quality);
        assertEquals(0, app.items[0].sellIn);
    }

    @Test
    void should_not_change_sulfuras_negative_sellIn() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", -1, 80) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(80, app.items[0].quality);
        assertEquals(-1, app.items[0].sellIn);
    }

    @Test
    void should_decrease_conjured_quality_by_two_before_sellIn() {
        Item[] items = new Item[] { new Item("Conjured Mana Cake", 3, 6) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(4, app.items[0].quality);
        assertEquals(2, app.items[0].sellIn);
    }

    @Test
    void should_decrease_conjured_quality_by_four_after_sellIn() {
        Item[] items = new Item[] { new Item("Conjured Mana Cake", 0, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(6, app.items[0].quality);
        assertEquals(-1, app.items[0].sellIn);
    }

    @Test
    void should_not_decrease_conjured_quality_below_zero() {
        Item[] items = new Item[] { new Item("Conjured Mana Cake", 0, 1) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
        assertEquals(-1, app.items[0].sellIn);
    }
}