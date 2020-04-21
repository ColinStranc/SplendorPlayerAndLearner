package splendor.core.model

interface StateManager {
    fun initiateState(state: State)
    fun updateState(gameId: String, newState: TurnState)
    fun getActiveState(gameId: String): TurnState
    fun getGameState(gameId: String): GameState
    fun getAllTurnStates(gameId: String): List<TurnState>
}