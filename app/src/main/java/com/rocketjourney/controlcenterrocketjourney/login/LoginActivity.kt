package com.rocketjourney.controlcenterrocketjourney.login

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.Menu
import android.view.View
import android.widget.Button
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.R.drawable.ic_close_yellow
import com.rocketjourney.controlcenterrocketjourney.home.HomeActivity
import com.rocketjourney.controlcenterrocketjourney.login.interfaces.LoginInterface
import com.rocketjourney.controlcenterrocketjourney.login.requests.LoginRequest
import com.rocketjourney.controlcenterrocketjourney.login.responses.LoginResponse
import com.rocketjourney.controlcenterrocketjourney.structure.managers.SessionManager
import com.rocketjourney.controlcenterrocketjourney.structure.network.RJRetrofit
import com.rocketjourney.controlcenterrocketjourney.structure.network.utils.Utils
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.component_toolbar_title.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var buttonLogin: Button

    override fun onClick(v: View?) {

        if (v == buttonLogin) {

            cleanViews()
            doLogin()

        } else if (v == textViewForgotPassword) {

            launchResetPassword()

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setSupportActionBar(componentToolbar.toolbar)
        Utils.hideToolbarTitle(supportActionBar)

        componentToolbar.toolbar.textViewToolbarTitle.text = getString(R.string.login)
        componentToolbar.toolbar.navigationIcon = getDrawable(ic_close_yellow)

        componentToolbar.toolbar.setNavigationOnClickListener {
            finish()
        }

        cleanViews()

        textViewForgotPassword.setOnClickListener(this)

    }

    private fun cleanViews() {
        textInputLayoutEmail.error = ""
        textInputLayoutPassword.error = ""
    }

    private fun doLogin() {

        val email = editTextEmail.text.toString()
        val password = editTextClubPassword.text.toString()

        if (!Utils.isValidEmail(email)) {
            textInputLayoutEmail.error = getString(R.string.invalid_email)
            return
        }

        if (password.length < 6) {
            textInputLayoutPassword.error = getString(R.string.password_should_be_6_characters)
            return
        }

        val request = LoginRequest(email, password)

        buttonLogin.isEnabled = false
        progressBar.visibility = View.VISIBLE

        Utils.hideKeyboard(this@LoginActivity)

        RJRetrofit.getInstance().create(LoginInterface::class.java).loginRequest(request).enqueue(object : Callback<LoginResponse> {

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                buttonLogin.isEnabled = true
                progressBar.visibility = View.GONE

                when (response.code()) {

                    201 -> {

                        val user = SessionManager.createSession(email, response.body()!!.data)

                        if (user.clubs.size > 1) {

                            val intent = Intent(applicationContext, ChooseClubRegisterActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)

                        } else {

                            Realm.getDefaultInstance().executeTransaction {
                                user.currentClub = user.clubs[0]
                                it.insertOrUpdate(user)
                            }

                            val intent = Intent(applicationContext, HomeActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)

                        }

                        finish()

                    }

                    404 -> {

                        val emailNotRegisteredDialog = AlertDialog.Builder(this@LoginActivity, R.style.StyleAlertDialog)
                        emailNotRegisteredDialog.setTitle(getString(R.string.email_not_registered, email))
                        emailNotRegisteredDialog.setMessage(getString(R.string.try_again_or_sign_up))
                        emailNotRegisteredDialog.setPositiveButton(getString(R.string.ok), null)

                        val alertDialogShown = emailNotRegisteredDialog.show()

                        Utils.giveDesignToAlertDialog(alertDialogShown, applicationContext)

                    }

                    401 -> {

                        val invalidCredentialsDialog = AlertDialog.Builder(this@LoginActivity, R.style.StyleAlertDialog)
                        invalidCredentialsDialog.setTitle(getString(R.string.email_and_password_dont_match))
                        invalidCredentialsDialog.setMessage(getString(R.string.please_try_again))
                        invalidCredentialsDialog.setPositiveButton(getString(R.string.try_again), object : DialogInterface.OnClickListener {

                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                //TODO
                                //ward
                            }

                        })

                        val alertDialogShown = invalidCredentialsDialog.show()

                        Utils.giveDesignToAlertDialog(alertDialogShown, applicationContext)

                    }

                }

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                buttonLogin.isEnabled = true
                progressBar.visibility = View.GONE
                Utils.showShortToast(getString(R.string.no_network_connection))
            }

        })
    }

    private fun launchResetPassword() {

        val intent = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intent)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_login, menu)
        var menuItem = menu?.findItem(R.id.menuLogin)

        buttonLogin = menuItem?.actionView as Button

        buttonLogin.text = getString(R.string.enter)
        buttonLogin.setTextColor(getColor(R.color.yellow_ff))
        buttonLogin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        buttonLogin.typeface = Utils.montserratBold()
        buttonLogin.setBackgroundColor(Color.TRANSPARENT)
        buttonLogin.setOnClickListener(this)

        return true
    }
}
