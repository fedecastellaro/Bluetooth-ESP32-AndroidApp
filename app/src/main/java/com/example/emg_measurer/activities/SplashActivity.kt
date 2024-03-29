package com.example.emg_measurer.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.emg_measurer.R

class SplashActivity : AppCompatActivity() {
    private val tiempo:Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            overridePendingTransition(R.transition.slide_from_right,R.transition.slide_to_left)
            finish()
        }, tiempo)

    }
}