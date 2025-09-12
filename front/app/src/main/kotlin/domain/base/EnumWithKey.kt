package com.application.domain.base

import kotlin.collections.firstOrNull

interface EnumWithKey {
    val key: String
}

inline fun <reified T> firstOrDefault(
    key: String,
    defIndex: Int = 0
): T where T : Enum<T>, T : EnumWithKey {
    return enumValues<T>().firstOrNull { it.key == key } ?: enumValues<T>()[defIndex]
}

inline fun <reified T> firstOrNull(
    key: String
): T? where T : Enum<T>, T : EnumWithKey {
    return enumValues<T>().firstOrNull { it.key == key }
}