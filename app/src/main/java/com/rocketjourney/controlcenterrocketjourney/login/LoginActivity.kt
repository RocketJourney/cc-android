package com.rocketjourney.controlcenterrocketjourney.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.Menu
import android.view.View
import android.widget.Button
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.R.drawable.ic_close_yellow
import com.rocketjourney.controlcenterrocketjourney.login.interfaces.LoginInterface
import com.rocketjourney.controlcenterrocketjourney.login.requests.LoginRequest
import com.rocketjourney.controlcenterrocketjourney.login.responses.LoginResponse
import com.rocketjourney.controlcenterrocketjourney.structure.network.RJRetrofit
import com.rocketjourney.controlcenterrocketjourney.structure.network.utils.Utils
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.component_toolbar_title.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        val LOGIN_EXTRA = "LOGIN_EXTRA"
    }

    lateinit var buttonLogin: Button
    private var isLogin = false

    override fun onClick(v: View?) {

        if (v == buttonLogin) {

            cleanViews()

            if (isLogin)
                doLogin()
            else
                doSignUp()

        } else if (v == buttonForgotPassword) {

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

        if (intent.hasExtra(LOGIN_EXTRA)) {
            isLogin = intent.getBooleanExtra(LOGIN_EXTRA, true)
        }

        if (isLogin) {
            textInputLayoutPassword.visibility = View.GONE
            buttonForgotPassword.visibility = View.GONE
        } else {
            buttonForgotPassword.setOnClickListener(this)
        }
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
            textInputLayoutEmail.error = getString(R.string.password_should_be_6_characters)
            return
        }

        val request = LoginRequest(email, password)

        RJRetrofit.getInstance().create(LoginInterface::class.java).loginRequest(request).enqueue(object : Callback<LoginResponse> {

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                if (response.isSuccessful) {
                    Utils.showShortToast("Cuenta creada!") //ward
                } else {
                    Utils.showShortToast("Algo salio mal :(") //ward
                }

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Utils.showShortToast("Error en la conexion(?)") //ward
            }

        })
    }

    private fun doSignUp() {

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

        buttonLogin.alpha = 0.5f
        buttonLogin.isEnabled = false

        Handler(Looper.getMainLooper()).postDelayed({

            buttonLogin.alpha = 1f
            buttonLogin.isEnabled = true

        }, 3000)
    }

    private fun launchResetPassword() {

        val intent = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intent)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_login, menu)
        var menuItem = menu?.findItem(R.id.menuLogin)

        buttonLogin = menuItem?.actionView as Button

        buttonLogin.text = getString(R.string.login)
        buttonLogin.setTextColor(getColor(R.color.yellow_ff))
        buttonLogin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        buttonLogin.typeface = Utils.montserratBlack()
        buttonLogin.setBackgroundColor(Color.TRANSPARENT)
        buttonLogin.setOnClickListener(this)

        return true
    }
}
