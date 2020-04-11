package splendor.core

// TODO: This really should encapsulate the idea of "Baseline math" where you have a value available to you which does
//  not have to be used. This concept can be tile and chip and cost agnostic.
object SimpleCostService : CostService {
    override fun compareCostToPayment(cost: Int, chipPayment: Int, tilePayment: Int): Int {
        if (cost < chipPayment) {
            return 1
        }

        if (cost <= chipPayment + tilePayment) {
            return 0
        }

        return -1
    }
}