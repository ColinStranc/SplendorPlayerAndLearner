package SplendorPlayerAndLearner

class BaseSplendor(private val state: State) : Splendor {
    override fun acquireThreeDistinctResources(player: Player, gem1: Gem, gem2: Gem, gem3: Gem) {
        TODO("Not yet implemented")
    }

    override fun acquireTwoOfSameResource(player: Player, type: Gem) {
        TODO("Not yet implemented")
    }

    override fun acquireWildcardAndTile(player: Player, tile: Tile) {
        TODO("Not yet implemented")
    }

    override fun buyTile(player: Player, tile: Tile, chips: GemMap, wildcards: GemMap) {
        // TODO: check if game is over

        val activePlayerState = state.players[state.activeTurnIndex]

        if (activePlayerState.player != player) {
            throw IllegalActionException("Player ${player.name} (${player.id}) not active")
        }

        if (!covers(activePlayerState.chips, chips)) {
            throw InsufficientFundsException(activePlayerState.chips, chips)
        }
    }

    override fun getState(): State {
        TODO("Not yet implemented")
    }

    // TODO: This does not belong here. Some sort of GemMap math?
    private fun covers(covering: GemMap, coveree: GemMap): Boolean {
        for (gem in Gem.values()) {
            if (covering[gem] < coveree[gem]) {
                return false
            }
        }

        return true
    }
}