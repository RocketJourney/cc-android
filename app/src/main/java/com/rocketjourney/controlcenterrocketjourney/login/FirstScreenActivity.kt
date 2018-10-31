package com.rocketjourney.controlcenterrocketjourney.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.rocketjourney.controlcenterrocketjourney.R
import kotlinx.android.synthetic.main.activity_first_screen.*

class FirstScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_screen)

        buttonLogin.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
//            intent.putExtra(LoginActivity.LOGIN_EXTRA, true)
            startActivity(intent)
        }
    }
}
