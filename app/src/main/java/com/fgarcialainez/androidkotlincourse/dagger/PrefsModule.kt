package com.fgarcialainez.androidkotlincourse.dagger

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.fgarcialainez.androidkotlincourse.utils.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

// Enable inject the same dependency using either
// an application context or an activity context
const val CONTEXT_APP = "Application Context"
const val CONTEXT_ACTIVITY= "Activity Context"

@Qualifier
annotation class TypeOfContext(val type: String)

@Module
class PrefsModule {

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_APP)
    fun provideSharedPreferences(app: Application): SharedPreferencesHelper {
        return SharedPreferencesHelper(app)
    }

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_ACTIVITY)
    fun provideSharedPreferencesActivity(activity: AppCompatActivity): SharedPreferencesHelper {
        return SharedPreferencesHelper(activity)
    }
}