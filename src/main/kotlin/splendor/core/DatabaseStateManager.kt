package splendor.core

import splendor.core.model.GameState
import splendor.core.model.State
import splendor.core.model.StateManager
import splendor.core.model.TurnState

abstract class DatabaseStateManager : StateManager {
    override fun initiateState(state: State) {
        val turnId = insertTurnState(state.turnState)
        val turnStateRecordId = insertInitialTurnStateRecord(turnId)
        insertGameState(state.gameState, turnStateRecordId)
    }

    override fun updateState(gameId: String, newState: TurnState) {
        val stateId = insertTurnState(newState)
        val previousActiveStateId = selectActiveStateId(gameId)
        val turnStateRecordId = insertTurnStateRecord(stateId, previousActiveStateId)
        updateActiveStateId(gameId, turnStateRecordId)
    }

    override fun getActiveState(gameId: String): TurnState {
        val activeStateId = selectActiveStateId(gameId)
        return selectState(activeStateId)
    }

    override fun getGameState(gameId: String): GameState {
        return selectGameState(gameId)
    }

    override fun getAllTurnStates(gameId: String): List<TurnState> {
        val turnStates: List<TurnState> = mutableListOf()

        var latestTurnStateRecordId: Int? = selectActiveStateId(gameId)
        while (latestTurnStateRecordId != null) {
            val turnStateRecord = selectTurnStateRecord(latestTurnStateRecordId)
            turnStates.plus(selectState(turnStateRecord.turnStateId))
            latestTurnStateRecordId = turnStateRecord.previousTurnStateRecordId
        }

        return turnStates
    }

    protected abstract fun insertTurnState(state: TurnState): Int
    protected abstract fun insertInitialTurnStateRecord(turnStateId: Int): Int
    protected abstract fun insertTurnStateRecord(turnStateId: Int, previousTurnStateId: Int): Int
    protected abstract fun insertGameState(gameState: GameState, firstTurnStateRecord: Int): Int
    protected abstract fun selectActiveStateId(externalGameId: String): Int
    protected abstract fun selectGameState(externalGameId: String): GameState
    protected abstract fun selectState(id: Int): TurnState
    protected abstract fun selectTurnStateRecord(turnStateRecordId: Int): TurnStateRecord
    protected abstract fun updateActiveStateId(externalGameId: String, newTurnStateRecordId: Int)

}