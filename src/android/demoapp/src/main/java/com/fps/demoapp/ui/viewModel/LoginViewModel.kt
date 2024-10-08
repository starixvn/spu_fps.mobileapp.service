package com.fps.demoapp.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.fps.android.sdk.FPS
import com.fps.demoapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LoginUiState(
    val username: String = "test",
    val password: String = "1234",
    val isAuthenticated: Boolean = false,
    val fpsTrans: String? = null
)

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updateUserName(username: String) {
        _uiState.update { currentState ->
            currentState.copy(
                username = username
            )
        }
    }

    fun updatePassword(password: String) {
        _uiState.update { currentState ->
            currentState.copy(
                password = password
            )
        }
    }

    fun updateFpsTrans(trans: String?) {
        _uiState.update { currentState ->
            currentState.copy(
                fpsTrans = trans
            )
        }
    }

    fun getUsername(): String {
        return _uiState.value.username
    }

    fun isAuthenticated(): Boolean {
        return _uiState.value.isAuthenticated
    }

    fun login(
        context: Context
    ): Boolean {
        if (_uiState.value.username == "test" &&
            _uiState.value.password == "1234") {

            val trans = FPS.onCheckFPSTrans(context, _uiState.value.username,
                context.getString(R.string.subscriber_name), "login") ?: "null"
            // TODO("Send this trans to app service and forward it to FPS API")

            _uiState.update { currentState ->
                currentState.copy(
                    fpsTrans = trans,
                    isAuthenticated = true
                )
            }
            return true
        }
        return false
    }

}