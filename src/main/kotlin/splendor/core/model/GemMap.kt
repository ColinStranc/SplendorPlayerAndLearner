package splendor.core.model

import java.lang.Exception

// TODO: Should this be more like a POKO?
//  a) is it/should it be serializable?
//  b) should it have negative value checking?
// TODO: Is this even necessary? Should we be doing a simple map and bubble in-use gems up to schema level? Ensure every
//  tile in the set uses the same gem set? That doesn't make sense because something that uses black and blue can be
//  part of any schema which uses black and blue, even if they use more and a simple map would fail on non-present gems.
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
