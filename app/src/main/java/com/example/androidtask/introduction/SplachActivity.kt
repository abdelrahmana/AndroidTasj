package com.example.androidtask.introduction

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.bumptech.glide.Glide
import com.example.androidtask.home.HomeActivity
import com.example.androidtask.R
import kotlinx.android.synthetic.main.splach_layout.*

class SplachActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splach_layout)
       // FirebaseApp.initializeApp(this)
     //   FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true) // set crashtylics
       // setAnimation()
        Glide.with(this).load("https://i.imgur.com/FG2eSjW.jpg").error(R.drawable.car).into(splach_logo)

        watiMeAndStartActivity()


    }

    private fun watiMeAndStartActivity() {
        Handler().postDelayed({
            //  startActivity(Intent(this,MyMainActivity::class.java))
            val bundle = ActivityOptionsCompat.makeCustomAnimation(this@SplachActivity,
                android.R.anim.fade_in, android.R.anim.fade_out).toBundle()
            startActivity(Intent(this@SplachActivity, HomeActivity::class.java), bundle) // go to home please


            finish()


        }, 3000)
    }

}
