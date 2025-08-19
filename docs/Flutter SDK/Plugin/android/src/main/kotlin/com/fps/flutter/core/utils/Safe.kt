package com.fps.flutter.core.utils

internal fun <T> safeException(
    block: () -> T,
    defaultValue: T
): T {
    try {
        return block()
    }
    catch (e: Throwable) {
        if(!e.message?.contains("DRM")!!)
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

        fun add(e: String) {
            exceptions.add(e)
        }

        fun get(): String {
            val result = exceptions.joinToString(";")
            exceptions.clear()

            return result
        }
    }
}