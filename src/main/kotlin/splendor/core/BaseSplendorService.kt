package splendor.core

const val SAME_RESOURCE_RETRIEVAL_COUNT: Int = 2
const val UNIQUE_RESOURCE_RETRIEVAL_COUNT: Int = 1

class BaseSplendorService(private val costService: CostService) :
    SplendorService {
    override fun acquireThreeDistinctResources(state: State, gem1: Gem, gem2: Gem, gem3: Gem): State {
        val playerUpdatedState = acquireResources(
            state.players[state.activeTurnIndex],
            GemMap(
                mapOf(
                    gem1 to UNIQUE_RESOURCE_RETRIEVAL_COUNT,
                    gem2 to UNIQUE_RESOURCE_RETRIEVAL_COUNT,
                    gem3 to UNIQUE_RESOURCE_RETRIEVAL_COUNT
                )
            )
        )

        return updateTurn(state.copy(players = state.players.map { ps ->
            if (ps.player.id == playerUpdatedState.player.id) playerUpdatedState else ps
        }))
    }

    override fun acquireTwoOfSameResource(state: State, type: Gem): State {
        val playerUpdatedState = acquireResources(
            state.players[state.activeTurnIndex],
            GemMap(mapOf(type to SAME_RESOURCE_RETRIEVAL_COUNT))
        )

        return updateTurn(state.copy(players = state.players.map { ps ->
            if (ps.player.id == playerUpdatedState.player.id) playerUpdatedState else ps
        }))
    }

    override fun acquireWildcardAndTile(state: State, tileId: String): State {
        val tile = findTile(state, tileId)
        val newTiers = replaceTile(state.tierDecks, tile)

        val p = state.players[state.activeTurnIndex]
        val newP = p.copy(
            wildcards = p.wildcards + 1,
            reservedTiles = p.reservedTiles.plus(listOf(tile))
        )

        return updateTurn(
            state.copy(
                players = state.players.map { ps -> if (ps.player.id == p.player.id) newP else ps },
                tierDecks = newTiers
            )
        )
    }

    override fun buyTile(state: State, tileId: String, chips: GemMap, wildcards: GemMap): State {
        val tile = findTile(state, tileId)
        val newDecks = replaceTile(state.tierDecks, tile)

        val p = state.players[state.activeTurnIndex]
        val newP = p.copy(
            chips = GemMap(Gem.values().associate { g -> g to p.chips[g] - chips[g] }),
            wildcards = p.wildcards - Gem.values().fold(0) { s, g -> s + wildcards[g] },
            tiles = p.tiles.plus(tile)
        )

        return updateTurn(
            state.copy(
                players = state.players.map { ps -> if (ps.player.id == p.player.id) newP else ps },
                tierDecks = newDecks
            )
        )
    }

    private fun findTile(state: State, tileId: String): Tile {
        return (state.tierDecks.fold(listOf<Tile>()) { ts, d -> ts.plus(d.displayed) }).find { (id) -> id == tileId }
            ?: throw Exception("Tile '$tileId' unavailable for being purchased")
    }

    private fun replaceTile(decks: List<DeckState>, tile: Tile): List<DeckState> {
        val deck = decks.find { ds -> ds.displayed.contains(tile) }
            ?: throw Exception("Tile '${tile.id}' was not displayed, could not be replaced")

        val newDisplayed = if (
            deck.deck.isEmpty()
        ) deck.displayed.minus(tile) else deck.displayed.minus(tile).plus(deck.deck[0])
        val newDeck = if (deck.deck.isEmpty()) deck.deck else deck.deck.minus(deck.deck[0])

        return decks.map { d -> if (d.id == deck.id) deck.copy(displayed = newDisplayed, deck = newDeck) else d }
    }

    private fun acquireResources(playerState: PlayerState, gemMap: GemMap): PlayerState {
        return playerState.copy(
            chips = GemMap.plus(
                playerState.chips, gemMap
            )
        )
    }

    private fun updateTurn(state: State): State {
        return state.copy(activeTurnIndex = (state.activeTurnIndex + TURN_INDEX_PROGRESSION) % state.players.size)
    }

    // TODO: This does not belong here. Some sort of GemMap math?
    private fun covers(covering: GemMap, covered: GemMap): Boolean {
        for (gem in Gem.values()) {
            if (covering[gem] < covered[gem]) {
                return false
            }
        }

        return true
    }
}