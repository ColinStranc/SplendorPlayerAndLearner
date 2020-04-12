package splendor.core

// TODO: Is this responsible for changing turns? Or just the action? Brings up interesting questions... Like if this is
//  just making the action, then the Splendor itself is changing turns at the end of every action, which makes the
//  different actions feel like they should have one entrypoint which updates turn at the very end.
interface SplendorService {
    fun acquireThreeDistinctResources(state: State, gem1: Gem, gem2: Gem, gem3: Gem): State
    fun acquireTwoOfSameResource(state: State, type: Gem): State
    fun acquireWildcardAndTile(state: State, tileId: String): State
    fun buyTile(state: State, tileId: String, chips: GemMap, wildcards: GemMap): State
}