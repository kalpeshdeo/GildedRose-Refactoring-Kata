package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    // ========== Conjured (new feature) ==========

    @Test
    void should_degrade_by_2_before_sellIn_when_conjured() {
        Item[] items = new Item[]{new Item("Conjured Mana Cake", 5, 10)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(8, app.items[0].quality);
        assertEquals(4, app.items[0].sellIn);
    }

    @Test
    void should_degrade_by_4_after_sellIn_when_conjured() {
        Item[] items = new Item[]{new Item("Conjured Mana Cake", 0, 10)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(6, app.items[0].quality);
        assertEquals(-1, app.items[0].sellIn);
    }

    @Test
    void should_not_degrade_below_0_when_conjured() {
        Item[] items = new Item[]{new Item("Conjured Mana Cake", 0, 1)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }

    // ========== Characterization tests (existing behavior, all pass) ==========

    private Item updateOnce(Item item) {
        GildedRose app = new GildedRose(new Item[]{item});
        app.updateQuality();
        return app.items[0];
    }

    @Test
    void should_degrade_by_1_when_normal_before_sellIn() {
        Item result = updateOnce(new Item("foo", 5, 10));
        assertEquals(9, result.quality);
        assertEquals(4, result.sellIn);
    }

    @Test
    void should_degrade_by_2_when_normal_after_sellIn() {
        Item result = updateOnce(new Item("foo", 0, 10));
        assertEquals(8, result.quality);
        assertEquals(-1, result.sellIn);
    }

    @Test
    void should_not_degrade_below_0_when_normal() {
        Item result = updateOnce(new Item("foo", 0, 0));
        assertEquals(0, result.quality);
    }

    @Test
    void should_increase_by_1_when_aged_brie_before_sellIn() {
        Item result = updateOnce(new Item("Aged Brie", 5, 10));
        assertEquals(11, result.quality);
        assertEquals(4, result.sellIn);
    }

    @Test
    void should_increase_by_2_when_aged_brie_after_sellIn() {
        Item result = updateOnce(new Item("Aged Brie", 0, 10));
        assertEquals(12, result.quality);
        assertEquals(-1, result.sellIn);
    }

    @Test
    void should_cap_at_50_when_aged_brie() {
        Item result = updateOnce(new Item("Aged Brie", 5, 50));
        assertEquals(50, result.quality);
        assertEquals(4, result.sellIn);
    }

    @Test
    void should_increase_by_1_when_backstage_more_than_10_days() {
        Item result = updateOnce(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
        assertEquals(21, result.quality);
        assertEquals(14, result.sellIn);
    }

    @Test
    void should_increase_by_2_when_backstage_10_or_less_days() {
        Item result = updateOnce(new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20));
        assertEquals(22, result.quality);
        assertEquals(9, result.sellIn);
    }

    @Test
    void should_increase_by_3_when_backstage_5_or_less_days() {
        Item result = updateOnce(new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20));
        assertEquals(23, result.quality);
        assertEquals(4, result.sellIn);
    }

    @Test
    void should_drop_to_0_when_backstage_after_sellIn() {
        Item result = updateOnce(new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20));
        assertEquals(0, result.quality);
        assertEquals(-1, result.sellIn);
    }

    @Test
    void should_cap_at_50_when_backstage() {
        Item result = updateOnce(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 50));
        assertEquals(50, result.quality);
        assertEquals(14, result.sellIn);
    }

    @Test
    void should_not_change_quality_when_sulfuras() {
        Item result = updateOnce(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
        assertEquals(80, result.quality);
        assertEquals(0, result.sellIn);
    }

    @Test
    void should_not_change_sellIn_when_sulfuras() {
        Item result = updateOnce(new Item("Sulfuras, Hand of Ragnaros", -1, 80));
        assertEquals(80, result.quality);
        assertEquals(-1, result.sellIn);
    }
}