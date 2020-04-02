package com.fgarcialainez.androidkotlincourse.dagger

import android.app.Application
import com.fgarcialainez.androidkotlincourse.utils.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PrefsModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferencesHelper {
        return SharedPreferencesHelper(app)
    }
}