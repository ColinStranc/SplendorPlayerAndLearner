package splendor.core

import kotlin.test.Test
import kotlin.test.assertEquals

class SimpleCostServiceTest {
    private var costService: SimpleCostService =
        SimpleCostService

    private val LESS_THAN = "less than"
    private val EQUAL_TO = "equal to"
    private val GREATER_THAN = "greater than"

    @Test
    fun noGemsEqualToNoCost() {
        testEqualComparison(0, 0, 0)
    }

    @Test
    fun singleChipEqualToOneCost() {
        testEqualComparison(1, 1, 0)
    }

    @Test
    fun singleTileEqualToOneCost() {
        testEqualComparison(1, 0, 1)
    }

    @Test
    fun chipAndTileEqualToExactCostMatch() {
        testEqualComparison(5, 2, 3)
    }

    @Test
    fun overshootBasedOnTileIsEqual() {
        testEqualComparison(2, 0, 3)
    }

    @Test
    fun tileAndChipOvershootEqual() {
        testEqualComparison(5, 2, 4)
    }

    @Test
    fun exactChipMatchWithSuperfluousTilesEqual() {
        testEqualComparison(3, 3, 100)
    }

    @Test
    fun noGemsLessThanCost() {
        testLessThanComparison(1, 0, 0)
    }

    @Test
    fun nonZeroChipCanBeTooSmall() {
        testLessThanComparison(4, 2, 0)
    }

    @Test
    fun nonZeroTileCanBeTooSmall() {
        testLessThanComparison(4, 0, 2)
    }

    @Test
    fun chipAndTileCanBeTooSmall() {
        testLessThanComparison(3, 1, 1)
    }

    @Test
    fun chipIsTooLargeForZeroCost() {
        testGreaterThanComparison(0, 1, 0)
    }

    @Test
    fun excessiveChipsTooLarge() {
        testGreaterThanComparison(2, 3, 0)
    }

    @Test
    fun chipsToMuchEvenWhenTilesSufficient() {
        testGreaterThanComparison(2, 3, 2)
    }

    private fun testLessThanComparison(cost: Int, chip: Int, tile: Int) {
        testComparison(cost, chip, tile, -1)
    }

    private fun testEqualComparison(cost: Int, chip: Int, tile: Int) {
        testComparison(cost, chip, tile, 0)
    }

    private fun testGreaterThanComparison(cost: Int, chip: Int, tile: Int) {
        testComparison(cost, chip, tile, 1)
    }

    private fun testComparison(cost: Int, chip: Int, tile: Int, desiredComparison: Int) {
        val comparison: Int = SimpleCostService.compareCostToPayment(cost, chip, tile)

        assertEquals(desiredComparison, comparison, comparisonMessage(cost, chip, tile, desiredComparison, comparison))
    }

    private fun comparisonMessage(cost: Int, chip: Int, tile: Int, desired: Int, actual: Int): String {
        val desiredText = if (desired < 0) {
            LESS_THAN
        } else if (desired > 0) {
            GREATER_THAN
        } else {
            EQUAL_TO
        }
        val actualText = if (actual < 0) {
            LESS_THAN
        } else if (actual > 0) {
            GREATER_THAN
        } else {
            EQUAL_TO
        }
        return "Expected payment of '$chip' chips, '$tile' tiles to be $desiredText a cost of '$cost', it tested" +
                " $actualText."
    }
}