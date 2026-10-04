package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GildedRoseTest {

    private static final String BRIE = "Aged Brie";
    private static final String PASS = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String CONJURED = "Conjured Mana Cake";
    private static final String NORMAL = "+5 Dexterity Vest";

    private Item update(String name, int sellIn, int quality) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        return app.items[0];
    }

    // ---- New feature: Conjured ----

    @Test
    void conjured_before_sell_date_degrades_by_two_and_sellIn_decrements() {
        Item item = update(CONJURED, 5, 10);
        assertEquals(4, item.sellIn);
        assertEquals(8, item.quality);
    }

    @Test
    void conjured_on_sell_date_degrades_by_four() {
        assertEquals(6, update(CONJURED, 0, 10).quality);
    }

    @Test
    void conjured_after_sell_date_degrades_by_four() {
        assertEquals(6, update(CONJURED, -3, 10).quality);
    }

    @Test
    void conjured_quality_one_before_sell_date_clamps_to_zero() {
        assertEquals(0, update(CONJURED, 5, 1).quality);
    }

    @Test
    void conjured_quality_one_after_sell_date_clamps_to_zero() {
        assertEquals(0, update(CONJURED, 0, 1).quality);
    }

    @Test
    void conjured_quality_three_after_sell_date_clamps_to_zero() {
        assertEquals(0, update(CONJURED, 0, 3).quality);
    }

    @Test
    void conjured_zero_quality_stays_zero() {
        assertEquals(0, update(CONJURED, 5, 0).quality);
        assertEquals(0, update(CONJURED, 0, 0).quality);
    }

    @Test
    void conjured_quality_never_exceeds_fifty() {
        int quality = update(CONJURED, 5, 50).quality;
        assertTrue(quality <= 50);
        assertEquals(48, quality);
    }

    @Test
    void conjured_degrades_exactly_twice_as_fast_as_normal_over_ten_days() {
        Item[] items = new Item[] { new Item(CONJURED, 20, 30), new Item(NORMAL, 20, 30) };
        GildedRose app = new GildedRose(items);
        for (int day = 1; day <= 10; day++) {
            app.updateQuality();
            assertEquals(30 - 2 * day, app.items[0].quality);
            assertEquals(30 - day, app.items[1].quality);
        }
        assertEquals(10, app.items[0].quality);
        assertEquals(20, app.items[1].quality);
    }

    @Test
    void conjured_crossing_sell_date_then_reaching_zero() {
        Item[] items = new Item[] { new Item(CONJURED, 1, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].sellIn);
        assertEquals(8, app.items[0].quality);
        app.updateQuality();
        assertEquals(-1, app.items[0].sellIn);
        assertEquals(4, app.items[0].quality);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }

    // ---- Characterization of existing behavior ----

    @Test
    void normal_item_degrades_by_one_before_sell_date() {
        Item item = update(NORMAL, 10, 20);
        assertEquals(9, item.sellIn);
        assertEquals(19, item.quality);
    }

    @Test
    void normal_item_degrades_by_two_on_sell_date() {
        Item item = update(NORMAL, 0, 10);
        assertEquals(-1, item.sellIn);
        assertEquals(8, item.quality);
    }

    @Test
    void normal_item_degrades_by_two_after_sell_date() {
        assertEquals(8, update(NORMAL, -5, 10).quality);
    }

    @Test
    void normal_item_quality_never_negative() {
        assertEquals(0, update(NORMAL, 5, 0).quality);
        assertEquals(0, update(NORMAL, 0, 1).quality);
        assertEquals(0, update(NORMAL, 0, 0).quality);
    }

    @Test
    void aged_brie_increases_by_one_before_sell_date() {
        Item item = update(BRIE, 5, 10);
        assertEquals(4, item.sellIn);
        assertEquals(11, item.quality);
    }

    @Test
    void aged_brie_increases_by_two_after_sell_date() {
        assertEquals(12, update(BRIE, 0, 10).quality);
    }

    @Test
    void aged_brie_quality_capped_at_fifty() {
        assertEquals(50, update(BRIE, 5, 50).quality);
        assertEquals(50, update(BRIE, 0, 49).quality);
        assertEquals(50, update(BRIE, 0, 50).quality);
    }

    @Test
    void backstage_pass_increases_by_one_with_more_than_ten_days() {
        assertEquals(21, update(PASS, 15, 20).quality);
        assertEquals(21, update(PASS, 11, 20).quality);
    }

    @Test
    void backstage_pass_increases_by_two_with_ten_days_or_less() {
        assertEquals(22, update(PASS, 10, 20).quality);
        assertEquals(22, update(PASS, 6, 20).quality);
    }

    @Test
    void backstage_pass_increases_by_three_with_five_days_or_less() {
        assertEquals(23, update(PASS, 5, 20).quality);
        assertEquals(23, update(PASS, 1, 20).quality);
    }

    @Test
    void backstage_pass_drops_to_zero_after_concert() {
        Item item = update(PASS, 0, 20);
        assertEquals(-1, item.sellIn);
        assertEquals(0, item.quality);
    }

    @Test
    void backstage_pass_quality_capped_at_fifty() {
        assertEquals(50, update(PASS, 10, 49).quality);
        assertEquals(50, update(PASS, 5, 48).quality);
        assertEquals(50, update(PASS, 15, 50).quality);
    }

    @Test
    void sulfuras_never_changes() {
        Item item = update(SULFURAS, 0, 80);
        assertEquals(0, item.sellIn);
        assertEquals(80, item.quality);
        Item negative = update(SULFURAS, -1, 80);
        assertEquals(-1, negative.sellIn);
        assertEquals(80, negative.quality);
    }
}
