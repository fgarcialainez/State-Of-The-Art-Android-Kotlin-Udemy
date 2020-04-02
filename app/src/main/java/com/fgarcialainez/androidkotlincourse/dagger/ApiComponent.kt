package com.fgarcialainez.androidkotlincourse.dagger

import com.fgarcialainez.androidkotlincourse.model.AnimalApiService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: AnimalApiService)
}