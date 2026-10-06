package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    private static final String BRIE = "Aged Brie";
    private static final String PASSES = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String CONJURED = "Conjured Mana Cake";

    private Item update(String name, int sellIn, int quality) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        return app.items[0];
    }

    @Test
    void normal_item_degrades_by_one_before_sell_date() {
        Item item = update("+5 Dexterity Vest", 10, 20);
        assertEquals(9, item.sellIn);
        assertEquals(19, item.quality);
    }

    @Test
    void normal_item_degrades_twice_as_fast_after_sell_date() {
        Item item = update("+5 Dexterity Vest", 0, 10);
        assertEquals(-1, item.sellIn);
        assertEquals(8, item.quality);
    }

    @Test
    void normal_item_quality_never_negative() {
        Item item = update("+5 Dexterity Vest", 5, 0);
        assertEquals(4, item.sellIn);
        assertEquals(0, item.quality);
    }

    @Test
    void normal_item_expired_with_quality_one_goes_to_zero() {
        Item item = update("+5 Dexterity Vest", 0, 1);
        assertEquals(-1, item.sellIn);
        assertEquals(0, item.quality);
    }

    @Test
    void brie_increases_by_one_before_sell_date() {
        Item item = update(BRIE, 10, 20);
        assertEquals(9, item.sellIn);
        assertEquals(21, item.quality);
    }

    @Test
    void brie_increases_by_two_after_sell_date() {
        Item item = update(BRIE, 0, 10);
        assertEquals(-1, item.sellIn);
        assertEquals(12, item.quality);
    }

    @Test
    void brie_quality_capped_at_fifty() {
        Item item = update(BRIE, 5, 50);
        assertEquals(4, item.sellIn);
        assertEquals(50, item.quality);
    }

    @Test
    void brie_expired_quality_capped_at_fifty() {
        Item item = update(BRIE, 0, 49);
        assertEquals(-1, item.sellIn);
        assertEquals(50, item.quality);
    }

    @Test
    void sulfuras_never_changes() {
        Item item = update(SULFURAS, 0, 80);
        assertEquals(0, item.sellIn);
        assertEquals(80, item.quality);
    }

    @Test
    void sulfuras_with_negative_sell_in_never_changes() {
        Item item = update(SULFURAS, -1, 80);
        assertEquals(-1, item.sellIn);
        assertEquals(80, item.quality);
    }

    @Test
    void passes_increase_by_one_with_more_than_ten_days() {
        Item item = update(PASSES, 15, 20);
        assertEquals(14, item.sellIn);
        assertEquals(21, item.quality);
    }

    @Test
    void passes_increase_by_one_at_eleven_days() {
        Item item = update(PASSES, 11, 20);
        assertEquals(10, item.sellIn);
        assertEquals(21, item.quality);
    }

    @Test
    void passes_increase_by_two_at_ten_days() {
        Item item = update(PASSES, 10, 20);
        assertEquals(9, item.sellIn);
        assertEquals(22, item.quality);
    }

    @Test
    void passes_increase_by_two_at_six_days() {
        Item item = update(PASSES, 6, 20);
        assertEquals(5, item.sellIn);
        assertEquals(22, item.quality);
    }

    @Test
    void passes_increase_by_three_at_five_days() {
        Item item = update(PASSES, 5, 20);
        assertEquals(4, item.sellIn);
        assertEquals(23, item.quality);
    }

    @Test
    void passes_increase_by_three_at_one_day() {
        Item item = update(PASSES, 1, 20);
        assertEquals(0, item.sellIn);
        assertEquals(23, item.quality);
    }

    @Test
    void passes_drop_to_zero_after_concert() {
        Item item = update(PASSES, 0, 20);
        assertEquals(-1, item.sellIn);
        assertEquals(0, item.quality);
    }

    @Test
    void passes_quality_capped_at_fifty_with_two_increment() {
        Item item = update(PASSES, 10, 49);
        assertEquals(9, item.sellIn);
        assertEquals(50, item.quality);
    }

    @Test
    void passes_quality_capped_at_fifty_with_three_increment() {
        Item item = update(PASSES, 5, 48);
        assertEquals(4, item.sellIn);
        assertEquals(50, item.quality);
    }

    @Test
    void passes_at_fifty_stay_at_fifty() {
        Item item = update(PASSES, 5, 50);
        assertEquals(4, item.sellIn);
        assertEquals(50, item.quality);
    }

    @Test
    void conjured_item_degrades_by_two_before_sell_date() {
        Item item = update(CONJURED, 5, 10);
        assertEquals(4, item.sellIn);
        assertEquals(8, item.quality);
    }

    @Test
    void conjured_item_degrades_by_four_after_sell_date() {
        Item item = update(CONJURED, 0, 10);
        assertEquals(-1, item.sellIn);
        assertEquals(6, item.quality);
    }

    @Test
    void conjured_item_quality_never_negative() {
        Item item = update(CONJURED, 5, 1);
        assertEquals(4, item.sellIn);
        assertEquals(0, item.quality);
    }

    @Test
    void conjured_item_expired_quality_never_negative() {
        Item item = update(CONJURED, 0, 3);
        assertEquals(-1, item.sellIn);
        assertEquals(0, item.quality);
    }

}
