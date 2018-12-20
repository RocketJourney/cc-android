package com.rocketjourney.controlcenterrocketjourney

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.rocketjourney.controlcenterrocketjourney.home.HomeActivity
import com.rocketjourney.controlcenterrocketjourney.login.ChooseClubRegisterActivity
import com.rocketjourney.controlcenterrocketjourney.login.ConfirmEmailActivity
import com.rocketjourney.controlcenterrocketjourney.login.FirstScreenActivity
import com.rocketjourney.controlcenterrocketjourney.login.LoginActivity
import com.rocketjourney.controlcenterrocketjourney.login.interfaces.LoginInterface
import com.rocketjourney.controlcenterrocketjourney.structure.managers.SessionManager
import com.rocketjourney.controlcenterrocketjourney.structure.network.RJRetrofit
import com.rocketjourney.controlcenterrocketjourney.structure.network.utils.Utils
import io.branch.referral.Branch
import io.branch.referral.BranchError
import io.realm.ImportFlag
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_launch.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LaunchActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_INVITATION_CODE = "EXTRA_INVITATION_CODE"
        const val EXTRA_EMAIL = "EXTRA_EMAIL"

        val REQUEST_EMAIL_VALIDATION = 101
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

                    if (!jsonBranch.has("+clicked_branch_link")) {
                        launchWithSessionValidation()
                        return
                    }

                    val openWithDeepLink = jsonBranch.getBoolean("+clicked_branch_link")

                    if (openWithDeepLink) {

                        val comesFromPasswordReset = jsonBranch.getBoolean("login")

                        if (comesFromPasswordReset) {

                            SessionManager.closeSession(applicationContext)
                            val intent = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intent)
                            finish()

                        } else {

                            val invitationCode = jsonBranch.getString("invitation_code")
                            validateInvitationCode(invitationCode)

                        }

                    } else {

                        launchWithSessionValidation()

                    }


                } else {

                    launchWithSessionValidation()

                }
            }
        }, this.intent.data, this)
    }

    private fun launchWithSessionValidation() {

        val user = SessionManager.getCurrentSession()

        val intent: Intent

        if (user != null) {

            if (user.currentClub != null) {

                intent = Intent(applicationContext, HomeActivity::class.java)

            } else if (user.currentClub == null && user.clubs.size > 1) {

                intent = Intent(applicationContext, ChooseClubRegisterActivity::class.java)

            } else {

                val realm = Realm.getDefaultInstance()
                realm.executeTransaction {
                    user.currentClub = user.clubs.first()
                    it.insertOrUpdate(user)
                }

                intent = Intent(applicationContext, HomeActivity::class.java)

            }

        } else {

            intent = Intent(applicationContext, FirstScreenActivity::class.java)

        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
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
                        startActivityForResult(intent, REQUEST_EMAIL_VALIDATION)
                        finish()

                    }

                    404, 422 -> {

                        val expiredInvitation = AlertDialog.Builder(this@LaunchActivity, R.style.StyleAlertDialog)
                        expiredInvitation.setTitle(getString(R.string.link_expired))
                        expiredInvitation.setMessage(getString(R.string.ask_your_gym_for_another_link))
                        expiredInvitation.setPositiveButton(getString(R.string.ok), object : DialogInterface.OnClickListener {

                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                launchWithSessionValidation()
                            }

                        })
                        val alertDialogShown = expiredInvitation.show()

                        Utils.giveDesignToAlertDialog(alertDialogShown, applicationContext)

                    }

                }

            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                progressBar.visibility = View.INVISIBLE
                Utils.showShortToast(getString(R.string.no_network_connection))
                launchWithSessionValidation()
            }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == REQUEST_EMAIL_VALIDATION) {

            launchWithSessionValidation()

        }

    }
}
