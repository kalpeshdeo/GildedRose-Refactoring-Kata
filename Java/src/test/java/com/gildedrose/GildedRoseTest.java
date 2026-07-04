package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    // Helper: build one item, run one update cycle, return the item
    private Item updateOne(String name, int sellIn, int quality) {
        Item[] items = new Item[]{new Item(name, sellIn, quality)};
        new GildedRose(items).updateQuality();
        return items[0];
    }

    // ---- Characterization tests for EXISTING behavior ----

    @Test
    void should_degrade_quality_by_one_for_normal_item_before_sellIn() {
        Item result = updateOne("+5 Dexterity Vest", 10, 20);
        assertEquals(19, result.quality);
    }

    @Test
    void should_degrade_quality_by_two_for_normal_item_after_sellIn() {
        Item result = updateOne("+5 Dexterity Vest", 0, 20);
        assertEquals(18, result.quality);
    }

    @Test
    void should_not_let_quality_go_below_zero_normal_item() {
        Item result = updateOne("+5 Dexterity Vest", 0, 0);
        assertEquals(0, result.quality);
    }

    @Test
    void should_increase_aged_brie_quality_before_sellIn() {
        Item result = updateOne("Aged Brie", 5, 10);
        assertEquals(11, result.quality);
    }

    @Test
    void should_increase_aged_brie_quality_after_sellIn() {
        Item result = updateOne("Aged Brie", 0, 10);
        assertEquals(12, result.quality);
    }

    @Test
    void should_not_increase_aged_brie_quality_beyond_fifty() {
        Item result = updateOne("Aged Brie", 5, 50);
        assertEquals(50, result.quality);
    }

    @Test
    void should_not_change_sulfuras_quality_or_sellIn() {
        Item result = updateOne("Sulfuras, Hand of Ragnaros", 10, 80);
        assertEquals(10, result.sellIn);
        assertEquals(80, result.quality);
    }

    @Test
    void should_not_change_sulfuras_quality_or_sellIn_when_negative_sellIn() {
        Item result = updateOne("Sulfuras, Hand of Ragnaros", -1, 80);
        assertEquals(-1, result.sellIn);
        assertEquals(80, result.quality);
    }

    @Test
    void should_increase_backstage_pass_quality_by_one_when_sellIn_greater_than_ten() {
        Item result = updateOne("Backstage passes to a TAFKAL80ETC concert", 15, 20);
        assertEquals(21, result.quality);
    }

    @Test
    void should_increase_backstage_pass_quality_by_two_when_sellIn_ten_or_less() {
        Item result = updateOne("Backstage passes to a TAFKAL80ETC concert", 10, 20);
        assertEquals(22, result.quality);
    }

    @Test
    void should_increase_backstage_pass_quality_by_three_when_sellIn_five_or_less() {
        Item result = updateOne("Backstage passes to a TAFKAL80ETC concert", 5, 20);
        assertEquals(23, result.quality);
    }

    @Test
    void should_drop_backstage_pass_quality_to_zero_after_concert() {
        Item result = updateOne("Backstage passes to a TAFKAL80ETC concert", 0, 20);
        assertEquals(0, result.quality);
    }

    @Test
    void should_not_let_backstage_quality_exceed_fifty() {
        Item result = updateOne("Backstage passes to a TAFKAL80ETC concert", 5, 49);
        assertEquals(50, result.quality);
    }

    @Test
    void should_degrade_sellIn_by_one_for_normal_item() {
        Item result = updateOne("+5 Dexterity Vest", 10, 20);
        assertEquals(9, result.sellIn);
    }

    // ---- New FAILING test for Conjured items behavior ----

    @Test
    void should_degrade_conjured_quality_by_two_before_sellIn() {
        // This test expects the NEW behavior (Conjured items degrade twice as fast)
        // Current code degrades by 1, so this will fail until the feature is implemented
        Item result = updateOne("Conjured Mana Cake", 5, 10);
        assertEquals(8, result.quality);
    }
}