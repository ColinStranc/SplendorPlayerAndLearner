package splendor.core

const val SAME_RESOURCE_RETRIEVAL_COUNT: Int = 2
const val UNIQUE_RESOURCE_RETRIEVAL_COUNT: Int = 1
const val TURN_INDEX_PROGRESSION: Int = 1

class BaseSplendorService(private val costService: CostService) :
    SplendorService {
    override fun acquireThreeDistinctResources(state: State, gem1: Gem, gem2: Gem, gem3: Gem): State {
        TODO("Not yet implemented")
    }

    override fun acquireTwoOfSameResource(state: State, type: Gem): State {
        val playerOriginalState = state.players[state.activeTurnIndex]
        val playerUpdatedState = playerOriginalState.copy(
            chips = GemMap(Gem.values().associate { g ->
                g to playerOriginalState.chips[g] + SAME_RESOURCE_RETRIEVAL_COUNT
            })
        )
        val oldActiveIndex = state.activeTurnIndex
        val newActiveIndex = (oldActiveIndex + TURN_INDEX_PROGRESSION) % state.players.size

        return state.copy(players = state.players.map { ps ->
            if (ps.player.id == playerUpdatedState.player.id) playerUpdatedState else ps
        }, activeTurnIndex = newActiveIndex)
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