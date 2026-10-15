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

    private void assertState(Item item, int sellIn, int quality) {
        assertEquals(sellIn, item.sellIn, "sellIn");
        assertEquals(quality, item.quality, "quality");
    }

    @Test
    void should_degrade_conjured_item_twice_as_fast_before_sell_date() {
        assertState(update(CONJURED, 10, 20), 9, 18);
    }

    @Test
    void should_degrade_conjured_item_four_per_day_after_sell_date() {
        assertState(update(CONJURED, 0, 20), -1, 16);
    }

    @Test
    void should_not_let_conjured_quality_go_negative() {
        assertState(update(CONJURED, 10, 1), 9, 0);
    }

    @Test
    void should_not_let_expired_conjured_quality_go_negative() {
        assertState(update(CONJURED, 0, 3), -1, 0);
    }

    @Test
    void should_degrade_normal_item_by_one() {
        assertState(update("foo", 10, 20), 9, 19);
    }

    @Test
    void should_degrade_normal_item_twice_as_fast_after_sell_date() {
        assertState(update("foo", 0, 20), -1, 18);
    }

    @Test
    void should_not_let_normal_quality_go_negative() {
        assertState(update("foo", 5, 0), 4, 0);
    }

    @Test
    void should_not_let_expired_normal_quality_go_negative() {
        assertState(update("foo", 0, 1), -1, 0);
    }

    @Test
    void should_increase_brie_quality() {
        assertState(update(BRIE, 5, 10), 4, 11);
    }

    @Test
    void should_cap_brie_quality_at_fifty() {
        assertState(update(BRIE, 5, 50), 4, 50);
    }

    @Test
    void should_increase_expired_brie_by_two() {
        assertState(update(BRIE, 0, 10), -1, 12);
    }

    @Test
    void should_cap_expired_brie_at_fifty() {
        assertState(update(BRIE, 0, 49), -1, 50);
    }

    @Test
    void should_increase_passes_by_one_with_more_than_ten_days() {
        assertState(update(PASSES, 15, 20), 14, 21);
        assertState(update(PASSES, 11, 20), 10, 21);
    }

    @Test
    void should_increase_passes_by_two_with_ten_days_or_less() {
        assertState(update(PASSES, 10, 20), 9, 22);
        assertState(update(PASSES, 6, 20), 5, 22);
    }

    @Test
    void should_increase_passes_by_three_with_five_days_or_less() {
        assertState(update(PASSES, 5, 20), 4, 23);
        assertState(update(PASSES, 1, 20), 0, 23);
    }

    @Test
    void should_drop_passes_to_zero_after_concert() {
        assertState(update(PASSES, 0, 20), -1, 0);
    }

    @Test
    void should_cap_passes_quality_at_fifty() {
        assertState(update(PASSES, 10, 49), 9, 50);
        assertState(update(PASSES, 5, 48), 4, 50);
        assertState(update(PASSES, 15, 50), 14, 50);
    }

    @Test
    void should_never_change_sulfuras() {
        assertState(update(SULFURAS, 0, 80), 0, 80);
        assertState(update(SULFURAS, -1, 80), -1, 80);
    }
}
