package com.michaelrichards.movieloversapp.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.Gson
import com.michaelrichards.movieloversapp.network.AuthAPI
import com.michaelrichards.movieloversapp.network.MovieAPI
import com.michaelrichards.movieloversapp.network.MovieReviewAPI
import com.michaelrichards.movieloversapp.network.UserDataAPI
import com.michaelrichards.movieloversapp.repositories.implementaions.AuthRepositoryImpl
import com.michaelrichards.movieloversapp.repositories.implementaions.MovieRepositoryImpl
import com.michaelrichards.movieloversapp.repositories.implementaions.UserMovieReviewsRepositoryImpl
import com.michaelrichards.movieloversapp.repositories.implementaions.UserRepositoryImpl
import com.michaelrichards.movieloversapp.repositories.interfaces.AuthRepository
import com.michaelrichards.movieloversapp.repositories.interfaces.MovieRepository
import com.michaelrichards.movieloversapp.repositories.interfaces.UserMovieReviewsRepository
import com.michaelrichards.movieloversapp.repositories.interfaces.UserRepository
import com.michaelrichards.movieloversapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideUserAuthAPI(): AuthAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.SITE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(AuthAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieAPI(): MovieAPI{
        return Retrofit.Builder()
            .baseUrl(Constants.MOVIE_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(MovieAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieReviewAPI(): MovieReviewAPI{
        return Retrofit.Builder()
            .baseUrl(Constants.SITE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(MovieReviewAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideUserDataAPI(): UserDataAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.SITE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(UserDataAPI::class.java)
    }


    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences = app.getSharedPreferences("prefs", MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthAPI, prefs: SharedPreferences): AuthRepository = AuthRepositoryImpl(api, prefs)

    @Provides
    @Singleton
    fun provideUserRepository(api: UserDataAPI, prefs: SharedPreferences): UserRepository = UserRepositoryImpl(api, prefs)

    @Provides
    @Singleton
    fun provideMovieRepository(api: MovieAPI): MovieRepository = MovieRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideMovieReviewRepository(api: MovieReviewAPI, prefs: SharedPreferences): UserMovieReviewsRepository = UserMovieReviewsRepositoryImpl(api, prefs)


}