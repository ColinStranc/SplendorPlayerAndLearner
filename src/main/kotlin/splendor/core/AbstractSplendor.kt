package splendor.core

import splendor.core.model.StateManager
// TODO: get rid of this baloney once there is only one applicable copy of State and such
import splendor.core.model.State as mState

const val TURN_INDEX_PROGRESSION: Int = 1

// TODO: separate ctors, one for start new game which only takes state manager and then initiates a new game, one for
//  continue saved game, which takes state manager and a game Id.
abstract class AbstractSplendor(protected val stateManager: StateManager, protected val gameId: String) : Splendor {
    override fun buyTile(tileId: String, chips: GemMap, wildcards: GemMap) {
        val newState = updateActiveTurn(buyTileStateUpdate(tileId, chips, wildcards))
        stateManager.updateState(gameId, newState.turnState)
    }

    override fun acquireTwoOfSameResource(resource: Gem) {
        val newState = updateActiveTurn(acquireTwoOfSameResourceStateUpdate(resource))
        stateManager.updateState(gameId, newState.turnState)
    }

    override fun acquireThreeDistinctResources(resource1: Gem, resource2: Gem, resource3: Gem) {
        val newState = updateActiveTurn(acquireThreeDistinctResourcesStateUpdate(resource1, resource2, resource3))
        stateManager.updateState(gameId, newState.turnState)
    }

    override fun acquireWildcardAndTile(tileId: String) {
        val newState = updateActiveTurn(acquireWildcardAndTileStateUpdate(tileId))
        stateManager.updateState(gameId, newState.turnState)
    }

    private fun updateActiveTurn(state: mState): mState {
        val activeIndex = state.gameState.players.indexOfFirst { (id) -> state.turnState.activePlayeId == id }
        val nextPlayerId = state.gameState.players[
                (activeIndex + TURN_INDEX_PROGRESSION) % state.gameState.players.size
        ].id
        return state.copy(turnState = state.turnState.copy(activePlayeId = nextPlayerId))
    }

    abstract fun buyTileStateUpdate(tileId: String, chips: GemMap, wildcards: GemMap): mState
    abstract fun acquireTwoOfSameResourceStateUpdate(resource: Gem): mState
    abstract fun acquireThreeDistinctResourcesStateUpdate(resource1: Gem, resource2: Gem, resource3: Gem): mState
    abstract fun acquireWildcardAndTileStateUpdate(tileId: String): mState
}