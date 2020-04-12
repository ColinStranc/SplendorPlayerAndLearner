package splendor.core

import java.lang.Exception

fun main() {
    println("Starting Splendor Console...")

    try {
        val player = ConsolePlayer()

        player.play()
    } catch (e: Exception) {
        println(e)
    } finally {
        println("Finished Splendor Console")
    }
}