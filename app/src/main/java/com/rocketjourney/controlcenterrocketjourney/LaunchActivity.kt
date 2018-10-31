package com.rocketjourney.controlcenterrocketjourney

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.rocketjourney.controlcenterrocketjourney.login.FirstScreenActivity

class LaunchActivity : AppCompatActivity() {

    companion object {
        val DELAY_BEFORE_INIT = 3000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        Handler().postDelayed({

            intent = Intent(applicationContext, FirstScreenActivity::class.java)
            startActivity(intent)
            finish()

        }, DELAY_BEFORE_INIT.toLong())
    }
}
