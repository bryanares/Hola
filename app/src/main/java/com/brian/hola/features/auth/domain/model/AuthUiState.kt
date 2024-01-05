package com.brian.hola.features.auth.domain.model

data class AuthUiState(
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false,
    val error: String? = null,
    val userId: String? = null
)