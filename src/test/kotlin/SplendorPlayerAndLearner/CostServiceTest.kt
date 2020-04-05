package SplendorPlayerAndLearner

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CostServiceTest {
    private var costService: CostService = CostService

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
        val comparison: Int = costService.compareCostToPayment(cost, chip, tile)

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
/*
    @Test
    fun canMakeSimplePurchaseWithAnyGem() {
        val costOfGem = 1

        for (gem in Gem.values()) {
            val cost = GemMap(mapOf(gem to costOfGem))
            val payment = Payment(GemMap(mapOf(gem to costOfGem)), GemMap(mapOf()), GemMap(mapOf()))

            assertTrue(
                costService.appropriatePayment(payment, cost),
                "Could not make simple purchase with gem type: '${gem.name}'"
            )
        }
    }

    @Test
    fun canPurchaseFreeCost() {
        val cost = GemMap(mapOf())
        val payment = Payment(GemMap(mapOf()), GemMap(mapOf()), GemMap(mapOf()))

        assertTrue(costService.appropriatePayment(payment, cost), "Could not make free purchase")
    }

    @Test
    fun canMakePurchaseOfMultipleGemTypes() {
        val costOfGems = 1
        for (gem1 in Gem.values()) {
            for (gem2 in Gem.values()) {
                if (gem2 == gem1) {
                    continue
                }

                val cost = GemMap(mapOf(gem1 to costOfGems, gem2 to costOfGems))
                val payment = Payment(
                    GemMap(mapOf(gem1 to costOfGems, gem2 to costOfGems)),
                    GemMap(mapOf()),
                    GemMap(mapOf())
                )

                assertTrue(
                    costService.appropriatePayment(payment, cost),
                    "Could not make purchase with gem types: '${gem1.name}' and ${gem2.name}'"
                )
            }
        }
    }

    @Test
    fun canMakePurchaseWithEveryGemType() {
        val costOfGems = 1
        val mapWithEveryGem = Gem.values().zip(MutableList(Gem.values().size) { costOfGems }).toMap()
        val cost = GemMap(mapWithEveryGem)
        val payment = Payment(GemMap(mapWithEveryGem), GemMap(mapOf()), GemMap(mapOf()))

        assertTrue(costService.appropriatePayment(payment, cost), "Could not make purchase of every gem type")
    }

    @Test
    fun canMakePurchaseWorthMoreThanOne() {
        val costOfGem = Int.MAX_VALUE
        for (gem in Gem.values()) {
            val cost = GemMap(mapOf(gem to costOfGem))
            val payment = Payment(GemMap(mapOf(gem to costOfGem)), GemMap(mapOf()), GemMap(mapOf()))

            assertTrue(costService.appropriatePayment(payment, cost), "Could not make purchase of large amount")
        }
    }

    @Test
    fun canMakePurchaseWithMultipleRequiredAmountsAcrossGems() {
        val cost1 = 2
        val cost2 = 3
        val gem1 = Gem.BLACK
        val gem2 = Gem.WHITE
        val cost = GemMap(mapOf(gem1 to cost1, gem2 to cost2))
        val payment = Payment(GemMap(mapOf(gem1 to cost1, gem2 to cost2)), GemMap(mapOf()), GemMap(mapOf()))

        assertTrue(
            costService.appropriatePayment(payment, cost),
            "Could not make purchase across varied required amounts"
        )
    }

    @Test
    fun purchaseFailsIfNotEnoughOfAGem() {
        val costOfGem = 3
        for (gem in Gem.values()) {
            val cost = GemMap(mapOf(gem to costOfGem))
            val payment = Payment(GemMap(mapOf(gem to costOfGem - 1)), GemMap(mapOf()), GemMap(mapOf()))

            assertFalse(
                costService.appropriatePayment(payment, cost),
                "Purchase without enough chips did not fail for gem type '${gem.name}'"
            )
        }
    }

    @Test
    fun purchaseFailsIfTooManyOfAGem() {
        val costOfGem = 3
        for (gem in Gem.values()) {
            val cost = GemMap(mapOf(gem to costOfGem))
            val payment = Payment(GemMap(mapOf(gem to costOfGem + 1)), GemMap(mapOf()), GemMap(mapOf()))

            assertFalse(
                costService.appropriatePayment(payment, cost),
                "Purchase with too many chips did not fail for gem type '${gem.name}'"
            )
        }
    }

    @Test
    fun purchaseFailsDespiteMeetingManyCriteria() {
        val costOfGems = 2
        val costOfExtraGem = 3
        val oddGemOut = Gem.BLUE
        val normalGems = listOf(Gem.WHITE, Gem.GREEN, Gem.RED)

        val cost = GemMap(mapOf(Gem.WHITE to 2, Gem.GREEN to 2, Gem.RED to 2, Gem.BLUE to 3))
        val payment = Payment(
            GemMap(mapOf(Gem.WHITE to 2, Gem.GREEN to 2, Gem.RED to 2, Gem.BLUE to 1)),
            GemMap(mapOf()),
            GemMap(mapOf())
        )

        assertFalse(costService.appropriatePayment(payment, cost))
    }

    @Test
    fun freePurchaseCanFail() {
        val cost = GemMap(mapOf())
        val payment = Payment(GemMap(mapOf(Gem.BLUE to 1)), GemMap(mapOf()), GemMap(mapOf()))

        assertFalse(costService.appropriatePayment(payment, cost), "Bought free object despite having chips")
    }

    @Test
    fun canPayWithAllTiles() {
        val costOfGems = 1
        val mapWithEveryGem = Gem.values().zip(MutableList(Gem.values().size) { costOfGems }).toMap()
        val cost = GemMap(mapWithEveryGem)
        val payment = Payment(GemMap(mapOf()), GemMap(mapWithEveryGem), GemMap(mapOf()))

        assertTrue(
            costService.appropriatePayment(payment, cost),
            "Could not make purchase of every gem type with exact tiles"
        )
    }

    @Test
    fun canPayPartiallyWithTiles() {
        val costInChips = 1
        val costInTiles = 2
        val cost = GemMap(mapOf(Gem.GREEN to costInChips + costInTiles))
        val payment = Payment(
            GemMap(mapOf(Gem.GREEN to costInChips)),
            GemMap(mapOf(Gem.GREEN to costInTiles)),
            GemMap(mapOf())
        )

        assertTrue(
            costService.appropriatePayment(payment, cost),
            "Could not make purchase partially with tiles"
        )
    }

    @Test
    fun canHaveSuperfluousTiles() {
        val costOfGem = 2
        val gem = Gem.BLACK
        val cost = GemMap(mapOf())
    }

    /**
     * Old tests
     */

    @Test
    fun payCostWithChips() {
        val cost = GemMap(mapOf(Gem.BLACK to 1))
        val payment = Payment(GemMap(mapOf(Gem.BLACK to 1)), GemMap(mapOf()), GemMap(mapOf()))

        assertTrue(costService.appropriatePayment(payment, cost))
    }

    @Test
    fun payZeroCostWithNoChips() {
        val cost = GemMap(mapOf())
        val payment = Payment(GemMap(mapOf()), GemMap(mapOf()), GemMap(mapOf()))

        assertTrue(costService.appropriatePayment(payment, cost))
    }

    @Test
    fun payCostWithMultipleChips() {
        val cost = GemMap(mapOf(Gem.BLACK to 1, Gem.GREEN to 5, Gem.WHITE to 2))
        val payment = Payment(
            GemMap(mapOf(Gem.BLACK to 1, Gem.GREEN to 5, Gem.WHITE to 2)),
            GemMap(mapOf()),
            GemMap(mapOf())
        )

        assertTrue(costService.appropriatePayment(payment, cost))
    }

    @Test
    fun cannotBuyWithTooManyChips() {
        val cost = GemMap(mapOf(Gem.BLACK to 1))
        val payment = Payment(GemMap(mapOf(Gem.BLACK to 2)), GemMap(mapOf()), GemMap(mapOf()))

        assertFalse(costService.appropriatePayment(payment, cost))
    }

    @Test
    fun cannotBuyWithTooManyChips2() {
        val cost = GemMap(mapOf(Gem.RED to 4))
        val payment = Payment(GemMap(mapOf(Gem.BLACK to 7)), GemMap(mapOf()), GemMap(mapOf()))

        assertFalse(costService.appropriatePayment(payment, cost))
    }

    @Test
    fun cannotBuyWithDifferentGem() {
        val cost = GemMap(mapOf(Gem.RED to 1))
        val payment = Payment(GemMap(mapOf(Gem.BLACK to 1)), GemMap(mapOf()), GemMap(mapOf()))

        assertFalse(costService.appropriatePayment(payment, cost))
    }

    @Test
    fun cannotBuyIfOnlySatisfiesOneConstraint() {
        val cost = GemMap(mapOf(Gem.RED to 2, Gem.BLUE to 3))
        val payment = Payment(GemMap(mapOf(Gem.RED to 1, Gem.BLUE to 3)), GemMap(mapOf()), GemMap(mapOf()))

        assertFalse(costService.appropriatePayment(payment, cost))
    }

    @Test
    fun cannotBuyWithAdditionalDifferentGem() {
        val cost = GemMap(mapOf(Gem.RED to 1))
        val payment = Payment(GemMap(mapOf(Gem.RED to 1, Gem.BLACK to 1)), GemMap(mapOf()), GemMap(mapOf()))

        assertFalse(costService.appropriatePayment(payment, cost))
    }

    @Test
    fun cannotBuyWithTooFewChips() {
        val cost = GemMap(mapOf(Gem.BLACK to 3))
        val payment = Payment(GemMap(mapOf(Gem.BLACK to 1)), GemMap(mapOf()), GemMap(mapOf()))

        assertFalse(costService.appropriatePayment(payment, cost))
    }

    @Test
    fun tileCanSubstituteForLackingChip() {
        val cost = GemMap(mapOf(Gem.BLACK to 1))
        val payment = Payment(
            GemMap(mapOf()),
            GemMap(mapOf(Gem.BLACK to 1)),
            GemMap(mapOf())
        )

        assertTrue(costService.appropriatePayment(payment, cost))
    }
 */
}