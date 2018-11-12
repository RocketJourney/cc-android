package com.rocketjourney.controlcenterrocketjourney.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.rocketjourney.controlcenterrocketjourney.R
import io.branch.referral.Branch
import kotlinx.android.synthetic.main.activity_first_screen.*


class FirstScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_screen)

        buttonLogin.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            //ward
//            intent.putExtra(LoginActivity.LOGIN_EXTRA, true)
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()
        val branch = Branch.getInstance()

        // Branch init
        branch.initSession({ referringParams, error ->
            if (error == null) {
                // params are the deep linked params associated with the link that the user clicked -> was re-directed to this app
                // params will be empty if no data found
                // ... insert custom logic here ...
                Log.i("BRANCH SDK", referringParams.toString())
            } else {
                Log.i("BRANCH SDK", error.message)
            }
        }, this.intent.data, this)
    }

    public override fun onNewIntent(intent: Intent) {
        this.intent = intent
    }

    fun confirmEmail(view: View) {
        val intent = Intent(this, ConfirmEmailActivity::class.java)
        startActivity(intent)
    }

    fun createAccount(view: View) {
        val intent = Intent(this, CreateAccountActivity::class.java)
        startActivity(intent)
    }
}
