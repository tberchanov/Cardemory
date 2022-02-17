package com.cardemory.app.util.logging

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.cardemory.app.util.DateTimeFormatter
import com.cardemory.app.util.createChildDirectoryIfNotExists
import java.io.BufferedWriter
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class FileTimberTree(context: Context) : DebugTree() {

    private var writer: BufferedWriter? = null
    private val dateTimeFormatter = DateTimeFormatter()

    private var selfErrorLogged = false

    init {
        try {
            createNewFile(context)
        } catch (e: IOException) {
            onSelfError(e)
        }
    }

    private fun createNewFile(context: Context) {
        val directory = context.getExternalFilesDir(null)
            ?.createChildDirectoryIfNotExists(LOGS_DIRECTORY)
        val file = File(directory, createNewFileName())
        file.createNewFile()
        writer = file.bufferedWriter()
    }

    private fun createNewFileName() =
        SimpleDateFormat(FILE_NAME_PATTERN, Locale.US).format(Date())

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        try {
            writer?.apply {
                write(formatLog(priority, tag, message))
                newLine()
                flush()
            }
        } catch (e: IOException) {
            onSelfError(e)
        }
    }

    private fun formatLog(priority: Int, tag: String?, message: String): String {
        val time = dateTimeFormatter.getLogTimeString(System.currentTimeMillis())
        val level = getLogLevelString(priority)
        return LOG_PATTERN.format(time, level, tag, message)
    }

    private fun getLogLevelString(level: Int) =
        when (level) {
            Log.VERBOSE -> "V"
            Log.DEBUG -> "D"
            Log.INFO -> "I"
            Log.WARN -> "W"
            Log.ERROR -> "E"
            Log.ASSERT -> "A"
            else -> null
        }

    @SuppressLint("LogNotTimber")
    private fun onSelfError(e: IOException) {
        if (selfErrorLogged) return

        Log.e(SELF_LOG_TAG, "Local internal error", e)
        selfErrorLogged = true
    }

    companion object {
        private const val LOGS_DIRECTORY = "Logs"
        private const val FILE_NAME_PATTERN = "'Log_'yyyy_MM_dd_HH_mm_ss'.txt'"
        private const val LOG_PATTERN = "%1\$s %2\$s/%3\$s %4\$s"

        private const val SELF_LOG_TAG = "FileTimberTree"
    }
}