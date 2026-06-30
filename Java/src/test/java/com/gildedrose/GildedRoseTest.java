package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    private Item updateOne(Item item) {
        GildedRose app = new GildedRose(new Item[]{ item });
        app.updateQuality();
        return app.items[0];
    }

    @Test
    void should_degrade_quality_by_2_for_conjured_item_before_sellIn() {
        Item item = new Item("Conjured Mana Cake", 5, 10);
        item = updateOne(item);
        assertEquals(8, item.quality);
    }

    @Test
    void should_degrade_normal_item_by_1_before_sellIn() {
        Item item = new Item("foo", 10, 10);
        item = updateOne(item);
        assertEquals(9, item.quality);
        assertEquals(9, item.sellIn);
    }

    @Test
    void should_degrade_normal_item_by_2_after_sellIn() {
        Item item = new Item("foo", 0, 10);
        item = updateOne(item);
        assertEquals(8, item.quality);
        assertEquals(-1, item.sellIn);
    }

    @Test
    void should_not_make_normal_item_quality_negative() {
        Item item = new Item("foo", 0, 1);
        item = updateOne(item);
        assertEquals(0, item.quality);
    }

    @Test
    void should_increase_aged_brie_quality_by_1_before_sellIn() {
        Item item = new Item("Aged Brie", 5, 20);
        item = updateOne(item);
        assertEquals(21, item.quality);
        assertEquals(4, item.sellIn);
    }

    @Test
    void should_increase_aged_brie_quality_by_2_after_sellIn() {
        Item item = new Item("Aged Brie", 0, 20);
        item = updateOne(item);
        assertEquals(22, item.quality);
        assertEquals(-1, item.sellIn);
    }

    @Test
    void should_cap_aged_brie_quality_at_50() {
        Item item = new Item("Aged Brie", 10, 50);
        item = updateOne(item);
        assertEquals(50, item.quality);
        assertEquals(9, item.sellIn);
    }

    @Test
    void should_increase_backstage_quality_by_1_when_sellIn_gt_10() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 12, 20);
        item = updateOne(item);
        assertEquals(21, item.quality);
        assertEquals(11, item.sellIn);
    }

    @Test
    void should_increase_backstage_quality_by_2_when_sellIn_10_or_less() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20);
        item = updateOne(item);
        assertEquals(22, item.quality);
        assertEquals(9, item.sellIn);
    }

    @Test
    void should_increase_backstage_quality_by_3_when_sellIn_5_or_less() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20);
        item = updateOne(item);
        assertEquals(23, item.quality);
        assertEquals(4, item.sellIn);
    }

    @Test
    void should_drop_backstage_quality_to_0_after_sellIn() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20);
        item = updateOne(item);
        assertEquals(0, item.quality);
        assertEquals(-1, item.sellIn);
    }

    @Test
    void should_cap_backstage_quality_at_50_when_increasing() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 2, 49);
        item = updateOne(item);
        assertEquals(50, item.quality);
        assertEquals(1, item.sellIn);
    }

    @Test
    void should_not_change_sulfuras_quality_or_sellIn() {
        Item item = new Item("Sulfuras, Hand of Ragnaros", 10, 80);
        item = updateOne(item);
        assertEquals(80, item.quality);
        assertEquals(10, item.sellIn);
    }

    @Test
    void should_not_change_sulfuras_quality_or_sellIn_even_with_negative_sellIn() {
        Item item = new Item("Sulfuras, Hand of Ragnaros", -5, 80);
        item = updateOne(item);
        assertEquals(80, item.quality);
        assertEquals(-5, item.sellIn);
    }

    @Test
    void should_degrade_conjured_item_quality_by_4_after_sellIn() {
        Item item = new Item("Conjured Mana Cake", 0, 10);
        item = updateOne(item);
        assertEquals(6, item.quality);
        assertEquals(-1, item.sellIn);
    }

    @Test
    void should_not_make_conjured_item_quality_negative() {
        Item item = new Item("Conjured Mana Cake", 0, 1);
        item = updateOne(item);
        assertEquals(0, item.quality);
    }
}
