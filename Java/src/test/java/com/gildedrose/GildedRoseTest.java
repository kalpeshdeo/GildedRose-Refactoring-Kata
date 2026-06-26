package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    private static int qualityAfterOneDay(String name, int sellIn, int quality) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        new GildedRose(items).updateQuality();
        return items[0].quality;
    }

    private static int sellInAfterOneDay(String name, int sellIn, int quality) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        new GildedRose(items).updateQuality();
        return items[0].sellIn;
    }

    // --- Normal items ---
    @Test
    void should_degrade_quality_by_one_when_normal_item_before_sellIn() {
        assertEquals(9, qualityAfterOneDay("Elixir of the Mongoose", 5, 10));
    }

    @Test
    void should_degrade_quality_by_two_when_normal_item_after_sellIn() {
        assertEquals(8, qualityAfterOneDay("Elixir of the Mongoose", 0, 10));
    }

    @Test
    void should_never_drop_quality_below_zero() {
        assertEquals(0, qualityAfterOneDay("Elixir of the Mongoose", 5, 0));
    }

    @Test
    void should_decrement_sellIn_for_normal_item() {
        assertEquals(4, sellInAfterOneDay("Elixir of the Mongoose", 5, 10));
    }

    // --- Conjured items (the feature) ---
    @Test
    void should_degrade_conjured_quality_by_two_before_sellIn() {
        assertEquals(8, qualityAfterOneDay("Conjured Mana Cake", 5, 10));
    }

    @Test
    void should_degrade_conjured_quality_by_four_after_sellIn() {
        assertEquals(6, qualityAfterOneDay("Conjured Mana Cake", 0, 10));
    }

    @Test
    void should_never_drop_conjured_quality_below_zero() {
        assertEquals(0, qualityAfterOneDay("Conjured Mana Cake", 5, 1));
    }

    // --- Aged Brie ---
    @Test
    void should_increase_aged_brie_quality_over_time() {
        assertEquals(11, qualityAfterOneDay("Aged Brie", 5, 10));
    }

    @Test
    void should_increase_aged_brie_twice_as_fast_after_sellIn() {
        assertEquals(12, qualityAfterOneDay("Aged Brie", 0, 10));
    }

    @Test
    void should_cap_quality_at_fifty() {
        assertEquals(50, qualityAfterOneDay("Aged Brie", 5, 50));
    }

    // --- Backstage passes ---
    @Test
    void should_increase_backstage_by_one_when_more_than_ten_days() {
        assertEquals(11, qualityAfterOneDay(
                "Backstage passes to a TAFKAL80ETC concert", 11, 10));
    }

    @Test
    void should_increase_backstage_by_two_when_ten_days_or_less() {
        assertEquals(12, qualityAfterOneDay(
                "Backstage passes to a TAFKAL80ETC concert", 10, 10));
    }

    @Test
    void should_increase_backstage_by_three_when_five_days_or_less() {
        assertEquals(13, qualityAfterOneDay(
                "Backstage passes to a TAFKAL80ETC concert", 5, 10));
    }

    @Test
    void should_drop_backstage_to_zero_after_concert() {
        assertEquals(0, qualityAfterOneDay(
                "Backstage passes to a TAFKAL80ETC concert", 0, 20));
    }

    // --- Sulfuras (regression guard) ---
    @Test
    void should_never_change_sulfuras_quality() {
        assertEquals(80, qualityAfterOneDay("Sulfuras, Hand of Ragnaros", 0, 80));
    }

    @Test
    void should_never_change_sulfuras_sellIn() {
        assertEquals(0, sellInAfterOneDay("Sulfuras, Hand of Ragnaros", 0, 80));
    }
}
