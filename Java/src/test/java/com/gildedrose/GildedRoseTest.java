package com.gildedrose;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GildedRoseTest {

    private static final String BRIE = "Aged Brie";
    private static final String PASSES = "Backstage passes to a TAFKAL80ETC concert";
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";

    private Item update(String name, int sellIn, int quality) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        return app.items[0];
    }

    private void assertState(Item item, int expectedSellIn, int expectedQuality) {
        assertEquals(expectedSellIn, item.sellIn, "sellIn");
        assertEquals(expectedQuality, item.quality, "quality");
    }

    // ---- New feature tests (dispatcher) ----

    @Test
    void should_update_each_item_exactly_once_via_registered_updater() {
        Item first = new Item("foo", 5, 10);
        Item second = new Item("bar", 3, 7);
        List<Item> updated = new ArrayList<>();
        ItemUpdaterRegistry registry = new ItemUpdaterRegistry();
        registry.register(ItemCategory.NORMAL, updated::add);

        GildedRose app = new GildedRose(new Item[] { first, second }, registry);
        app.updateQuality();

        assertEquals(2, updated.size());
        assertEquals(1, updated.stream().filter(i -> i == first).count());
        assertEquals(1, updated.stream().filter(i -> i == second).count());
    }

    @Test
    void should_fail_loudly_when_no_updater_registered_for_category() {
        ItemUpdaterRegistry emptyRegistry = new ItemUpdaterRegistry();
        GildedRose app = new GildedRose(new Item[] { new Item("foo", 5, 10) }, emptyRegistry);

        assertThrows(IllegalStateException.class, app::updateQuality);
    }

    // ---- Characterization tests ----

    @Test
    void normal_item_degrades_by_one_before_sell_date() {
        assertState(update("foo", 5, 10), 4, 9);
    }

    @Test
    void normal_item_degrades_by_two_after_sell_date() {
        assertState(update("foo", 0, 10), -1, 8);
    }

    @Test
    void normal_item_quality_never_negative() {
        assertState(update("foo", 5, 0), 4, 0);
    }

    @Test
    void normal_item_expired_with_zero_quality_stays_zero() {
        assertState(update("foo", 0, 0), -1, 0);
    }

    @Test
    void normal_item_expired_with_quality_one_goes_to_zero() {
        assertState(update("foo", 0, 1), -1, 0);
    }

    @Test
    void brie_increases_by_one_before_sell_date() {
        assertState(update(BRIE, 5, 10), 4, 11);
    }

    @Test
    void brie_increases_by_two_after_sell_date() {
        assertState(update(BRIE, 0, 10), -1, 12);
    }

    @Test
    void brie_quality_capped_at_fifty() {
        assertState(update(BRIE, 5, 50), 4, 50);
    }

    @Test
    void brie_expired_quality_capped_at_fifty() {
        assertState(update(BRIE, 0, 49), -1, 50);
    }

    @Test
    void sulfuras_never_changes() {
        assertState(update(SULFURAS, 0, 80), 0, 80);
    }

    @Test
    void sulfuras_with_negative_sellin_never_changes() {
        assertState(update(SULFURAS, -1, 80), -1, 80);
    }

    @Test
    void backstage_increases_by_one_when_more_than_ten_days() {
        assertState(update(PASSES, 15, 20), 14, 21);
    }

    @Test
    void backstage_increases_by_one_at_eleven_days() {
        assertState(update(PASSES, 11, 20), 10, 21);
    }

    @Test
    void backstage_increases_by_two_at_ten_days() {
        assertState(update(PASSES, 10, 20), 9, 22);
    }

    @Test
    void backstage_increases_by_two_at_six_days() {
        assertState(update(PASSES, 6, 20), 5, 22);
    }

    @Test
    void backstage_increases_by_three_at_five_days() {
        assertState(update(PASSES, 5, 20), 4, 23);
    }

    @Test
    void backstage_increases_by_three_at_one_day() {
        assertState(update(PASSES, 1, 20), 0, 23);
    }

    @Test
    void backstage_drops_to_zero_after_concert() {
        assertState(update(PASSES, 0, 20), -1, 0);
    }

    @Test
    void backstage_quality_capped_at_fifty_with_two_increment() {
        assertState(update(PASSES, 10, 49), 9, 50);
    }

    @Test
    void backstage_quality_capped_at_fifty_with_three_increment() {
        assertState(update(PASSES, 5, 48), 4, 50);
        assertState(update(PASSES, 5, 49), 4, 50);
    }

    @Test
    void unknown_conjured_item_currently_degrades_like_normal_item() {
        assertState(update("Conjured Mana Cake", 3, 6), 2, 5);
    }

    @Test
    void each_item_in_inventory_is_updated() {
        Item[] items = new Item[] { new Item("foo", 5, 10), new Item(BRIE, 5, 10), new Item(SULFURAS, 0, 80) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertState(app.items[0], 4, 9);
        assertState(app.items[1], 4, 11);
        assertState(app.items[2], 0, 80);
    }

}
