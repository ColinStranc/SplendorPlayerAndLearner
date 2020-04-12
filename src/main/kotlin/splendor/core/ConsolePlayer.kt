package splendor.core

import java.lang.NumberFormatException
import kotlin.text.StringBuilder

class ConsolePlayer {
    private val DEFAULT_NAME_PREFIX = "no-name-"

    fun play() {
        val game: Splendor = startGame()

        var state: State = game.getState()
        while (!state.finished) {
            println("Displayed Tiles:")
            printTiles(state)
            println()

            // TODO: I don't think we want to get active player state like this
            //  really I think state has to be reorganized and rethought
            val active = state.players[state.activeTurnIndex]
            println("Player '${active.player.name}' possessions:")
            printPossessions(active)
            println()

            act(game)

            state = game.getState()
        }

        val winner: Player? = state.winner

        if (winner == null) {
            throw Exception("Finished game with no winner.")
        } else {
            println("Game Finished! '${winner.name}' won.")
        }

    }

    private fun startGame(): Splendor {
        print("Enter Player 1 name: ")
        val pOneName = readLine()
        println("Player 1 named: '$pOneName'.")

        print("Enter Player 2 name: ")
        val pTwoName = readLine()
        println("Player 2 named: '$pTwoName'.")

        return SplendorJustPlay(
            SimpleCostService,
            pOneName ?: "<${DEFAULT_NAME_PREFIX}1>",
            pTwoName ?: "<${DEFAULT_NAME_PREFIX}2>"
        )
    }

    private fun act(game: Splendor) {
        while (true) {
            print("Enter Action ('P2', 'P3', 'R', 'B'): ")
            val action = readLine()
            if (action == "P2") {
                try {
                    val gem = readGem()

                    game.acquireTwoOfSameResource(gem)
                    return
                } catch (e: Exception) {
                    println("Failed to retrieve 2 of same resource: ${e.message}")
                }
            } else if (action == "P3") {
                try {
                    val g1 = readGem()
                    val g2 = readGem()
                    val g3 = readGem()

                    game.acquireThreeDistinctResources(g1, g2, g3)
                    return
                } catch (e: Exception) {
                    println("Failed to retrieve 3 distinct resources: ${e.message}")
                }
            } else if (action == "R") {
                try {
                    val tileId = readId("Tile")

                    game.acquireWildcardAndTile(tileId)
                    return
                } catch (e: Exception) {
                    println("Failed to reserve tile: ${e.message}")
                }
            } else if (action == "B") {
                try {
                    val tileId = readId("Tile")

                    game.buyTile(tileId)
                    return
                } catch (e: Exception) {
                    println("Failed to buy tile: ${e.message}")
                }
            } else {
                println("Unrecognized action: $action")
            }

            println("Please try again.")
        }
    }

    private fun printTiles(state: State) {
        val tiersStates = state.tierDecks
        for ((_, name, deck, displayed1) in tiersStates) {
            print("${name}:")
            for (displayed in displayed1) {
                print(" | ${tileDisplayString(displayed)}")
            }
            println(" | and ${deck.size} more in the deck")
        }
    }

    private fun printPossessions(playerState: PlayerState) {
        val b = StringBuilder()

        b.append("(${playerState.player.id} - ${playerState.vp} VPs)")

        b.appendln(
            " chips ${gemMapDisplayString(playerState.chips)}, tiles " +
                    "${gemMapDisplayString(playerState.tileGems)} and ${playerState.wildcards} wildcards."
        )
        if (playerState.reservedTiles.isNotEmpty()) {
            b.append("Reserved Tiles")
            for (tile in playerState.reservedTiles) {
                b.append(" | ${tileDisplayString(tile)}")
            }
        }
        b.appendln()

        print(b.toString())
    }

    private fun tileDisplayString(tile: Tile): String {
        val b = StringBuilder()

        b.append("(${tile.id})")

        b.append(" pay ${gemMapDisplayString(tile.cost)}")
        b.append(" for ${gemMapDisplayString(tile.rewardGems)}")
        if (tile.victoryPoints > 0) {
            b.append(" and ${tile.victoryPoints} VP")
        }

        return b.toString()
    }

    private fun gemMapDisplayString(gm: GemMap): String {
        val sb = StringBuilder()

        for (gem in Gem.values()) {
            if (gm[gem] != 0) {
                sb.append(" ${gemDisplayString(gem)}=${gm[gem]}")
            }
        }

        return sb.toString()
    }

    private fun gemDisplayString(gem: Gem): String {
        if (gem == Gem.BLACK) {
            return "BLK"
        }
        if (gem == Gem.BLUE) {
            return "BLU"
        }
        if (gem == Gem.GREEN) {
            return "GRN"
        }
        if (gem == Gem.WHITE) {
            return "WHT"
        }
        if (gem == Gem.RED) {
            return "RED"
        }

        throw Exception("Invalid Gem!")
    }

    private fun readGem(): Gem {
        print("Enter Gem ('BLK', 'BLU', 'GRN', 'RED', 'WHT'): ")
        val gemName = readLine()
        val gem: Gem
        gem = when (gemName) {
            "BLK" -> {
                Gem.BLACK
            }
            "BLU" -> {
                Gem.BLUE
            }
            "GRN" -> {
                Gem.GREEN
            }
            "RED" -> {
                Gem.RED
            }
            "WHT" -> {
                Gem.WHITE
            }
            else -> {
                throw Exception("Unrecognized Gem: $gemName")
            }
        }

        return gem
    }

    private fun readId(idType: String): String {
        print("Enter $idType Id: ")
        val idString = readLine()
        if (idString != null) {
            return idString
        }

        throw Exception("Could not read Id from '$idString'.")
    }
}