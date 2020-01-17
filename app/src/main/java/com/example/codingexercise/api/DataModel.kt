package com.example.codingexercise.api


import com.google.gson.annotations.SerializedName


data class DataModel(

        @SerializedName("title")
    var title: String = "",

        @SerializedName( "rows")
    var rows: List<DataRowsList> = listOf()


) : ApiData

data class DataRowsList(

        @SerializedName( "title")
        var title: String = "",

        @SerializedName("description")
        var description: String = "",

        @SerializedName( "imageHref")
        var imageHref: String = ""


) : ApiData