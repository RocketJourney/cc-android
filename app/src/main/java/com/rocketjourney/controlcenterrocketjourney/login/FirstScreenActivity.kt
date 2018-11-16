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

    private val ACTIVITY_FOR_RESULT_LOGIN = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_screen)

        buttonLogin.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivityForResult(intent, ACTIVITY_FOR_RESULT_LOGIN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode != Activity.RESULT_OK) return

        if (requestCode == ACTIVITY_FOR_RESULT_LOGIN) {
            finish()
        }

    }
}
