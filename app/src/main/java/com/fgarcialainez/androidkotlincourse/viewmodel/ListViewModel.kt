package com.fgarcialainez.androidkotlincourse.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.fgarcialainez.androidkotlincourse.dagger.*
import com.fgarcialainez.androidkotlincourse.model.Animal
import com.fgarcialainez.androidkotlincourse.model.AnimalApi
import com.fgarcialainez.androidkotlincourse.model.AnimalApiService
import com.fgarcialainez.androidkotlincourse.model.ApiKey
import com.fgarcialainez.androidkotlincourse.utils.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel(application: Application) : AndroidViewModel(application) {

    val animals by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    private var invalidApiKey = false
    private val disposable = CompositeDisposable()

    @Inject
    lateinit var apiService: AnimalApiService

    @Inject
    @field:TypeOfContext(CONTEXT_APP)
    lateinit var preferences: SharedPreferencesHelper

    init {
        // Inject a new instance of the AnimalApiService
        DaggerViewModelComponent.builder()
            .appModule(AppModule(getApplication()))
            .build()
            .inject(this)
    }

    fun refresh() {
        // Reset the flag
        invalidApiKey = false

        // Show progress view
        loading.value = true

        // Get the stored key
        val key = preferences.getApiKey()

        if (key.isNullOrEmpty()) {
            // Get the key
            getApiKey()
        } else {
            // Get animals using the stored key
            getAnimals(key)
        }

        getApiKey()
    }

    fun hardRefresh() {
        loading.value = true

        // Get the key
        getApiKey()
    }

    private fun getApiKey() {
        disposable.add(
            apiService.getApiKey()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiKey>() {
                    override fun onSuccess(apiKey: ApiKey) {
                        if (apiKey.key.isNullOrEmpty()) {
                            loading.value = false
                            loadError.value = true
                        } else {
                            // Store retrieved key
                            preferences.saveApiKey(apiKey.key)

                            // Get animals
                            getAnimals(apiKey.key)
                        }
                    }

                    override fun onError(e: Throwable) {
                        if (!invalidApiKey) {
                            invalidApiKey = true

                            // Get the key
                            getApiKey()
                        } else {
                            animals.value = null
                            loading.value = false
                            loadError.value = true
                        }
                    }
                })
        )
    }

    private fun getAnimals(key: String) {
        disposable.add(
            apiService.getAnimals(key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Animal>>() {
                    override fun onSuccess(list: List<Animal>) {
                        animals.value = list
                        loading.value = false
                        loadError.value = false
                    }

                    override fun onError(e: Throwable) {
                        animals.value = null
                        loading.value = false
                        loadError.value = true
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }
}