package com.cimot.suitgame.ui.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.cimot.suitgame.R
import com.cimot.suitgame.ui.page.PageIntroActivity

class SplashScreenActivity : AppCompatActivity() {

    private var loading: CountDownTimer?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        setSplashScreen()
    }



    override fun onDestroy() {
        super.onDestroy()
        if (loading != null) {
            loading?.cancel()
            loading = null
        }
    }


    private fun setSplashScreen(){
        loading= object :CountDownTimer(2000,1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                val intent = Intent(this@SplashScreenActivity, PageIntroActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }
        loading?.start()
    }
}