package com.michaelrichards.movieloversapp.di

import com.google.gson.Gson
import com.michaelrichards.movieloversapp.network.AuthAPI
import com.michaelrichards.movieloversapp.repositories.implementaions.AuthRepositoryImpl
import com.michaelrichards.movieloversapp.repositories.interfaces.AuthRepository
import com.michaelrichards.movieloversapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideUserAPI(): AuthAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.SITE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(AuthAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthAPI): AuthRepository = AuthRepositoryImpl(api)
}