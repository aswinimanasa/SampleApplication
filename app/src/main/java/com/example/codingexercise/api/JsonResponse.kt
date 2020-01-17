package com.example.codingexercise.api


import com.google.gson.annotations.SerializedName


data class JsonResponse<out T : Any>(
        @SerializedName("title") val title: String = "",
        @SerializedName("rows") val rows: List<DataRowsList> = listOf()
)