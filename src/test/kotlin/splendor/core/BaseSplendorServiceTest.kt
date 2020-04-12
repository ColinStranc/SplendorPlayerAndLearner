package splendor.core

import splendor.core.util.MockImplementations as MI
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

// TODO: These are not great yet.
//  - Magic numbers in the test conditions
//  - About to retest a bunch of "valid move" functionality across each action (although that is more design question
//  than test question)
//  - Creating too much cruft? Maybe it is fine though.
class BaseSplendorServiceTest {
    @Test
    fun acquireTwoResourcesAddsResourcesToActivePlayer() {
        val playerGems = 1
        val gem = Gem.BLACK

        val oldState = MI.simpleState().copy(
            players = listOf(MI.emptyPlayer("p1").copy(chips = GemMap(mapOf(gem to playerGems))))
        )

        val playerId: String = oldState.players[oldState.activeTurnIndex].player.id

        val service = BaseSplendorService(MI.successCostService())

        val state = service.acquireTwoOfSameResource(oldState, gem)

        assertEquals(playerGems + 2, state.players.find { (player) -> player.id == playerId }!!.chips[gem])
    }

    @Test
    fun buyRemovesResourcesAddsTileToPlayerStateReplacesTileInDeck() {
        val p1 = PlayerState(
            Player("1", "p1"),
            GemMap(mapOf(Gem.BLACK to 1)),
            0,
            listOf(),
            listOf(),
            listOf()
        )
        val t1 = Tile(
            "1",
            GemMap(mapOf(Gem.BLACK to 1)),
            GemMap(mapOf()),
            0
        )
        val t2 = Tile(
            "2",
            GemMap(mapOf()),
            GemMap(mapOf()),
            0
        )
        val d1 = DeckState("1", "d1", listOf(t2), listOf(t1))
        val settings = Settings(0, 0)
        val state = State(listOf(p1), listOf(d1), 0, settings, null)

        val splendor =
            BaseSplendorService(MI.successCostService())

        val newState = splendor.buyTile(
            state, t1.id,
            GemMap(mapOf(Gem.BLACK to 1)),
            GemMap(mapOf())
        )

        assertEquals(0, newState.players[0].chips[Gem.BLACK])
        assertTrue(newState.players[0].tiles.contains(t1))
        assertFalse(newState.tierDecks[0].displayed.contains(t1))
        assertTrue(newState.tierDecks[0].displayed.contains(t2))
    }

    @Test(expected = Exception::class)
    fun buyFailsWithInsufficientFunds() {
        val p1 = PlayerState(
            Player("1", "p1"),
            GemMap(mapOf()),
            0,
            listOf(),
            listOf(),
            listOf()
        )
        val p2 = PlayerState(
            Player("2", "p2"),
            GemMap(mapOf()),
            0,
            listOf(),
            listOf(),
            listOf()
        )
        val tile = Tile(
            "1",
            GemMap(mapOf()),
            GemMap(mapOf()),
            0
        )
        val t1 = DeckState("1", "t1", listOf(), listOf(tile))
        val settings = Settings(0, 0)
        val state = State(listOf(p1, p2), listOf(t1), 0, settings, null)

        val splendor =
            BaseSplendorService(MI.successCostService())

        splendor.buyTile(
            state, tile.id,
            GemMap(mapOf(Gem.BLACK to 1)),
            GemMap(mapOf())
        )
    }
}