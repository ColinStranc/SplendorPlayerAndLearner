package splendor.core

interface SplendorService {
    fun acquireThreeDistinctResources(state: State, gem1: Gem, gem2: Gem, gem3: Gem): State
    fun acquireTwoOfSameResource(state: State, type: Gem): State
    fun acquireWildcardAndTile(state: State, tileId: String): State
    fun buyTile(state: State, tileId: String, chips: GemMap, wildcards: GemMap): State
}