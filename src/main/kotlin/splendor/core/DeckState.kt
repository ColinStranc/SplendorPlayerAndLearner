package splendor.core

// TODO: This doesn't feel quite right to encapsulate the whole deck thing...
data class DeckState(val id: String, val name: String, val deck: List<Tile>, val displayed: List<Tile>)