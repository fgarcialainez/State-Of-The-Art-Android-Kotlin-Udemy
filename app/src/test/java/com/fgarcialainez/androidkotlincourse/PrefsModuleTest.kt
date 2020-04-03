package com.fgarcialainez.androidkotlincourse

import android.app.Application
import com.fgarcialainez.androidkotlincourse.dagger.PrefsModule
import com.fgarcialainez.androidkotlincourse.utils.SharedPreferencesHelper

class PrefsModuleTest(val mockPrefs: SharedPreferencesHelper): PrefsModule() {
    override fun provideSharedPreferences(app: Application): SharedPreferencesHelper {
        return mockPrefs
    }
}