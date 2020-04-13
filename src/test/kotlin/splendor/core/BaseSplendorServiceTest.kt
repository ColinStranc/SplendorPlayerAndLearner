package splendor.core

import kotlin.test.*
import splendor.core.util.MockImplementations as MI

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

        val playerId = oldState.players[oldState.activeTurnIndex].player.id

        val service = BaseSplendorService(MI.successCostService())

        val state = service.acquireTwoOfSameResource(oldState, gem)

        assertEquals(playerGems + 2, state.players.find { (player) -> player.id == playerId }!!.chips[gem])
    }

    @Test
    fun acquireTwoResourcesDoesNotChangeOtherResources() {
        val playerGems = 17
        val gem = Gem.BLACK

        val oldState = MI.simpleState().copy(
            players = listOf(
                MI.emptyPlayer("p1").copy(
                    chips = GemMap(Gem.values().associate { g -> g to playerGems })
                )
            )
        )

        val playerId = oldState.players[oldState.activeTurnIndex].player.id

        val service = BaseSplendorService(MI.successCostService())

        val state = service.acquireTwoOfSameResource(oldState, gem)

        for (g in Gem.values()) {
            if (g != gem) {
                assertEquals(playerGems, state.players.find { (player) -> player.id == playerId }!!.chips[g])
            }
        }
    }

    @Test
    fun acquireTwoResourcesAddsResourcesToPlayerWithNothing() {
        val playerGems = 0
        val gem = Gem.BLACK

        val oldState = MI.simpleState().copy(
            players = listOf(MI.emptyPlayer("p1").copy(chips = GemMap(mapOf(gem to playerGems))))
        )

        val playerId = oldState.players[oldState.activeTurnIndex].player.id

        val service = BaseSplendorService(MI.successCostService())

        val state = service.acquireTwoOfSameResource(oldState, gem)

        assertEquals(playerGems + 2, state.players.find { (player) -> player.id == playerId }!!.chips[gem])
    }

    @Test
    fun acquireTwoResourcesAddsResourcesToPlayerWithManyGems() {
        val playerGems = 17
        val gem = Gem.BLACK

        val oldState = MI.simpleState().copy(
            players = listOf(
                MI.emptyPlayer("p1").copy(
                    chips = GemMap(Gem.values().associate { g -> g to playerGems })
                )
            )
        )

        val playerId = oldState.players[oldState.activeTurnIndex].player.id

        val service = BaseSplendorService(MI.successCostService())

        val state = service.acquireTwoOfSameResource(oldState, gem)

        assertEquals(playerGems + 2, state.players.find { (player) -> player.id == playerId }!!.chips[gem])
    }

    @Test
    fun acquireThreeResourcesAddsResourcesToPlayer() {
        val playerGems = 1
        val g1 = Gem.RED
        val g2 = Gem.WHITE
        val g3 = Gem.BLUE

        val oldState = MI.simpleState().copy(
            players = listOf(
                MI.emptyPlayer("p1").copy(
                    chips = GemMap(mapOf(g1 to playerGems, g2 to playerGems, g3 to playerGems))
                )
            )
        )

        val playerId = oldState.players[oldState.activeTurnIndex].player.id

        val service = BaseSplendorService(MI.successCostService())

        val state = service.acquireThreeDistinctResources(oldState, g1, g2, g3)

        assertEquals(playerGems + 1, state.players.find { (player) -> player.id == playerId }!!.chips[g1])
        assertEquals(playerGems + 1, state.players.find { (player) -> player.id == playerId }!!.chips[g2])
        assertEquals(playerGems + 1, state.players.find { (player) -> player.id == playerId }!!.chips[g3])
    }

    @Test
    fun acquireThreeResourcesDoesNotChangeOtherResources() {
        val playerGems = 1
        val g1 = Gem.RED
        val g2 = Gem.WHITE
        val g3 = Gem.BLUE

        val oldState = MI.simpleState().copy(
            players = listOf(
                MI.emptyPlayer("p1").copy(
                    chips = GemMap(Gem.values().associate { g -> g to playerGems })
                )
            )
        )

        val playerId = oldState.players[oldState.activeTurnIndex].player.id

        val service = BaseSplendorService(MI.successCostService())

        val state = service.acquireThreeDistinctResources(oldState, g1, g2, g3)

        for (g in Gem.values()) {
            if (g != g1 && g != g2 && g != g3) {
                assertEquals(playerGems, state.players.find { (player) -> player.id == playerId }!!.chips[g])
            }
        }
    }

    @Test
    fun reserveAddsWildcardMovesTileToReservedListAndPlacesNewTile() {
        val wildcardCount = 2
        val reservedTiles: List<Tile> = listOf()
        val toReserve = MI.emptyTile("t-res")
        val displayed = listOf(toReserve)
        val replacementTile = MI.emptyTile("t-rep")

        val oldState = MI.simpleState().copy(
            players = listOf(
                MI.emptyPlayer("p1").copy(
                    wildcards = wildcardCount,
                    reservedTiles = reservedTiles
                )
            ),
            tierDecks = listOf(
                DeckState("d1", "d1", listOf(replacementTile), displayed)
            )
        )

        val playerId = oldState.players[oldState.activeTurnIndex].player.id

        val service = BaseSplendorService(MI.successCostService())

        val state = service.acquireWildcardAndTile(oldState, toReserve.id)

        val player = state.players.find { (player) -> player.id == playerId }

        assertEquals(wildcardCount + 1, player!!.wildcards)
        assertEquals(reservedTiles.size + 1, player!!.reservedTiles.size)
        assertEquals(displayed.size, state.tierDecks[0].displayed.size)
        assertTrue(state.tierDecks[0].displayed.contains(replacementTile))
        assertFalse(state.tierDecks[0].deck.contains(replacementTile))
    }

    // TODO: Is this the behaviour we want? Should it configurable by settings? Should it be allowed by the service no
    //  matter the settings so that the game decides?
    @Test
    fun reserveWithNoMoreTilesToDisplayLeavesLessDisplayedCards() {
        val displayed = MI.emptyTile("t-res")

        val oldState = MI.simpleState().copy(
            tierDecks = listOf(
                DeckState("d1", "d1", listOf(), listOf(displayed))
            )
        )

        val service = BaseSplendorService(MI.successCostService())

        val state = service.acquireWildcardAndTile(oldState, displayed.id)

        assertTrue(state.tierDecks[0].displayed.isEmpty())
    }

    @Test(expected = Exception::class)
    fun reserveFailsWithInvalidTileId() {
        val id = "non-existent"

        val oldState = MI.simpleState().copy(
            tierDecks = listOf(
                DeckState("d1", "d1", listOf(), listOf())
            )
        )

        val service = BaseSplendorService(MI.successCostService())

        service.acquireWildcardAndTile(oldState, id)
    }
}