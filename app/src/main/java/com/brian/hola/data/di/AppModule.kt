package com.brian.hola.data.di

import com.brian.hola.data.repository.HolaRepository
import com.brian.hola.data.repository.HolaRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideHolaRepository(
        firebaseAuth: FirebaseAuth
    ) : HolaRepository = HolaRepositoryImpl(firebaseAuth)

}