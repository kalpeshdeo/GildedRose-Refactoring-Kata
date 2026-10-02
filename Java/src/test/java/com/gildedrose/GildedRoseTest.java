package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    private Item updateOnce(Item item) {
        GildedRose app = new GildedRose(new Item[] { item });
        app.updateQuality();
        return app.items[0];
    }

    // ------------------------------------------------------------------
    // New feature: Conjured items degrade twice as fast as normal items
    // ------------------------------------------------------------------

    @Test
    void should_degrade_conjured_item_quality_by_two_when_before_sell_by() {
        Item updated = updateOnce(new Item("Conjured Mana Cake", 3, 6));
        assertEquals(4, updated.quality);
    }

    @Test
    void should_degrade_conjured_item_quality_by_four_when_after_sell_by() {
        Item updated = updateOnce(new Item("Conjured Mana Cake", 0, 6));
        assertEquals(2, updated.quality);
    }

    // ------------------------------------------------------------------
    // Characterization: normal items
    // ------------------------------------------------------------------

    @Test
    void should_degrade_normal_item_quality_by_one_when_before_sell_by() {
        Item updated = updateOnce(new Item("+5 Dexterity Vest", 10, 20));
        assertEquals(19, updated.quality);
    }

    @Test
    void should_decrement_normal_item_sell_in_by_one() {
        Item updated = updateOnce(new Item("+5 Dexterity Vest", 10, 20));
        assertEquals(9, updated.sellIn);
    }

    @Test
    void should_degrade_normal_item_quality_by_two_when_sell_by_passed() {
        Item updated = updateOnce(new Item("+5 Dexterity Vest", 0, 20));
        assertEquals(18, updated.quality);
    }

    @Test
    void should_keep_normal_item_quality_at_zero_when_already_zero() {
        Item updated = updateOnce(new Item("+5 Dexterity Vest", 5, 0));
        assertEquals(0, updated.quality);
    }

    @Test
    void should_not_let_normal_item_quality_go_below_zero_when_it_would_drop_past_floor() {
        Item updated = updateOnce(new Item("+5 Dexterity Vest", 0, 1));
        assertEquals(0, updated.quality);
    }

    // ------------------------------------------------------------------
    // Characterization: Aged Brie
    // ------------------------------------------------------------------

    @Test
    void should_increase_brie_quality_by_one_when_before_sell_by() {
        Item updated = updateOnce(new Item("Aged Brie", 2, 0));
        assertEquals(1, updated.quality);
    }

    @Test
    void should_increase_brie_quality_by_two_when_sell_by_passed() {
        Item updated = updateOnce(new Item("Aged Brie", 0, 0));
        assertEquals(2, updated.quality);
    }

    @Test
    void should_not_increase_brie_quality_above_fifty_when_at_fifty() {
        Item updated = updateOnce(new Item("Aged Brie", 2, 50));
        assertEquals(50, updated.quality);
    }

    @Test
    void should_not_increase_brie_quality_above_fifty_when_after_sell_by() {
        Item updated = updateOnce(new Item("Aged Brie", -1, 49));
        assertEquals(50, updated.quality);
    }

    // ------------------------------------------------------------------
    // Characterization: Sulfuras (legendary - must NEVER change)
    // ------------------------------------------------------------------

    @Test
    void should_leave_sulfuras_unchanged_when_updated() {
        Item updated = updateOnce(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
        assertEquals("Sulfuras, Hand of Ragnaros, 0, 80", updated.toString());
    }

    @Test
    void should_leave_sulfuras_unchanged_when_sell_in_negative() {
        Item updated = updateOnce(new Item("Sulfuras, Hand of Ragnaros", -1, 80));
        assertEquals("Sulfuras, Hand of Ragnaros, -1, 80", updated.toString());
    }

    // ------------------------------------------------------------------
    // Characterization: Backstage passes
    // ------------------------------------------------------------------

    @Test
    void should_increase_backstage_quality_by_one_when_more_than_ten_days() {
        Item updated = updateOnce(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
        assertEquals(21, updated.quality);
    }

    @Test
    void should_increase_backstage_quality_by_two_when_ten_days_or_less() {
        Item updated = updateOnce(new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20));
        assertEquals(22, updated.quality);
    }

    @Test
    void should_increase_backstage_quality_by_two_when_six_days() {
        Item updated = updateOnce(new Item("Backstage passes to a TAFKAL80ETC concert", 6, 20));
        assertEquals(22, updated.quality);
    }

    @Test
    void should_increase_backstage_quality_by_three_when_five_days_or_less() {
        Item updated = updateOnce(new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20));
        assertEquals(23, updated.quality);
    }

    @Test
    void should_drop_backstage_quality_to_zero_when_concert_passed() {
        Item updated = updateOnce(new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20));
        assertEquals(0, updated.quality);
    }

    @Test
    void should_not_increase_backstage_quality_above_fifty() {
        Item updated = updateOnce(new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49));
        assertEquals(50, updated.quality);
    }
}
