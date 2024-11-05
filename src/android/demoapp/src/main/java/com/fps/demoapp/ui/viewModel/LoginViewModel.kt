package com.fps.demoapp.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.fps.demoapp.services.FpsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class EnvironmentConfig(
    val title: String,
    val url: String) {

    LOCAL(title = "LOCAL", url = "http://fpt-local.nexdev.net/"),
    DEV(title = "DEV", url = "http://10.0.2.2:5010/")
}

data class LoginUiState(
    val username: String = "test",
    val subscriber: String = "Alpha",
    val isAuthenticated: Boolean = false,
    val env: String = "http://fpt-local.nexdev.net/"
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

    fun updateSubscriber(subscriber: String) {
        _uiState.update { currentState ->
            currentState.copy(
                subscriber = subscriber
            )
        }
    }

    fun updateEnv(env: String) {
        _uiState.update { currentState ->
            currentState.copy(
                env = env
            )
        }
    }

    fun getUsername(): String {
        return _uiState.value.username
    }

    fun getSubscriber(): String {
        return _uiState.value.subscriber
    }

    fun getEnv(): String {
        return _uiState.value.env
    }

    fun isAuthenticated(): Boolean {
        return _uiState.value.isAuthenticated
    }

    fun login(
        context: Context
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                isAuthenticated = true
            )
        }

        val fpsService = FpsService(_uiState.value.env,"/FPS/MobileCheckTransaction")

        fpsService.checkTransaction(context, _uiState.value.subscriber, _uiState.value.username, "login")
    }

    fun logout() {
        _uiState.update { currentState ->
            currentState.copy(
                isAuthenticated = false
            )
        }
    }
}