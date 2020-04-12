package splendor.core

// TODO: Does making these lists and such public mean people can modify the list itself through the accessor? I want to
//  make this information readily available to whoever owns me, but I don't want anyone to be able modify anything about
//  me.

// Note: Do I want convenience accessors here or do I want a StateService?
// TODO: Influence Cards
data class State(
    val players: List<PlayerState>,
    val tierDecks: List<DeckState>,
    val activeTurnIndex: Int,
    val settings: Settings,
    // TODO: I don't like having this be a parameter, but we can't actually determine the winner from the rest of the
    //  state, so I think it needs to be. Is there a better way?
    val winner: Player?
) {
    val finished: Boolean = winner != null
}