package splendor.core

interface CostService {
    fun compareCostToPayment(cost: Int, chipPayment: Int, tilePayment: Int): Int
}