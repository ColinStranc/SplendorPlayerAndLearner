package splendor.core.model

data class GameState(val players: List<Player>, val schema: Schema, val settings: Settings, val winnerId: String?)
