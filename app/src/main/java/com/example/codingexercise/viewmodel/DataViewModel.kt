package com.example.codingexercise.viewmodel



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
  private lateinit var disposableObserver: DisposableObserver<Call<String>>

  val dataResult = Transformations.switchMap(fetch) {
    dataRepository.dataList()
  }
  fun disposeElements() {
    if (!disposableObserver.isDisposed) disposableObserver.dispose()
  }

}




