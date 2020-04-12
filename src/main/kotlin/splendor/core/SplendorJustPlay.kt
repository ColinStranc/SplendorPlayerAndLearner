package splendor.core

class SplendorJustPlay(private val costService: CostService, p1: String, p2: String) : Splendor {
    private var state: State = State(
        settings = Settings(5, 5),
        winner = null,
        // TODO: Ids?
        players = listOf(initialPlayerState("1", p1), initialPlayerState("2", p2)),
        activeTurnIndex = 0,
        tierDecks = initialTierDecks()
    )

    override fun getState(): State {
        return state.copy();
    }

    override fun buyTile(tileId: String) {
        TODO("Not yet implemented")
    }

    override fun acquireTwoOfSameResource(resource: Gem) {
        TODO("Not yet implemented")
    }

    override fun acquireThreeDistinctResources(resource1: Gem, resource2: Gem, resource3: Gem) {
        TODO("Not yet implemented")
    }

    override fun acquireWildcardAndTile(tileId: String) {
        TODO("Not yet implemented")
    }

    companion object {
        private fun initialPlayerState(id: String, name: String): PlayerState {
            return PlayerState(
                Player(id, name),
                chips = GemMap(mapOf()),
                tiles = listOf(),
                reservedTiles = listOf(),
                wildcards = 0,
                influenceCards = listOf()
            )
        }

        private fun initialTierDecks(): List<DeckState> {
            return listOf(deck1(), deck2(), deck3())
        }

        private fun deck1(): DeckState {
            val t1n1 = Tile(
                "t1n1",
                GemMap(mapOf(Gem.BLUE to 2, Gem.GREEN to 1)),
                GemMap(mapOf(Gem.RED to 1)),
                0
            )
            val t1n2 = Tile(
                "t1n2",
                GemMap(mapOf(Gem.BLUE to 1, Gem.GREEN to 1, Gem.WHITE to 1, Gem.RED to 1)),
                GemMap(mapOf(Gem.BLACK to 1)),
                0
            )
            val t1n3 = Tile(
                "t1n3",
                GemMap(mapOf(Gem.BLACK to 2, Gem.GREEN to 2)),
                GemMap(mapOf(Gem.BLUE to 1)),
                0
            )
            val t1n4 = Tile(
                "t1n4",
                GemMap(mapOf(Gem.BLUE to 1, Gem.RED to 1, Gem.BLACK to 1, Gem.WHITE to 1)),
                GemMap(mapOf(Gem.GREEN to 1)),
                0
            )
            val t1n5 = Tile(
                "t1n5",
                GemMap(mapOf(Gem.BLUE to 2, Gem.GREEN to 2, Gem.BLACK to 1)),
                GemMap(mapOf(Gem.WHITE to 1)),
                0
            )
            val t1n6 = Tile(
                "t1n6",
                GemMap(mapOf(Gem.BLUE to 2, Gem.WHITE to 2, Gem.RED to 1)),
                GemMap(mapOf(Gem.BLACK to 1)),
                0
            )
            val t1n7 = Tile(
                "t1n7",
                GemMap(mapOf(Gem.BLACK to 4)),
                GemMap(mapOf(Gem.GREEN to 1)),
                1
            )
            val t1n8 = Tile(
                "t1n8",
                GemMap(mapOf(Gem.RED to 1)),
                GemMap(mapOf(Gem.GREEN to 1)),
                0
            )

            return DeckState("d1", "Tier 1", listOf(t1n5, t1n6, t1n7, t1n8), listOf(t1n1, t1n2, t1n3, t1n4))
        }

        private fun deck2(): DeckState {
            val t2n1 = Tile(
                "t2n1",
                GemMap(mapOf(Gem.BLUE to 4, Gem.GREEN to 2, Gem.WHITE to 1)),
                GemMap(mapOf(Gem.RED to 1)),
                2
            )
            val t2n2 = Tile(
                "t2n2",
                GemMap(mapOf(Gem.BLUE to 5, Gem.GREEN to 3)),
                GemMap(mapOf(Gem.GREEN to 1)),
                2
            )
            val t2n3 = Tile(
                "t2n3",
                GemMap(mapOf(Gem.WHITE to 2, Gem.BLUE to 3, Gem.RED to 3)),
                GemMap(mapOf(Gem.WHITE to 1)),
                1
            )
            val t2n4 = Tile(
                "t2n4",
                GemMap(mapOf(Gem.WHITE to 3, Gem.RED to 3, Gem.GREEN to 2)),
                GemMap(mapOf(Gem.GREEN to 1)),
                1
            )
            val t2n5 = Tile(
                "t2n5",
                GemMap(mapOf(Gem.RED to 5, Gem.BLACK to 3)),
                GemMap(mapOf(Gem.WHITE to 1)),
                2
            )
            val t2n6 = Tile(
                "t2n6",
                GemMap(mapOf(Gem.WHITE to 5, Gem.BLUE to 3)),
                GemMap(mapOf(Gem.BLUE to 1)),
                2
            )
            val t2n7 = Tile(
                "t2n7",
                GemMap(mapOf(Gem.RED to 2, Gem.BLACK to 2, Gem.GREEN to 3)),
                GemMap(mapOf(Gem.WHITE to 1)),
                1
            )

            return DeckState("d2", "Tier 2", listOf(t2n5, t2n6, t2n7), listOf(t2n1, t2n2, t2n3, t2n4))
        }

        private fun deck3(): DeckState {
            val t3n1 = Tile(
                "t3n1",
                GemMap(mapOf(Gem.RED to 7)),
                GemMap(mapOf(Gem.BLACK to 1)),
                4
            )
            val t3n2 = Tile(
                "t3n2",
                GemMap(mapOf(Gem.BLUE to 7)),
                GemMap(mapOf(Gem.GREEN to 1)),
                4
            )
            val t3n3 = Tile(
                "t3n3",
                GemMap(mapOf(Gem.WHITE to 3, Gem.BLACK to 7)),
                GemMap(mapOf(Gem.WHITE to 1)),
                5
            )
            val t3n4 = Tile(
                "t3n4",
                GemMap(mapOf(Gem.WHITE to 7)),
                GemMap(mapOf(Gem.BLUE to 1)),
                4
            )
            val t3n5 = Tile(
                "t3n5",
                GemMap(mapOf(Gem.RED to 6, Gem.BLACK to 3, Gem.GREEN to 3)),
                GemMap(mapOf(Gem.BLACK to 1)),
                4
            )
            val t3n6 = Tile(
                "t3n6",
                GemMap(mapOf(Gem.BLUE to 7, Gem.GREEN to 3)),
                GemMap(mapOf(Gem.GREEN to 1)),
                5
            )
            val t3n7 = Tile(
                "t3n7",
                GemMap(mapOf(Gem.WHITE to 3, Gem.BLACK to 3, Gem.BLUE to 5, Gem.GREEN to 3)),
                GemMap(mapOf(Gem.RED to 1)),
                3
            )

            return DeckState("d3", "Tier 3", listOf(t3n5, t3n6, t3n7), listOf(t3n1, t3n2, t3n3, t3n4))
        }
    }
}