package com.rocketjourney.controlcenterrocketjourney

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.rocketjourney.controlcenterrocketjourney.login.ConfirmEmailActivity
import com.rocketjourney.controlcenterrocketjourney.login.FirstScreenActivity
import com.rocketjourney.controlcenterrocketjourney.login.LoginActivity
import com.rocketjourney.controlcenterrocketjourney.login.interfaces.LoginInterface
import com.rocketjourney.controlcenterrocketjourney.structure.network.RJRetrofit
import io.branch.referral.Branch
import io.branch.referral.BranchError
import kotlinx.android.synthetic.main.activity_launch.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LaunchActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_INVITATION_CODE = "EXTRA_INVITATION_CODE"
        const val EXTRA_EMAIL = "EXTRA_EMAIL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
    }

    override fun onStart() {
        super.onStart()

        // Branch init
        Branch.getInstance().initSession(object : Branch.BranchReferralInitListener {
            override fun onInitFinished(referringParams: JSONObject, error: BranchError?) {
                if (error == null) {

                    val jsonBranch = JSONObject(referringParams.toString())
                    val openWithDeepLink = jsonBranch.getBoolean("+clicked_branch_link")

                    if (openWithDeepLink) {

                        val comesFromPasswordReset = jsonBranch.getBoolean("login")

                        if (comesFromPasswordReset) {

                            //ward remember to kill the current session
                            val intent = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intent)
                            finish()

                        } else {

                            val invitationCode = jsonBranch.getString("invitation_code")
                            validateInvitationCode(invitationCode)

                        }

                    } else {

                        val intent = Intent(applicationContext, FirstScreenActivity::class.java)
                        startActivity(intent)
                        finish()

                    }


                } else {

                    //ward checar si ya hay una sesion iniciada
                    val intent = Intent(applicationContext, FirstScreenActivity::class.java)
                    startActivity(intent)
                    finish()

                }
            }
        }, this.intent.data, this)
    }

    public override fun onNewIntent(intent: Intent) {
        this.intent = intent
    }

    private fun validateInvitationCode(code: String) {

        progressBar.visibility = View.VISIBLE

        RJRetrofit.getInstance().create(LoginInterface::class.java).validateInvite(code).enqueue(object : Callback<Void> {

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                progressBar.visibility = View.INVISIBLE

                when (response.code()) {

                    200 -> {

                        val intent = Intent(applicationContext, ConfirmEmailActivity::class.java)
                        intent.putExtra(EXTRA_INVITATION_CODE, code)
                        startActivity(intent)
                        finish()

                    }

                    404, 422 -> {

                        val expiredInvitation = AlertDialog.Builder(this@LaunchActivity, R.style.StyleAlertDialog)
                        expiredInvitation.setTitle(getString(R.string.link_expired))
                        expiredInvitation.setMessage(getString(R.string.ask_your_gym_for_another_link))
                        expiredInvitation.setPositiveButton(getString(R.string.ok), object : DialogInterface.OnClickListener {

                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                val intent = Intent(applicationContext, FirstScreenActivity::class.java)
                                startActivity(intent)
                                finish()
                            }

                        })
                        expiredInvitation.show()

                    }

                }

            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                progressBar.visibility = View.INVISIBLE
                //ward
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

    }
}
