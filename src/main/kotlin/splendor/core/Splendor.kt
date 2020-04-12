package splendor.core

interface Splendor {
    fun getState(): State
    fun buyTile(tileId: String, chips: GemMap, wildcards: GemMap)
    fun acquireTwoOfSameResource(resource: Gem)
    fun acquireThreeDistinctResources(resource1: Gem, resource2: Gem, resource3: Gem)
    fun acquireWildcardAndTile(tileId: String)
}