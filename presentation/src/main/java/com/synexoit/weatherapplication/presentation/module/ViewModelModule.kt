package com.synexoit.weatherapplication.presentation.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.synexoit.weatherapplication.presentation.data.util.ViewModelFactory
import com.synexoit.weatherapplication.presentation.data.util.ViewModelKey
import com.synexoit.weatherapplication.presentation.viewmodel.city.CityViewModel
import com.synexoit.weatherapplication.presentation.viewmodel.main.MainViewModel
import com.synexoit.weatherapplication.presentation.viewmodel.search.SearchViewModel
import com.synexoit.weatherapplication.presentation.viewmodel.settings.SettingsViewModel
import com.synexoit.weatherapplication.presentation.viewmodel.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    internal abstract fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CityViewModel::class)
    internal abstract fun bindCityViewModel(viewModel: CityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    internal abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    internal abstract fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

}