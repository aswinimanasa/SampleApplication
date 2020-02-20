package com.example.codingexercise


import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.codingexercise.api.DataModel
import com.example.codingexercise.repository.DataRepository
import com.example.codingexercise.viewmodel.DataViewModel
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.description
import org.mockito.MockitoAnnotations
import java.util.*


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

 class ExampleUnitTest {
    @Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dataRepository: DataRepository
    @Mock
    private lateinit var appContext: Application
    private lateinit var dataViewModel:DataViewModel

    val dataModel = DataModel()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`<Context>(appContext.applicationContext).thenReturn(appContext)

        dataViewModel = DataViewModel(dataRepository)
        insertData() // in order to test , we directly insert it

    }

    private fun insertData() {
    }
}

