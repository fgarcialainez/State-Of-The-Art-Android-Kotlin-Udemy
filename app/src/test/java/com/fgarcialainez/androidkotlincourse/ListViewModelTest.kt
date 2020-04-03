package com.fgarcialainez.androidkotlincourse

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fgarcialainez.androidkotlincourse.dagger.AppModule
import com.fgarcialainez.androidkotlincourse.dagger.DaggerViewModelComponent
import com.fgarcialainez.androidkotlincourse.model.Animal
import com.fgarcialainez.androidkotlincourse.model.AnimalApiService
import com.fgarcialainez.androidkotlincourse.model.ApiKey
import com.fgarcialainez.androidkotlincourse.utils.SharedPreferencesHelper
import com.fgarcialainez.androidkotlincourse.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor

class ListViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var prefs: SharedPreferencesHelper

    @Mock
    lateinit var animalService: AnimalApiService

    val application = Mockito.mock(Application::class.java)
    val listViewModel = ListViewModel(application, true)

    private val key = "Test Key"

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        DaggerViewModelComponent.builder()
            .appModule(AppModule(application))
            .apiModule(ApiModuleTest(animalService))
            .prefsModule(PrefsModuleTest(prefs))
            .build()
            .inject(listViewModel)
    }

    @Before
    fun setupRxSchedulers() {
        // Run before any test is executed
        val immediate = object: Scheduler() {
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, true)
            }
        }

        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
    }

    @Test
    fun getAnimalSuccess() {
        // Mock the call to method getApiKey of the class SharedPreferencesHelper
        Mockito.`when`(prefs.getApiKey()).thenReturn(key)

        val animal = Animal("Cow", null, null, null, null, null, null)
        val animalList = listOf(animal)

        val testSingle = Single.just(animalList)

        // Mock the call to method getAnimals of the class AnimalApiService
        Mockito.`when`(animalService.getAnimals(key)).thenReturn(testSingle)

        // Call refresh method
        listViewModel.refresh()

        // Check results
        Assert.assertEquals(1, listViewModel.animals.value?.size)
        Assert.assertEquals(false, listViewModel.loadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    @Test
    fun getAnimalFailure() {
        // Mock the call to method getApiKey of the class SharedPreferencesHelper
        Mockito.`when`(prefs.getApiKey()).thenReturn(key)

        val testSingle = Single.error<List<Animal>>(Throwable())
        val keySingle = Single.just(ApiKey("OK", key))

        // Mock the call to method getAnimals of the class AnimalApiService
        Mockito.`when`(animalService.getAnimals(key)).thenReturn(testSingle)

        // Mock the call to method getApiKey of the class AnimalApiService
        Mockito.`when`(animalService.getApiKey()).thenReturn(keySingle)

        // Call refresh method
        listViewModel.refresh()

        // Check results
        Assert.assertEquals(null, listViewModel.animals.value)
        Assert.assertEquals(true, listViewModel.loadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    @Test
    fun getApiKeyFailure() {
        // Mock the call to method getApiKey of the class SharedPreferencesHelper
        Mockito.`when`(prefs.getApiKey()).thenReturn(null)

        // Mock the call to method getApiKey of the class AnimalApiService
        val keySingle = Single.error<ApiKey>(Throwable())

        Mockito.`when`(animalService.getApiKey()).thenReturn(keySingle)

        // Call refresh method
        listViewModel.refresh()

        // Check results
        Assert.assertEquals(null, listViewModel.animals.value)
        Assert.assertEquals(true, listViewModel.loadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }
}