package splendor.core.model

data class TurnState(
    val players: Map<String, PlayerState>,
    val sets: Map<String, TileSetState>,
    val availableInfluenceCards: List<InfluenceCard>,
    val activePlayeId: String
)
