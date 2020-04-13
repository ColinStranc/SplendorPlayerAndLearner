package splendor.core

const val SAME_RESOURCE_RETRIEVAL_COUNT: Int = 2
const val UNIQUE_RESOURCE_RETRIEVAL_COUNT: Int = 1
const val TURN_INDEX_PROGRESSION: Int = 1

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
        TODO("Not yet implemented")
    }

    override fun buyTile(state: State, tileId: String, chips: GemMap, wildcards: GemMap): State {
        // TODO: check if game is over

        val playerState = state.players[state.activeTurnIndex]

        if (!covers(playerState.chips, chips)) {
            throw InsufficientFundsException(playerState.chips, chips)
        }

        val displayedTiles: List<Tile> = state.tierDecks.fold(listOf(), { tiles, tier -> tiles.plus(tier.displayed) })

        val tile: Tile = displayedTiles.find { (id) -> id == tileId }
            ?: throw IllegalActionException("Tile '$tileId' is not available")

        for (gem in Gem.values()) {
            // TODO: This is a monstrosity.
            val resourcesOnHand: GemMap = playerState.tiles.fold(
                GemMap(mapOf()), { gm, t ->
                    GemMap(
                        Gem.values().associate { gem ->
                            Pair(gem, gm[gem] + t.rewardGems[gem])
                        })
                }
            )

            if (
                costService.compareCostToPayment(
                    tile.cost[gem],
                    chips[gem] + wildcards[gem],
                    resourcesOnHand[gem]
                ) != 0
            ) {
                // TODO: Better exception, better message
                throw Exception("Payment provided was not sufficient")
            }
        }

        // TODO: calculate a new state
        return state
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