package SplendorPlayerAndLearner

interface Splendor {
    // TODO: Do we need player passed in or are we responsible for knowing the player?
    fun acquireThreeDistinctResources(player: Player, gem1: Gem, gem2: Gem, gem3: Gem)
    fun acquireTwoOfSameResource(player: Player, type: Gem)
    fun acquireWildcardAndTile(player: Player, tile: Tile)
    fun buyTile(player: Player, tile: Tile)
    fun getState(): State
}