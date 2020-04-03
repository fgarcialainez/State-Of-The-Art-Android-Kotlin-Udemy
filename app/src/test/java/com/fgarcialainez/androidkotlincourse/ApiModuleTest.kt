package com.fgarcialainez.androidkotlincourse

import com.fgarcialainez.androidkotlincourse.dagger.ApiModule
import com.fgarcialainez.androidkotlincourse.model.AnimalApiService

class ApiModuleTest(val mockService: AnimalApiService): ApiModule () {
    override fun provideAnimalApiService(): AnimalApiService {
        return mockService
    }
}