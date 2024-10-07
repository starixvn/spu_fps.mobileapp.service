package com.fps.demoapp.ui.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.fps.android.sdk.FPS
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking

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
            runBlocking {
                kotlin.runCatching {
//                    delay(1000L)
                    val trans = FPS.onCheckFPSTrans(context, _uiState.value.username, "Alpha", "login") ?: "null"

                    _uiState.update { currentState ->
                        currentState.copy(
                            fpsTrans = trans
                        )
                    }

                }.getOrElse { exception ->
                    Log.d("FPS SDK", exception.message!!)
                }
            }

            return true
        }
        return false
    }

}