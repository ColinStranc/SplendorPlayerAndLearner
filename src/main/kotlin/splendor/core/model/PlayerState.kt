package splendor.core.model

data class PlayerState(
    val chips: GemMap,
    val wildcards: Int,
    val tiles: List<Tile>,
    val reserved: List<Tile>,
    val influenceCards: List<InfluenceCard>
)
