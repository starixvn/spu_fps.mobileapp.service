package com.fps.android.core.utils

internal fun <T> safeException(
    block: () -> T,
    defaultValue: T
): T {
    try {
        return block()
    }
    catch (_: Exception) {

    }
    return defaultValue
}