package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    // ========== Conjured (new feature — this test will FAIL) ==========

    @Test
    void should_degrade_by_2_before_sellIn_when_conjured() {
        Item[] items = new Item[]{new Item("Conjured Mana Cake", 5, 10)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        // Before sellIn, Conjured degrades by 2: 10 - 2 = 8
        assertEquals(8, app.items[0].quality);
        assertEquals(4, app.items[0].sellIn);
    }

    // ========== Characterization tests (existing behavior, all pass) ==========

    private Item updateOnce(Item item) {
        GildedRose app = new GildedRose(new Item[]{item});
        app.updateQuality();
        return app.items[0];
    }

    // Normal item: before sellIn, degrades by 1
    @Test
    void should_degrade_by_1_when_normal_before_sellIn() {
        Item result = updateOnce(new Item("foo", 5, 10));
        assertEquals(9, result.quality);
        assertEquals(4, result.sellIn);
    }

    // Normal item: after sellIn, degrades by 2 (but not below 0)
    @Test
    void should_degrade_by_2_when_normal_after_sellIn() {
        Item result = updateOnce(new Item("foo", 0, 10));
        assertEquals(8, result.quality);
        assertEquals(-1, result.sellIn);
    }

    // Normal item: quality never goes below 0
    @Test
    void should_not_degrade_below_0_when_normal() {
        Item result = updateOnce(new Item("foo", 0, 0));
        assertEquals(0, result.quality);
    }

    // Aged Brie: before sellIn, increases by 1, capped at 50
    @Test
    void should_increase_by_1_when_aged_brie_before_sellIn() {
        Item result = updateOnce(new Item("Aged Brie", 5, 10));
        assertEquals(11, result.quality);
        assertEquals(4, result.sellIn);
    }

    // Aged Brie: after sellIn, increases by 2, capped at 50
    @Test
    void should_increase_by_2_when_aged_brie_after_sellIn() {
        Item result = updateOnce(new Item("Aged Brie", 0, 10));
        assertEquals(12, result.quality);
        assertEquals(-1, result.sellIn);
    }

    // Aged Brie: quality capped at 50
    @Test
    void should_cap_at_50_when_aged_brie() {
        Item result = updateOnce(new Item("Aged Brie", 5, 50));
        assertEquals(50, result.quality);
        assertEquals(4, result.sellIn);
    }

    // Backstage passes: >10 days, increase by 1
    @Test
    void should_increase_by_1_when_backstage_more_than_10_days() {
        Item result = updateOnce(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
        assertEquals(21, result.quality);
        assertEquals(14, result.sellIn);
    }

    // Backstage passes: <=10 days, increase by 2
    @Test
    void should_increase_by_2_when_backstage_10_or_less_days() {
        Item result = updateOnce(new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20));
        assertEquals(22, result.quality);
        assertEquals(9, result.sellIn);
    }

    // Backstage passes: <=5 days, increase by 3
    @Test
    void should_increase_by_3_when_backstage_5_or_less_days() {
        Item result = updateOnce(new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20));
        assertEquals(23, result.quality);
        assertEquals(4, result.sellIn);
    }

    // Backstage passes: after concert, quality drops to 0
    @Test
    void should_drop_to_0_when_backstage_after_sellIn() {
        Item result = updateOnce(new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20));
        assertEquals(0, result.quality);
        assertEquals(-1, result.sellIn);
    }

    // Backstage passes: quality capped at 50
    @Test
    void should_cap_at_50_when_backstage() {
        Item result = updateOnce(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 50));
        assertEquals(50, result.quality);
        assertEquals(14, result.sellIn);
    }

    // Sulfuras: quality never changes
    @Test
    void should_not_change_quality_when_sulfuras() {
        Item result = updateOnce(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
        assertEquals(80, result.quality);
        assertEquals(0, result.sellIn);
    }

    // Sulfuras: sellIn never changes even if negative
    @Test
    void should_not_change_sellIn_when_sulfuras() {
        Item result = updateOnce(new Item("Sulfuras, Hand of Ragnaros", -1, 80));
        assertEquals(80, result.quality);
        assertEquals(-1, result.sellIn);
    }

    // Conjured: after sellIn, degrades by 4 but not below 0
    @Test
    void should_degrade_by_4_when_conjured_after_sellIn() {
        Item result = updateOnce(new Item("Conjured Mana Cake", 0, 10));
        // sellIn 0 -> -1; before sellIn? Actually sellIn=0 means before sellIn? In original code,
        // the "after sellIn" degradation happens when sellIn < 0. At sellIn=0, first degrade by 2 -> 8, then sellIn becomes -1, then after-sellIn degrade by 2 again -> 6.
        assertEquals(6, result.quality);
        assertEquals(-1, result.sellIn);
    }

    // Conjured: quality never goes below 0
    @Test
    void should_not_degrade_below_0_when_conjured() {
        Item result = updateOnce(new Item("Conjured Mana Cake", 0, 1));
        // Before sellIn (sellIn=0 -> before? Actually sellIn=0 is not <0 so first degrade: 1-2 = -1 clamped to 0 -> 0, sellIn becomes -1, then after-sellIn degrade: 0-2 = 0
        assertEquals(0, result.quality);
        assertEquals(-1, result.sellIn);
    }
}
