package SplendorPlayerAndLearner

// TODO: Is the idea that this interface can last you the whole game? Or are you creating a new one for every turn?
//  Either? The either option works, but it feels a bit too flexible
//  To Elaborate: Do we provide the splendor instance with a State? Will it always be starting a new game and so creates
//  a new State? Do we provide it with a StateSource? If we do that, is it only getting access through the source? That
//  feels unnecessary. If we provide it with the StateSource, we are not obligated to use the game to get Source in the
//  future which feels wrong. If we do not provide it an instance it must create it itself? That isn't testable...
interface Splendor {
    // TODO: Do we need player passed in or are we responsible for knowing the player?
    fun acquireThreeDistinctResources(player: Player, gem1: Gem, gem2: Gem, gem3: Gem)
    fun acquireTwoOfSameResource(player: Player, type: Gem)
    fun acquireWildcardAndTile(player: Player, tile: Tile)
    fun buyTile(player: Player, tile: Tile, chips: GemMap, wildcards: GemMap)
    fun getState(): State
}