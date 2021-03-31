package com.example.androidtask.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.ResponseBody

// this viewpager is used within fragment
// specifically when running new fragment and notifying fragment with this change
class ViewModelHandleChangeFragmentclass  : ViewModel() {
    var responseDataCode = MutableLiveData<Any>() // lets make this generic to use it with all apis
    var notifyItemSelected = MutableLiveData<Any>() // lets make this for sharing data overall application


    fun setNotifyItemSelected(responseBody : Any?) { // lets post this to our listener places

        this.notifyItemSelected.value =responseBody
    }

    // if it false then child requests calls from api

    fun responseCodeDataSetter(responseBody : Any?) { // lets post this to our listener places

        this.responseDataCode.postValue(responseBody)
    }



}