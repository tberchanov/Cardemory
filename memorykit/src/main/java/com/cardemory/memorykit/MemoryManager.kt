package com.cardemory.memorykit

class MemoryManager {

    fun forget(memoryHolder: MemoryHolder): Double {
        val currentTimeMillis = System.currentTimeMillis()
        val lastTrainMillisDelta = if (memoryHolder.lastTrainMillis <= 0) {
            0
        } else {
            currentTimeMillis - memoryHolder.lastTrainMillis
        }
        val lastTrainDaysDelta = millisToDays(lastTrainMillisDelta)
        var calculatedMemoryRank =
            1.0 - Math.exp(-lastTrainDaysDelta / (FORGETTING_SCALE_FACTOR / memoryHolder.memoryRank))
        if (calculatedMemoryRank >= memoryHolder.memoryRank) {
            calculatedMemoryRank = MIN_MEMORY_RANK
        }

        memoryHolder.memoryRank = calculatedMemoryRank
        memoryHolder.lastTrainMillis = currentTimeMillis
        return calculatedMemoryRank
    }

    fun remember(memoryHolder: MemoryHolder): Double {
        val currentTimeMillis = System.currentTimeMillis()
        val lastTrainMillisDelta = if (memoryHolder.lastTrainMillis <= 0) {
            0
        } else {
            currentTimeMillis - memoryHolder.lastTrainMillis
        }
        val lastTrainDaysDelta = millisToDays(lastTrainMillisDelta)
        var calculatedMemoryRank =
            1.0 / (1.0 + Math.exp(-lastTrainDaysDelta * SIGMOID_SCALE_FACTOR + OX_SIGMOID_TRANSITION))
        calculatedMemoryRank += memoryHolder.memoryRank
        if (calculatedMemoryRank > MAX_MEMORY_RANK) {
            calculatedMemoryRank = MAX_MEMORY_RANK
        }
        memoryHolder.memoryRank = calculatedMemoryRank
        memoryHolder.lastTrainMillis = currentTimeMillis
        return calculatedMemoryRank
    }

    private fun millisToDays(millis: Long): Double = millis / MILLIS_IN_DAY

    companion object {
        private const val MIN_MEMORY_RANK = 0.0
        private const val MAX_MEMORY_RANK = 1.0

        private const val FORGETTING_SCALE_FACTOR = 30.0
        private const val OX_SIGMOID_TRANSITION = 6.0
        private const val SIGMOID_SCALE_FACTOR = 0.5
        private const val MILLIS_IN_DAY = 1000.0 * 60.0 * 60.0 * 24.0
    }
}
