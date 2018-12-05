package com.rocketjourney.controlcenterrocketjourney.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.rocketjourney.controlcenterrocketjourney.LaunchActivity
import com.rocketjourney.controlcenterrocketjourney.R
import io.branch.referral.Branch
import kotlinx.android.synthetic.main.activity_first_screen.*


class FirstScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_screen)

        buttonLogin.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
