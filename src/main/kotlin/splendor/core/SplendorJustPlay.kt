package splendor.core

class SplendorJustPlay(val costService: CostService, val p1: String, val p2: String) : Splendor {
    override fun getState(): State {
        TODO("Not yet implemented")
    }

    override fun buyTile(tileId: Int) {
        TODO("Not yet implemented")
    }

    override fun acquireTwoOfSameResource(resource: Gem) {
        TODO("Not yet implemented")
    }

    override fun acquireThreeDistinctResources(resource1: Gem, resource2: Gem, resource3: Gem) {
        TODO("Not yet implemented")
    }

    override fun acquireWildcardAndTile(tileId: Int) {
        TODO("Not yet implemented")
    }
}