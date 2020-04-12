package splendor.core

interface Splendor {
    fun getState(): State
    fun buyTile(tileId: Int)
    fun acquireTwoOfSameResource(resource: Gem)
    fun acquireThreeDistinctResources(resource1: Gem, resource2: Gem, resource3: Gem)
    fun acquireWildcardAndTile(tileId: Int)
}