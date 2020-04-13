package splendor.core.model

// TODO: should this have an idea of the currency for the schema? is it provided or calculated?
data class Schema(val id: String, val sets: List<TileSet>, val influenceCard: List<InfluenceCard>)
