package com.rocketjourney.controlcenterrocketjourney.home

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.login.FirstScreenActivity
import com.rocketjourney.controlcenterrocketjourney.structure.network.utils.Utils
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        buttonCerrarSesion.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Utils.saveBooleanToPrefs(applicationContext, Utils.SHARED_PREFERENCES_HAS_SESSION, false)

                val intent = Intent(applicationContext, FirstScreenActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }
}
