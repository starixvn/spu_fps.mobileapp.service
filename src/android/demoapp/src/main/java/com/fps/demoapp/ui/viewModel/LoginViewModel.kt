package com.fps.demoapp.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.fps.android.sdk.FPS
import com.fps.demoapp.R
import com.fps.demoapp.services.FpsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LoginUiState(
    val username: String = "test",
    val password: String = "1234",
    val isAuthenticated: Boolean = false
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

            _uiState.update { currentState ->
                currentState.copy(
                    isAuthenticated = true
                )
            }

            FpsService("http://10.0.2.2:5010/","/FPS/MobileCheckTransaction")
                .checkTransaction(context, "Maxbet" ,_uiState.value.username,"login")

            return true
        }
        return false
    }

}