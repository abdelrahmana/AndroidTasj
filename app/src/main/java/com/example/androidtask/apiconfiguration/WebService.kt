package com.seven.util.ApiConfiguration

import com.example.androidtask.home.model.ResponseCarList
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface WebService {
  //  @Headers("Accept: application/json")
    @GET("v1/cars")
    fun getCarList(@Query("page") page: Int): Observable<Response<ResponseCarList>>

}