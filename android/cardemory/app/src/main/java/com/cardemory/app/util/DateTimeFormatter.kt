package com.cardemory.app.util

import java.text.SimpleDateFormat
import java.util.*

class DateTimeFormatter {

    private val logTimeFormatLazy = lazy { SimpleDateFormat(LOG_TIME_FORMAT, Locale.US) }

    fun getLogTimeString(millis: Long): String {
        return logTimeFormatLazy.value.format(millis)
    }

    companion object {
        private const val LOG_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS"
    }
}