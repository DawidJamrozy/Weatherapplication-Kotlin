package com.synexoit.weatherapplication.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.synexoit.weatherapplication.ui.city.CityViewModel
import com.synexoit.weatherapplication.ui.main.MainViewModel
import com.synexoit.weatherapplication.ui.search.SearchViewModel
import com.synexoit.weatherapplication.ui.settings.SettingsViewModel
import com.synexoit.weatherapplication.ui.splash.SplashViewModel
import com.synexoit.weatherapplication.util.ViewModelFactory
import com.synexoit.weatherapplication.util.ViewModelKey
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