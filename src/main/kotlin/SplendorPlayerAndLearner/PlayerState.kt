package SplendorPlayerAndLearner

data class PlayerState(
    val player: Player,
    val chips: GemMap,
    val wildcards: Int,
    val tiles: List<Tile>,
    val reservedTiles: List<Tile>,
    val influenceCards: List<InfluenceCard>
) {
}