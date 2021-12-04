package com.example.mysubmissionbfaagithubuserapps.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.mysubmissionbfaagithubuserapps.R
import com.example.mysubmissionbfaagithubuserapps.ui.home.MainActivity
import com.example.mysubmissionbfaagithubuserapps.utils.Constans

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashScreen, MainActivity::class.java))
            finish()
        }, Constans.TIMES.toLong())
    }
}