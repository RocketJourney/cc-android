package com.rocketjourney.controlcenterrocketjourney.login

import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.support.v4.os.ConfigurationCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.login.interfaces.LoginInterface
import com.rocketjourney.controlcenterrocketjourney.login.requests.ResetPasswordRequest
import com.rocketjourney.controlcenterrocketjourney.structure.network.RJRetrofit
import com.rocketjourney.controlcenterrocketjourney.structure.network.utils.Utils
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.component_toolbar_title.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {

        if (v == buttonResetPassword) {
            sendPasswordReset()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        setSupportActionBar(componentToolbar.toolbar)
        Utils.hideToolbarTitle(supportActionBar)

        componentToolbar.textViewToolbarTitle.text = getString(R.string.recovery_password)

        componentToolbar.toolbar.navigationIcon = getDrawable(R.drawable.ic_close_yellow)
        componentToolbar.toolbar.setNavigationOnClickListener { finish() }

        buttonResetPassword.setOnClickListener(this)

        cleanViews()
    }

    private fun cleanViews() {
        textInputLayoutEmail.error = ""
    }

    private fun sendPasswordReset() {

        cleanViews()

        val email = editTextEmail.text.toString()

        if (!Utils.isValidEmail(email)) {
            textInputLayoutEmail.error = getString(R.string.invalid_email)
            return
        }

        val request = ResetPasswordRequest()
        request.email = email

        buttonResetPassword.isEnabled = false

        //ward
        val locale = ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0)
        val language = locale.language

        RJRetrofit.getInstance().create(LoginInterface::class.java).resetPasswordRequest(request, language).enqueue(object : Callback<Void> {

            override fun onResponse(call: Call<Void>, response: Response<Void>) {

                buttonResetPassword.isEnabled = true

                when (response.code()) {

                    201 -> {

                        val emailNotRegisteredDialog = AlertDialog.Builder(this@ForgotPasswordActivity, R.style.StyleAlertDialog)
                        emailNotRegisteredDialog.setTitle(getString(R.string.email_sent))
                        emailNotRegisteredDialog.setMessage(getString(R.string.check_your_email_and_follow_instructions))
                        emailNotRegisteredDialog.setPositiveButton(getString(R.string.ok), object : DialogInterface.OnClickListener {

                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                finish()
                            }

                        })
                        emailNotRegisteredDialog.show()

                    }

                    404 -> {

                        val emailNotRegisteredDialog = AlertDialog.Builder(this@ForgotPasswordActivity, R.style.StyleAlertDialog)
                        emailNotRegisteredDialog.setTitle(getString(R.string.email_not_registered, email))
                        emailNotRegisteredDialog.setMessage(getString(R.string.try_again_or_sign_up))
                        emailNotRegisteredDialog.setPositiveButton(getString(R.string.ok), null)
                        emailNotRegisteredDialog.show()

                    }

                }

            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                buttonResetPassword.isEnabled = true
                //ward
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}
