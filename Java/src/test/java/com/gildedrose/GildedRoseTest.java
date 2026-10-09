package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    private static final String BRIE = "Aged Brie";
    private static final String PASS = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private static final String CONJURED = "Conjured Mana Cake";

    private Item update(String name, int sellIn, int quality) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        return app.items[0];
    }

    private void assertState(Item item, int expectedSellIn, int expectedQuality) {
        assertEquals(expectedSellIn, item.sellIn);
        assertEquals(expectedQuality, item.quality);
    }

    @Test
    void normal_item_degrades_by_one_before_sell_date() {
        assertState(update("foo", 10, 20), 9, 19);
    }

    @Test
    void normal_item_degrades_by_two_after_sell_date() {
        assertState(update("foo", 0, 20), -1, 18);
    }

    @Test
    void normal_item_quality_never_negative_before_sell_date() {
        assertState(update("foo", 5, 0), 4, 0);
    }

    @Test
    void normal_item_quality_never_negative_after_sell_date() {
        assertState(update("foo", 0, 0), -1, 0);
    }

    @Test
    void normal_item_with_quality_one_after_sell_date_goes_to_zero() {
        assertState(update("foo", 0, 1), -1, 0);
    }

    @Test
    void brie_increases_by_one_before_sell_date() {
        assertState(update(BRIE, 10, 20), 9, 21);
    }

    @Test
    void brie_increases_by_two_after_sell_date() {
        assertState(update(BRIE, 0, 20), -1, 22);
    }

    @Test
    void brie_quality_capped_at_fifty() {
        assertState(update(BRIE, 10, 50), 9, 50);
    }

    @Test
    void brie_quality_capped_at_fifty_after_sell_date() {
        assertState(update(BRIE, 0, 49), -1, 50);
        assertState(update(BRIE, 0, 50), -1, 50);
    }

    @Test
    void backstage_pass_increases_by_one_with_eleven_days_left() {
        assertState(update(PASS, 11, 20), 10, 21);
    }

    @Test
    void backstage_pass_increases_by_two_with_ten_days_left() {
        assertState(update(PASS, 10, 20), 9, 22);
    }

    @Test
    void backstage_pass_increases_by_two_with_six_days_left() {
        assertState(update(PASS, 6, 20), 5, 22);
    }

    @Test
    void backstage_pass_increases_by_three_with_five_days_left() {
        assertState(update(PASS, 5, 20), 4, 23);
    }

    @Test
    void backstage_pass_increases_by_three_with_one_day_left() {
        assertState(update(PASS, 1, 20), 0, 23);
    }

    @Test
    void backstage_pass_drops_to_zero_after_concert() {
        assertState(update(PASS, 0, 20), -1, 0);
    }

    @Test
    void backstage_pass_quality_capped_at_fifty() {
        assertState(update(PASS, 10, 49), 9, 50);
        assertState(update(PASS, 5, 48), 4, 50);
        assertState(update(PASS, 5, 49), 4, 50);
        assertState(update(PASS, 15, 50), 14, 50);
    }

    @Test
    void sulfuras_never_changes() {
        assertState(update(SULFURAS, 0, 80), 0, 80);
    }

    @Test
    void sulfuras_with_negative_sell_in_never_changes() {
        assertState(update(SULFURAS, -1, 80), -1, 80);
    }

    @Test
    void conjured_item_degrades_by_two_before_sell_date() {
        assertState(update(CONJURED, 10, 20), 9, 18);
    }

    @Test
    void conjured_item_degrades_by_four_after_sell_date() {
        assertState(update(CONJURED, 0, 20), -1, 16);
    }

    @Test
    void conjured_item_quality_never_negative() {
        assertState(update(CONJURED, 5, 1), 4, 0);
    }
}
