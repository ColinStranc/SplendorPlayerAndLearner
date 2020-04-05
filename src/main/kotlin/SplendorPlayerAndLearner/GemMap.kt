package SplendorPlayerAndLearner

import java.lang.Exception

class GemMap(private val map: Map<Gem, Int>) {
    init {
        for (gem in Gem.values()) {
            val providedGemCount = map[gem]
            if (providedGemCount != null && providedGemCount < 0) {
                throw Exception("GemMap provided invalid value $providedGemCount for gem ${gem.name}")
            }
        }
    }

    operator fun get(gem: Gem): Int {
        return map[gem] ?: 0
    }
}
