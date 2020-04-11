package SplendorPlayerAndLearner

// TODO: Does making these lists and such public mean people can modify the list itself through the accessor? I want to
//  make this information readily available to whoever owns me, but I don't want anyone to be able modify anything about
//  me.

// Note: Do I want convenience accessors here or do I want a StateService?
data class State(
    val players: List<PlayerState>,
    val tierDecks: List<DeckState>,
    val activeTurnIndex: Int,
    val settings: Settings
)