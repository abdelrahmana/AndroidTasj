package com.example.androidtask.home

import com.example.androidtask.home.model.ResponseCarList
import com.seven.util.ApiConfiguration.WebService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

object HomePresenter {
    fun getCarListCall(
        mServiceUser : WebService,
        page : Int,
        responseDisposableObserver: DisposableObserver<Response<ResponseCarList>>
    ) {
        mServiceUser.getCarList(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(responseDisposableObserver)
    }
}