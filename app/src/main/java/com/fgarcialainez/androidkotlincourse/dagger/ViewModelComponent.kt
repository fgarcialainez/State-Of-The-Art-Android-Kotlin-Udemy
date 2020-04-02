package com.fgarcialainez.androidkotlincourse.dagger

import com.fgarcialainez.androidkotlincourse.viewmodel.ListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, PrefsModule::class, AppModule::class])
interface ViewModelComponent {

    fun inject(viewModel: ListViewModel)
}