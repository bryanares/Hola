package com.brian.hola.features.auth.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brian.hola.data.repository.HolaRepository
import com.brian.hola.data.utils.Rezults
import com.brian.hola.features.auth.domain.model.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val holaRepository: HolaRepository
) : ViewModel() {
    private val _authUiState = MutableStateFlow(AuthUiState())
    val authUiState = _authUiState.asStateFlow()


    fun resetState() {
        _authUiState.update { AuthUiState() }
    }

    fun login(userEmail: String, userPassword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                _authUiState.update {
                    AuthUiState(
                        isLoading = false,
                        isSuccessful = false,
                        error = "Please fill in all fields"
                    )
                }
                return@launch
            }
            holaRepository.login(userEmail, userPassword).collectLatest { rezult ->
                when (rezult) {
                    is Rezults.Success -> {
                        _authUiState.update {
                            AuthUiState(
                                isLoading = false,
                                isSuccessful = true,
                                userId = rezult.data.id
                            )
                        }
                    }

                    is Rezults.Error -> {
                        _authUiState.update {
                            AuthUiState(
                                isLoading = false,
                                isSuccessful = false,
                                error = rezult.exception?.message
                            )
                        }
                    }
                }
            }
        }
    }


    fun signUp(
        userEmail: String,
        userPassword: String,
        userConfirmPassword: String,
        userName: String
    ) {

        viewModelScope.launch(Dispatchers.IO) {

            if (userEmail.isEmpty() || userPassword.isEmpty() || userConfirmPassword.isEmpty() || userName.isEmpty()) {
                _authUiState.update {
                    AuthUiState(
                        isLoading = false,
                        isSuccessful = false,
                        error = "Please fill in all the fields!"
                    )
                }
                return@launch
            }

            if (userPassword != userConfirmPassword) {
                _authUiState.update {
                    AuthUiState(
                        isLoading = false,
                        isSuccessful = false,
                        error = "Passwords do not match!"
                    )
                }
                return@launch
            }

            holaRepository.signUp(
                userEmail,
                userPassword,
                userName
            ).collectLatest { rezult ->

                when (rezult) {
                    is Rezults.Success -> {
                        _authUiState.update {
                            AuthUiState(
                                isLoading = false,
                                isSuccessful = true,
                                userId = rezult.data.id
                            )
                        }
                    }

                    is Rezults.Error -> {
                        _authUiState.update {
                            AuthUiState(
                                isLoading = false,
                                isSuccessful = false,
                                error = rezult.exception?.message
                            )
                        }
                    }
                }
            }
        }
    }
}