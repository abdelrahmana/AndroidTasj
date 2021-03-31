package com.example.androidtask.util

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andrognito.flashbar.Flashbar
import com.andrognito.flashbar.anim.FlashAnim
import com.example.androidtask.R
import com.imperialcreation.remcoowner.utils.NetworkErrorDialog


import com.seven.util.GridModel

import okhttp3.ResponseBody
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


object UtilKotlin {

    fun ProgressDialog(): BlurDialog {
        val dialog =BlurDialog()
        return dialog
    }
    fun declarViewModel(activity: Fragment?): ViewModelHandleChangeFragmentclass? { // listen life cycle to fragment only
        return ViewModelProvider(activity!!).get(ViewModelHandleChangeFragmentclass::class.java)
    }
    fun declarViewModel(activity: FragmentActivity?): ViewModelHandleChangeFragmentclass? {
        return ViewModelProvider(activity!!).get(ViewModelHandleChangeFragmentclass::class.java)
    }
    fun showLoader(progressDialog: BlurDialog?,activity: FragmentActivity?) {
        if (progressDialog?.isAdded == false) {
            progressDialog?.show(activity?.supportFragmentManager!!, "")
            progressDialog?.showsDialog = true
        }
    }
    fun hideLoader(progressDialog : BlurDialog?) {
        progressDialog?.dismiss()
    }
    fun showNetworkIssueDialog(activity: FragmentActivity?,normalNetwork: Boolean = true) {
        val bundle = Bundle()
        NetworkErrorDialog().also { it.arguments=bundle }.show(activity?.supportFragmentManager?.beginTransaction()!!,"networkIssue")
    }

    fun setRecycleView(recyclerView: RecyclerView?, adaptor: RecyclerView.Adapter<*>,
                       verticalOrHorizontal: Int?, context:Context, gridModel: GridModel?, includeEdge : Boolean) {
        var layoutManger : RecyclerView.LayoutManager? = null
        if (gridModel==null) // normal linear
            layoutManger = LinearLayoutManager(context, verticalOrHorizontal!!,false)
        else
        {
            layoutManger = GridLayoutManager(context, gridModel.numberOfItems)
            layoutManger.isAutoMeasureEnabled = true
            if (recyclerView?.itemDecorationCount==0)
                recyclerView?.addItemDecoration(SpacesItemDecoration(gridModel.numberOfItems, gridModel.space, includeEdge))
        }
        recyclerView?.apply {
            setLayoutManager(layoutManger)
            setHasFixedSize(true)
            adapter = adaptor

        }
    }
    fun showSnackErrorInto(
        activity: Activity?,
        error: String?
    ) {
        if (activity != null) {
            Flashbar.Builder(activity)
                .gravity(Flashbar.Gravity.TOP)
                .title(activity.getString(R.string.errors))
                .message(error!!)
                .backgroundColorRes(R.color.red600)
                .dismissOnTapOutside()
                .duration(2500)
                .enableSwipeToDismiss()
                .enterAnimation(
                    FlashAnim.with(activity)
                        .animateBar()
                        .duration(550)
                        .alpha()
                        .overshoot()
                )
                .exitAnimation(
                    FlashAnim.with(activity)
                        .animateBar()
                        .duration(200)
                        .anticipateOvershoot()
                )
                .build().show()
        }
    }
    fun changeFragmentBack(activity: FragmentActivity, fragment: Fragment, tag: String ,bundle: Bundle?, id : Int ) {

        val transaction = activity?.supportFragmentManager.beginTransaction()
        if (bundle != null) {
            fragment.arguments = bundle
        }
        //R.id.frameLayout_direction+
        transaction.replace(id, fragment, tag)
        transaction.addToBackStack(tag)
        //    transaction.addToBackStack(null)
        transaction.commit()

    }
    @Suppress("DEPRECATION")
    fun isNetworkAvailable(context: Context): Boolean {
            var result = false
            val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cm?.run {
                    cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                        result = when {
                            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                            else -> false
                        }
                    }
                }
            } else {
                cm?.run {
                    cm.activeNetworkInfo?.run {
                        if (type == ConnectivityManager.TYPE_WIFI) {
                            result = true
                        } else if (type == ConnectivityManager.TYPE_MOBILE) {
                            result = true
                        }
                    }
                }
            }
            return result
        }


}