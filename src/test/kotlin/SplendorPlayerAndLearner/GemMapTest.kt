package SplendorPlayerAndLearner

import kotlin.test.Test
import kotlin.test.assertEquals

class GemMapTest {
    @Test
    fun gemMapStoresValues() {
        val black = 1
        val blue = 1
        val green = 1
        val red = 1
        val white = 1

        val map: Map<Gem, Int> = mapOf(
            Gem.BLACK to black,
            Gem.BLUE to blue,
            Gem.GREEN to green,
            Gem.RED to red,
            Gem.WHITE to white
        )

        val gemMap: GemMap = GemMap(map)

        assertEquals(black, gemMap[Gem.BLACK], "GemMap stored the wrong number of black gems")
        assertEquals(blue, gemMap[Gem.BLUE], "GemMap stored the wrong number of blue gems")
        assertEquals(green, gemMap[Gem.GREEN], "GemMap stored the wrong number of green gems")
        assertEquals(red, gemMap[Gem.RED], "GemMap stored the wrong number of red gems")
        assertEquals(white, gemMap[Gem.WHITE], "GemMap stored the wrong number of white gems")
    }

    @Test
    fun gemMapPutsZeroDefaultWhenGemNotPresent() {
        val black = 1
        val blue = 1
        val green = 1
        val red = 1

        val map: Map<Gem, Int> = mapOf(
            Gem.BLACK to black,
            Gem.BLUE to blue,
            Gem.GREEN to green,
            Gem.RED to red
        )

        val gemMap: GemMap = GemMap(map)

        assertEquals(0, gemMap[Gem.WHITE], "Expected a default value of 0 gems, instead found ${gemMap[Gem.WHITE]}")
    }

    @Test
    fun canProvideEmptyMap() {
        val gemMap: GemMap = GemMap(mapOf())

        assertEquals(0, gemMap[Gem.BLACK], "Expected a default value of 0 gems, instead found ${gemMap[Gem.BLACK]}")
        assertEquals(0, gemMap[Gem.BLUE], "Expected a default value of 0 gems, instead found ${gemMap[Gem.BLUE]}")
        assertEquals(0, gemMap[Gem.GREEN], "Expected a default value of 0 gems, instead found ${gemMap[Gem.GREEN]}")
        assertEquals(0, gemMap[Gem.RED], "Expected a default value of 0 gems, instead found ${gemMap[Gem.RED]}")
        assertEquals(0, gemMap[Gem.WHITE], "Expected a default value of 0 gems, instead found ${gemMap[Gem.WHITE]}")
    }

    // TODO: Give this a better Exception type
    @Test(expected = Exception::class)
    fun gemMapFailsOnNegativeValues() {
        val black = -1

        val map: Map<Gem, Int> = mapOf(
            Gem.BLACK to black
        )

        GemMap(map)
    }
}
