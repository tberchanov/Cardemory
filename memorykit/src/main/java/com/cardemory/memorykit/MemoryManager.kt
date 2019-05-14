package com.cardemory.memorykit

class MemoryManager {

    fun forget(memoryHolder: MemoryHolder): Double {
        val lastTrainDays = millisToDays(memoryHolder.lastTrainMillis)
        val calculatedMemoryRank =
            Math.exp(-lastTrainDays / (FORGETTING_SCALE_FACTOR / memoryHolder.memoryRank))

        memoryHolder.memoryRank = calculatedMemoryRank
        memoryHolder.lastTrainMillis = System.currentTimeMillis()
        return calculatedMemoryRank
    }

    fun remember(memoryHolder: MemoryHolder): Double {
        val lastTrainDays = millisToDays(memoryHolder.lastTrainMillis)
        val calculatedMemoryRank =
            1.0 / (1.0 + Math.exp(-lastTrainDays + OX_SIGMOID_TRANSITION))

        memoryHolder.memoryRank = calculatedMemoryRank
        memoryHolder.lastTrainMillis = System.currentTimeMillis()
        return calculatedMemoryRank
    }

    private fun millisToDays(millis: Long): Double = millis / MILLIS_IN_DAY

    companion object {
        private const val FORGETTING_SCALE_FACTOR = 30.0
        private const val OX_SIGMOID_TRANSITION = 6.0
        private const val MILLIS_IN_DAY = 1000.0 * 60.0 * 60.0 * 24.0
    }
}