package com.minger.czechdictionary.di

import com.minger.czechdictionary.presentation.favourite.FavouriteViewModel
import com.minger.czechdictionary.presentation.history.HistoryViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { FavouriteViewModel() }
    viewModel { HistoryViewModel() }
}