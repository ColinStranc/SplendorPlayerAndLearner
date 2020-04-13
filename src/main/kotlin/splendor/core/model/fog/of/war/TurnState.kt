package splendor.core.model.fog.of.war

import splendor.core.model.InfluenceCard
import splendor.core.model.PlayerState

data class TurnState(
    val players: Map<String, PlayerState>,
    val tier: Map<String, TileSetState>,
    val availableInfluenceCards: List<InfluenceCard>,
    val activePlayerId: String
)
