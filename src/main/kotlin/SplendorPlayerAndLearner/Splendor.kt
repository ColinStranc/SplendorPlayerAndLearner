package SplendorPlayerAndLearner

// TODO: Is the idea that this interface can last you the whole game? Or are you creating a new one for every turn?
//  Either? The either option works, but it feels a bit too flexible
interface Splendor {
    // TODO: Do we need player passed in or are we responsible for knowing the player?
    fun acquireThreeDistinctResources(player: Player, gem1: Gem, gem2: Gem, gem3: Gem)
    fun acquireTwoOfSameResource(player: Player, type: Gem)
    fun acquireWildcardAndTile(player: Player, tile: Tile)
    fun buyTile(player: Player, tile: Tile, chips: GemMap, wildcards: GemMap)
    fun getState(): State
}