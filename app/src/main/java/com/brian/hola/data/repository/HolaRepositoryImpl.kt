package com.brian.hola.data.repository

import com.brian.hola.data.model.User
import com.brian.hola.data.utils.Rezults
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class HolaRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : HolaRepository {
    override suspend fun login(userEmail: String, userPassword: String): Flow<Rezults<User>> {

        return callbackFlow {
            firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        this.trySend(
                            Rezults.Success(
                                User(
                                    firebaseAuth.currentUser!!.uid
                                )
                            )
                        ).isSuccess
                    } else {
                        this.trySend(
                            Rezults.Error(
                                "",
                                task.exception!!
                            )
                        ).isSuccess
                    }
                }
            awaitClose { this.cancel() }
        }
    }


    override suspend fun signUp(
        userEmail: String,
        userPassword: String,
        userName: String
    ): Flow<Rezults<User>> = callbackFlow {
        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    this.trySend(
                        Rezults.Success(
                            User(
                                firebaseAuth.currentUser!!.uid
                            )
                        )
                    ).isSuccess
                } else {
                    this.trySend(
                        Rezults.Error(
                            "",
                            task.exception!!
                        )
                    ).isSuccess
                }
            }
        awaitClose { this.cancel() }
    }
}