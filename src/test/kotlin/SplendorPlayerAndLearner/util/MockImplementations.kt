package SplendorPlayerAndLearner.util

import SplendorPlayerAndLearner.CostService

object MockImplementations {
    fun successCostService(): CostService {
        return object : CostService {
            override fun compareCostToPayment(cost: Int, chipPayment: Int, tilePayment: Int): Int {
                return 0
            }
        }
    }
}