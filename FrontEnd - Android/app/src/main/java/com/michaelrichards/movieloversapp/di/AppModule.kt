package com.michaelrichards.movieloversapp.di

import com.google.gson.Gson
import com.michaelrichards.movieloversapp.network.UserAPI
import com.michaelrichards.movieloversapp.repositories.implementaions.UserRepositoryImpl
import com.michaelrichards.movieloversapp.repositories.interfaces.UserRepository
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
    fun provideUserAPI(): UserAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.SITE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(UserAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: UserAPI): UserRepository = UserRepositoryImpl(api)
}