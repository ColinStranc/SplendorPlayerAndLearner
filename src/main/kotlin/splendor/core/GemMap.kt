package splendor.core

import java.lang.Exception

// TODO: Should this be more like a POKO?
//  a) is it/should it be serializable?
//  b) should it have negative value checking?
class GemMap(private val map: Map<Gem, Int>) {
    init {
        for (gem in Gem.values()) {
            // TODO: unnecessary variable?
            val providedGemCount = map[gem]
            if (providedGemCount != null && providedGemCount < 0) {
                throw Exception("GemMap provided invalid value $providedGemCount for gem ${gem.name}")
            }
        }
    }

    operator fun get(gem: Gem): Int {
        return map[gem] ?: 0
    }

    companion object {
        fun plus(g1: GemMap, g2: GemMap): GemMap {
            return GemMap(
                Gem.values().associate { gem -> Pair(gem, g1[gem] + g2[gem]) })
        }
    }
}
