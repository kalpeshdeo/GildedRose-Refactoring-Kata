package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    // NEW FAILING TEST: Conjured item after sellIn should degrade by 4
    @Test
    void should_decrease_quality_by_four_when_conjured_item_after_sellIn() {
        Item[] items = new Item[]{new Item("Conjured Mana Cake", 0, 10)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(6, app.items[0].quality);
    }

    // CHARACTERIZATION TESTS

    @Test
    void should_decrease_quality_by_two_when_conjured_item_before_sellIn() {
        Item[] items = new Item[]{new Item("Conjured Mana Cake", 3, 6)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(4, app.items[0].quality);
    }

    @Test
    void should_not_decrease_conjured_quality_below_zero() {
        Item[] items = new Item[]{new Item("Conjured Mana Cake", 3, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }

    @Test
    void should_not_decrease_conjured_quality_below_zero_after_sellIn() {
        Item[] items = new Item[]{new Item("Conjured Mana Cake", -1, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }

    @Test
    void should_decrease_quality_by_one_when_normal_item_before_sellIn() {
        Item[] items = new Item[]{new Item("Elixir of the Mongoose", 5, 7)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(6, app.items[0].quality);
    }

    @Test
    void should_decrease_quality_by_two_when_normal_item_after_sellIn() {
        Item[] items = new Item[]{new Item("Normal Item", 0, 5)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(3, app.items[0].quality);
    }

    @Test
    void should_not_decrease_quality_below_zero_when_normal_item_after_sellIn() {
        Item[] items = new Item[]{new Item("Normal Item", 0, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }

    @Test
    void should_not_increase_quality_when_normal_item_at_maximum_quality() {
        Item[] items = new Item[]{new Item("Normal Item", 5, 50)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(49, app.items[0].quality);
    }

    @Test
    void should_increase_quality_when_aged_brie_before_sellIn() {
        Item[] items = new Item[]{new Item("Aged Brie", 2, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(1, app.items[0].quality);
    }

    @Test
    void should_increase_quality_by_two_when_aged_brie_after_sellIn() {
        Item[] items = new Item[]{new Item("Aged Brie", 0, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(2, app.items[0].quality);
    }

    @Test
    void should_not_increase_quality_above_fifty_when_aged_brie() {
        Item[] items = new Item[]{new Item("Aged Brie", 0, 50)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }

    @Test
    void should_not_increase_quality_above_fifty_when_aged_brie_after_multiple_days() {
        Item[] items = new Item[]{new Item("Aged Brie", 2, 49)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }

    @Test
    void should_increase_quality_by_one_when_backstage_more_than_ten_days() {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(21, app.items[0].quality);
    }

    @Test
    void should_increase_quality_by_two_when_backstage_ten_days_or_less() {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(22, app.items[0].quality);
    }

    @Test
    void should_increase_quality_by_three_when_backstage_five_days_or_less() {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(23, app.items[0].quality);
    }

    @Test
    void should_drop_quality_to_zero_when_backstage_after_concert() {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }

    @Test
    void should_not_increase_quality_above_fifty_when_backstage_sellIn_ten_or_less() {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }

    @Test
    void should_not_increase_quality_above_fifty_when_backstage_sellIn_five_or_less() {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 5, 48)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }

    @Test
    void should_not_change_sulfuras_quality() {
        Item[] items = new Item[]{new Item("Sulfuras, Hand of Ragnaros", 0, 80)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(80, app.items[0].quality);
    }

    @Test
    void should_not_change_sulfuras_sellIn() {
        Item[] items = new Item[]{new Item("Sulfuras, Hand of Ragnaros", 0, 80)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].sellIn);
    }

    @Test
    void should_not_change_sulfuras_quality_when_negative_sellIn() {
        Item[] items = new Item[]{new Item("Sulfuras, Hand of Ragnaros", -1, 80)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(80, app.items[0].quality);
    }

    @Test
    void should_not_change_sulfuras_sellIn_when_negative() {
        Item[] items = new Item[]{new Item("Sulfuras, Hand of Ragnaros", -1, 80)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(-1, app.items[0].sellIn);
    }

    @Test
    void should_handle_multiple_items_correctly() {
        Item[] items = new Item[]{
            new Item("+5 Dexterity Vest", 10, 20),
            new Item("Aged Brie", 2, 0),
            new Item("Elixir of the Mongoose", 5, 7),
            new Item("Sulfuras, Hand of Ragnaros", 0, 80),
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            new Item("Conjured Mana Cake", 3, 6)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(19, items[0].quality);
        assertEquals(9, items[0].sellIn);
        assertEquals(1, items[1].quality);
        assertEquals(1, items[1].sellIn);
        assertEquals(6, items[2].quality);
        assertEquals(4, items[2].sellIn);
        assertEquals(80, items[3].quality);
        assertEquals(0, items[3].sellIn);
        assertEquals(21, items[4].quality);
        assertEquals(14, items[4].sellIn);
        assertEquals(4, items[5].quality);
        assertEquals(2, items[5].sellIn);
    }
}