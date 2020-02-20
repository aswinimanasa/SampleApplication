package com.example.codingexercise.viewmodel


import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.codingexercise.repository.DataRepository
import io.reactivex.observers.DisposableObserver
import retrofit2.Call
import javax.inject.Inject


class DataViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {
    var fetch: MutableLiveData<Boolean> = MutableLiveData()

    val dataResult = Transformations.switchMap(fetch) {
        dataRepository.dataList()
    }



}




