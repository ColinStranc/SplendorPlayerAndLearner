package splendor.core.util

import splendor.core.*

object MockImplementations {
    fun successCostService(): CostService {
        return object : CostService {
            override fun compareCostToPayment(cost: Int, chipPayment: Int, tilePayment: Int): Int {
                return 0
            }
        }
    }

    fun simpleState(): State {
        return State(
            oneEmptyPlayer(),
            noDecks(),
            0,
            baseSettings(),
            null
        )
    }

    // TODO: I should be able to create a no player game and that should be this default
    fun oneEmptyPlayer(): List<PlayerState> {
        return listOf(emptyPlayer("p1"))
    }

    fun noDecks(): List<DeckState> {
        return listOf()
    }

    fun emptyPlayer(nameAndId: String): PlayerState {
        return PlayerState(
            Player(nameAndId, nameAndId),
            GemMap(mapOf()),
            0,
            listOf(),
            listOf(),
            listOf()
        )
    }

    fun emptyTile(id: String): Tile {
        return Tile(id, GemMap(mapOf()), GemMap(mapOf()), 0)
    }

    // TODO: What kind of settings?
    fun baseSettings(): Settings {
        return Settings(5, 5)
    }
}