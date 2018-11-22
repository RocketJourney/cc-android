package com.rocketjourney.controlcenterrocketjourney.home

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.login.FirstScreenActivity
import com.rocketjourney.controlcenterrocketjourney.structure.managers.SessionManager
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val user = SessionManager.getCurrentSession()
        textViewCurrentClub.text = user?.currentClub?.name

        buttonCerrarSesion.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                SessionManager.closeSession()

                val intent = Intent(applicationContext, FirstScreenActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        })
    }
}
