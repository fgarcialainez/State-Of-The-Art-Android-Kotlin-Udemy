package com.fgarcialainez.androidkotlincourse.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.fgarcialainez.androidkotlincourse.model.Animal
import com.fgarcialainez.androidkotlincourse.model.AnimalApiService
import com.fgarcialainez.androidkotlincourse.model.ApiKey
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel(application: Application): AndroidViewModel(application) {

    val animals by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    private val api = AnimalApiService()
    private val disposable = CompositeDisposable()

    fun refresh() {
        loading.value = true
        getApiKey()
    }

    private fun getApiKey() {
        disposable.add(
            api.getApiKey()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<ApiKey>() {
                    override fun onSuccess(apiKey: ApiKey) {
                        if (apiKey.key.isNullOrEmpty()) {
                            loading.value = false
                            loadError.value = true
                        }
                        else {
                            getAnimals(apiKey.key)
                        }
                    }

                    override fun onError(e: Throwable) {
                        loading.value = false
                        loadError.value = true
                    }
                })
        )
    }

    private fun getAnimals(key: String) {
        disposable.add(
            api.getAnimals(key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Animal>>() {
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