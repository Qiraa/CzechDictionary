package com.minger.czechdictionary.di

import androidx.room.Database
import androidx.room.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import com.minger.czechdictionary.data.ApiService
import com.minger.czechdictionary.data.MockWordsRepository
import com.minger.czechdictionary.data.WordsRepository
import com.minger.czechdictionary.db.DataBase
import com.minger.czechdictionary.domain.AddWordUseCase
import com.minger.czechdictionary.domain.AddWordUseCaseImpl
import com.minger.czechdictionary.domain.ClearWordsUseCase
import com.minger.czechdictionary.domain.ClearWordsUseCaseImpl
import com.minger.czechdictionary.domain.GetWordUseCase
import com.minger.czechdictionary.domain.GetWordUseCaseImpl
import com.minger.czechdictionary.domain.GetWordsUseCase
import com.minger.czechdictionary.domain.GetWordsUseCaseImpl
import com.minger.czechdictionary.domain.UpdateWordUseCase
import com.minger.czechdictionary.domain.UpdateWordWordUseCaseImpl
import com.minger.czechdictionary.presentation.favourite.FavouriteViewModel
import com.minger.czechdictionary.presentation.history.HistoryViewModel
import com.minger.czechdictionary.presentation.word.WordViewModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val appModule = module {
    single {
        Room.databaseBuilder<DataBase>(get(), "database")
            .setDriver(AndroidSQLiteDriver())
            .build()
    }
    single {
        MockWordsRepository(get(), get<DataBase>().wordsDao())
    } bind WordsRepository::class
    single {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
            .build()
    }
    single {
        val json = Json { ignoreUnknownKeys = true }
        Retrofit.Builder()
            .baseUrl("https://freedictionaryapi.com/api/v1/")
            .client(get())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
    single { get<Retrofit>().create(ApiService::class.java) }

    factory { AddWordUseCaseImpl(get()) } bind AddWordUseCase::class
    factory { GetWordsUseCaseImpl(get()) } bind GetWordsUseCase::class
    factory { ClearWordsUseCaseImpl(get()) } bind ClearWordsUseCase::class
    factory { UpdateWordWordUseCaseImpl(get()) } bind UpdateWordUseCase::class
    factory { GetWordUseCaseImpl(get()) } bind GetWordUseCase::class

    viewModel { FavouriteViewModel(get(), get(), get()) }
    viewModel {
        HistoryViewModel(
            get(),
            get(),
            get(),
            get()
        )
    }
    viewModel { WordViewModel( get(), get()) }
}