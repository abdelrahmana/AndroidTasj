package com.example.androidtask.home.model

data class ResponseCarList(
    var `data`: ArrayList<Data>?,
    var status: Int?
)

data class Data(
    var brand: String?,
    var constractionYear: String?,
    var id: Int?,
    var imageUrl: String?,
    var isUsed: Boolean?
)