package com.cardemory.common.data

interface KeyValueRepository {

    suspend fun readBoolean(key: String): Boolean

    suspend fun readString(key: String): String

    suspend fun <V> writeValue(key: String, value: V): Boolean
}