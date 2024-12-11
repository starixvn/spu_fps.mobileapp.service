package com.fps.android.core.utils

internal fun <T> safeException(
    block: () -> T,
    defaultValue: T
): T {
    try {
        return block()
    }
    catch (e: Exception) {
        ThrowableHandler.add(e.message ?: "")
    }
    catch (e: NoSuchMethodError) {
        ThrowableHandler.add(e.message ?: "")
    }
    catch (e: Error) {
        ThrowableHandler.add(e.message ?: "")
    }

    return defaultValue
}

internal class ThrowableHandler {
    companion object {
        private var exceptions = mutableListOf<String>()

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