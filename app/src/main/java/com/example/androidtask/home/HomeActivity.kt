package com.example.androidtask.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidtask.R
import com.example.androidtask.util.UtilKotlin
import com.seven.delivery29.normaluser.bottomnav.homeuser.allservices.FragmentCars

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        UtilKotlin.changeFragmentBack(this , FragmentCars() , "car"  , null, R.id.container)
    }
}