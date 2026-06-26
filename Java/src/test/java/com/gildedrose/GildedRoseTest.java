package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void should_degrade_quality_by_2_before_sellby_when_conjured_item() {
        Item[] items = new Item[]{new Item("Conjured Mana Cake", 3, 6)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(4, app.items[0].quality);
    }

    @Test
    void should_degrade_quality_by_4_after_sellby_when_conjured_item() {
        Item[] items = new Item[]{new Item("Conjured Mana Cake", 0, 6)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(2, app.items[0].quality);
    }

    @Test
    void should_not_degrade_quality_below_zero_when_conjured_item() {
        Item[] items = new Item[]{new Item("Conjured Mana Cake", 3, 1)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }

    @Test
    void should_not_change_quality_of_normal_item_when_no_special_conditions() {
        Item[] items = new Item[]{new Item("+5 Dexterity Vest", 10, 20)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(19, app.items[0].quality);
    }

    @Test
    void should_increase_quality_of_aged_brie_before_sellby() {
        Item[] items = new Item[]{new Item("Aged Brie", 2, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(1, app.items[0].quality);
    }

    @Test
    void should_increase_quality_of_aged_brie_after_sellby() {
        Item[] items = new Item[]{new Item("Aged Brie", 0, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(2, app.items[0].quality);
    }

    @Test
    void should_increase_quality_of_backstage_passes_by_1_before_sellin_11() {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 12, 49)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }

    @Test
    void should_increase_quality_of_backstage_passes_by_2_between_sellin_6_and_10() {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }

    @Test
    void should_increase_quality_of_backstage_passes_by_3_between_sellin_0_and_5() {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 6, 49)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }

    @Test
    void should_set_quality_to_zero_after_concert_for_backstage_passes() {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 0, 49)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }

    @Test
    void should_not_change_quality_of_sulfuras_before_sellby() {
        Item[] items = new Item[]{new Item("Sulfuras, Hand of Ragnaros", 10, 80)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(80, app.items[0].quality);
    }

    @Test
    void should_not_change_quality_of_sulfuras_after_sellby() {
        Item[] items = new Item[]{new Item("Sulfuras, Hand of Ragnaros", 0, 80)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(80, app.items[0].quality);
    }

}