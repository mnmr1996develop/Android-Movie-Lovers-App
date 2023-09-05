package com.michaelrichards.movieloversapp.di

import com.michaelrichards.movieloversapp.network.UserAPI
import com.michaelrichards.movieloversapp.repositories.implementaions.UserRepositoryImpl
import com.michaelrichards.movieloversapp.repositories.interfaces.UserRepository
import com.michaelrichards.movieloversapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideUserAPI(): UserAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.SITE_BASE_URL)
            .build()
            .create(UserAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: UserAPI): UserRepository = UserRepositoryImpl(api)
}