package splendor.core

/**
 * Payment in the form of chips, reusable tile resources, and wildcards. The wildcards must already be assigned to Gem
 * types.
 * @param chips
 * @param tiles
 * @param wildcards
 */
data class Payment(val chips: GemMap, val tiles: GemMap, val wildcards: GemMap)
