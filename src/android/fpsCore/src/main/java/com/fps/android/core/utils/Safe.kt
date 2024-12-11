package com.fps.android.core.utils

internal fun <T> safeException(
    block: () -> T,
    defaultValue: T
): T {
    try {
        return block()
    }
    catch (e: Throwable) {
        ThrowableHandler.add(e)
    }

    return defaultValue
}

internal class ThrowableHandler {
    companion object {
        private var exceptions = mutableListOf<String>()

        fun add(e: Throwable) {
            exceptions.add("${e.message} at ${e.stackTrace.firstOrNull().toString()}")
        }

        fun get(): String {
            val result = exceptions.joinToString(";")
            exceptions.clear()

            return result
        }
    }
}