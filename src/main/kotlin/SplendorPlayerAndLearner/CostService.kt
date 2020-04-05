package SplendorPlayerAndLearner

object CostService {
    fun appropriatePayment(cost: GemMap, payment: Payment): Boolean {
        for (gem in Gem.values()) {
            if (compareCostToPayment(cost[gem], payment.chips[gem] + payment.wildcards[gem], payment.tiles[gem]) != 0) {
                return false
            }
        }

        return true
    }

    fun compareCostToPayment(cost: Int, chipPayment: Int, tilePayment: Int): Int {
        if (cost < chipPayment) {
            return 1
        }

        if (cost <= chipPayment + tilePayment) {
            return 0
        }

        return -1
    }
}