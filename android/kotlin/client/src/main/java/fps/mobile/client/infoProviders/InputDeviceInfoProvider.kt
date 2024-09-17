package fps.mobile.client.infoProviders

import android.content.Context
import android.view.inputmethod.InputMethodInfo
import android.view.inputmethod.InputMethodManager

internal interface  IInputDeviceInfoProvider {
    fun hasKeyboard(name: String): Boolean
}

internal class InputDeviceInfoProvider(
    context: Context
) : IInputDeviceInfoProvider {

    private val inputMethodManager: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    override fun hasKeyboard(name: String): Boolean {
        val inputMethods: List<InputMethodInfo> = inputMethodManager.enabledInputMethodList

        if(inputMethods.isNotEmpty()) {
            inputMethods.forEach {
                val serviceName = it.serviceName.lowercase()
                val id = it.id.lowercase()

                if(serviceName.contains(name.lowercase())
                    || id.contains(name.lowercase())
                ) {
                    return true
                }
            }
        }

        return false
    }

}