package splendor.core

interface Splendor {
    fun acquireThreeDistinctResources(state: State, gem1: Gem, gem2: Gem, gem3: Gem): State
    fun acquireTwoOfSameResource(state: State, type: Gem): State
    fun acquireWildcardAndTile(state: State, tileId: Int): State
    fun buyTile(state: State, tileId: Int, chips: GemMap, wildcards: GemMap): State
}