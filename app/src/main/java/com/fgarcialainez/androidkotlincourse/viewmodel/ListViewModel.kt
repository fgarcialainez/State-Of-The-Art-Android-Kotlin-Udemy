package com.fgarcialainez.androidkotlincourse.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.fgarcialainez.androidkotlincourse.model.Animal

class ListViewModel(application: Application): AndroidViewModel(application) {

    val animals by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    fun refresh() {
        getAnimals()
    }

    private fun getAnimals() {
        val animal1 = Animal("alligator")
        val animal2 = Animal("bee")
        val animal3 = Animal("cat")
        val animal4 = Animal("dog")
        val animal5 = Animal("elephant")
        val animal6 = Animal("flamingo")

        val animalList = arrayListOf(animal1, animal2, animal3, animal4, animal5, animal6)

        animals.value = animalList
        loadError.value = false
        loading.value = false
    }
}