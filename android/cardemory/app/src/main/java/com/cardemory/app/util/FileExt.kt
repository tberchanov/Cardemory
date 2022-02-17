package com.cardemory.app.util

import java.io.File

fun File.createChildDirectoryIfNotExists(childDirectory: String): String {
    val directory = File(absolutePath, childDirectory)
    if (!directory.exists()) {
        directory.mkdirs()
    }
    return directory.absolutePath
}