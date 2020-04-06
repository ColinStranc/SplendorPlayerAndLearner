package SplendorPlayerAndLearner

import kotlin.test.Test

class BaseSplendorTest {
    @Test(expected = Exception::class)
    fun buyFailsWithInsufficientFunds() {
        val p1 = PlayerState(Player(1, "p1"), GemMap(mapOf()), 0, listOf(), listOf(), listOf())
        val p2 = PlayerState(Player(2, "p2"), GemMap(mapOf()), 0, listOf(), listOf(), listOf())
        val tile = Tile(1, GemMap(mapOf()), GemMap(mapOf()), 0)
        val t1 = TierState(1, "t1", listOf(), listOf(tile))
        val settings = Settings(0, 0)
        val state = State(listOf(p1, p2), listOf(t1), 0, settings)

        val splendor = BaseSplendor(state)

        splendor.buyTile(p1.player, tile, GemMap(mapOf(Gem.BLACK to 1)), GemMap(mapOf()))
    }

    @Test(expected = IllegalActionException::class)
    fun buyOutOfTurnFailsWithIllegalActionException() {
        val p1 = PlayerState(Player(1, "p1"), GemMap(mapOf()), 0, listOf(), listOf(), listOf())
        val p2 = PlayerState(Player(2, "p2"), GemMap(mapOf()), 0, listOf(), listOf(), listOf())
        val tile = Tile(1, GemMap(mapOf()), GemMap(mapOf()), 0)
        val t1 = TierState(1, "t1", listOf(), listOf(tile))
        val settings = Settings(0, 0)
        val state = State(listOf(p1, p2), listOf(t1), 0, settings)

        val splendor = BaseSplendor(state)

        splendor.buyTile(p2.player, tile, GemMap(mapOf()), GemMap(mapOf()))
    }
}