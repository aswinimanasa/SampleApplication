package com.example.codingexercise


import android.os.AsyncTask
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.codingexercise.api.ApiInterface
import com.example.codingexercise.api.DataRowsList
import com.example.codingexercise.api.JsonResponse
import com.example.codingexercise.repository.DataRepository
import com.example.codingexercise.utils.Resource
import com.example.codingexercise.utils.Status
import com.example.codingexercise.viewmodel.DataViewModel
import io.reactivex.Single
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
public class ExampleUnitTest {
    @Rule
    val instantExecutorRule = InstantTaskExecutorRule()
}

