package com.minger.czechdictionary.di

import com.minger.czechdictionary.data.MockWordsRepository
import com.minger.czechdictionary.data.WordsRepository
import com.minger.czechdictionary.domain.AddWordUseCase
import com.minger.czechdictionary.domain.AddWordUseCaseImpl
import com.minger.czechdictionary.domain.ClearWordsUseCase
import com.minger.czechdictionary.domain.ClearWordsUseCaseImpl
import com.minger.czechdictionary.domain.GetWordsUseCase
import com.minger.czechdictionary.domain.GetWordsUseCaseImpl
import com.minger.czechdictionary.domain.UpdateUseCase
import com.minger.czechdictionary.domain.UpdateWordUseCaseImpl
import com.minger.czechdictionary.presentation.favourite.FavouriteViewModel
import com.minger.czechdictionary.presentation.history.HistoryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { MockWordsRepository() } bind WordsRepository::class

    factory { AddWordUseCaseImpl(get()) } bind AddWordUseCase::class
    factory { GetWordsUseCaseImpl(get()) } bind GetWordsUseCase::class
    factory { ClearWordsUseCaseImpl(get()) } bind ClearWordsUseCase::class
    factory { UpdateWordUseCaseImpl(get()) } bind UpdateUseCase::class

    viewModel { FavouriteViewModel() }
    viewModel {
        HistoryViewModel(
            get(),
            get(),
            get(),
            get()
        )
    }
}