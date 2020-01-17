package com.example.codingexercise.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject


class DataViewModelFactory @Inject constructor(
    private val dataViewModel: DataViewModel) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(DataViewModel::class.java)) {
      return dataViewModel as T
    }
    throw IllegalArgumentException("Unknown class name")
  }
}