package com.fps.flutter.core.infoProviders

import android.content.Context
import android.view.inputmethod.InputMethodInfo
import android.view.inputmethod.InputMethodManager
import com.fps.flutter.core.utils.safeException

internal interface  IInputDeviceInfoProvider {
    fun hasKeyboard(name: String): Boolean
}

internal class InputDeviceInfoProvider(
    context: Context
) : IInputDeviceInfoProvider {

    private val inputMethodManager: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    override fun hasKeyboard(name: String): Boolean {
        return safeException({
            val inputMethods: List<InputMethodInfo> = inputMethodManager.enabledInputMethodList

            if(inputMethods.isNotEmpty()) {
                inputMethods.forEach {
                    val serviceName = it.serviceName.lowercase()
                    val id = it.id.lowercase()

                    if(serviceName.contains(name.lowercase())
                        || id.contains(name.lowercase())
                    ) {
                        return@safeException true
                    }
                }
            }
            return@safeException false
        }, false)
    }

}