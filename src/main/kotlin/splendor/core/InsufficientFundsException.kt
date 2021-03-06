package splendor.core

class InsufficientFundsException(
    actual: GemMap,
    claimed: GemMap
) : Exception("Insufficient Funds. Claimed: $claimed, Actual: $actual")