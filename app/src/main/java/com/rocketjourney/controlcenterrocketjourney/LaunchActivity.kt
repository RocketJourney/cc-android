package com.rocketjourney.controlcenterrocketjourney

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.rocketjourney.controlcenterrocketjourney.login.FirstScreenActivity
import io.branch.referral.Branch

class LaunchActivity : AppCompatActivity() {

    companion object {
        val DELAY_BEFORE_INIT = 3000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

//        Handler().postDelayed({
//
//            intent = Intent(applicationContext, FirstScreenActivity::class.java)
//            startActivity(intent)
//            finish()
//
//        }, DELAY_BEFORE_INIT.toLong())
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

                //ward validar estas validaciones
                intent = Intent(applicationContext, FirstScreenActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                //ward leer que es lo que viene aqui
                intent = Intent(applicationContext, FirstScreenActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, this.intent.data, this)
    }

    public override fun onNewIntent(intent: Intent) {
        this.intent = intent
    }
}
