package splendor.core

data class PlayerState(
    val player: Player,
    val chips: GemMap,
    val wildcards: Int,
    val tiles: List<Tile>,
    val reservedTiles: List<Tile>,
    val influenceCards: List<InfluenceCard>
) {
    val vp: Int = (influenceCards.fold(0) { sum, ic ->
        sum + ic.victoryPoints
    }) + (tiles.fold(0) { sum, t ->
        sum + t.victoryPoints
    })

    val tileGems: GemMap = tiles.fold(GemMap(mapOf())) { map, tile ->
        GemMap((Gem.values().associate { g -> Pair(g, map[g] + tile.rewardGems[g]) }))
    }
}