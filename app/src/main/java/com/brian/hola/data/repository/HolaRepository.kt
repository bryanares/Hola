package com.brian.hola.data.repository

import com.brian.hola.data.model.User
import com.brian.hola.data.utils.Rezults
import kotlinx.coroutines.flow.Flow

interface HolaRepository {
    suspend fun login(userEmail: String, userPassword: String): Flow<Rezults<User>>

    suspend fun signUp(userEmail: String, userPassword: String, userName: String) : Flow<Rezults<User>>


}