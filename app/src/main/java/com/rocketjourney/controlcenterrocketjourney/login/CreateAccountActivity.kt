package com.rocketjourney.controlcenterrocketjourney.login

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.View
import android.widget.Button
import com.rocketjourney.controlcenterrocketjourney.LaunchActivity
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.home.HomeActivity
import com.rocketjourney.controlcenterrocketjourney.login.interfaces.LoginInterface
import com.rocketjourney.controlcenterrocketjourney.login.requests.SignUpRequest
import com.rocketjourney.controlcenterrocketjourney.login.responses.SignUpResponse
import com.rocketjourney.controlcenterrocketjourney.login.objects.User
import com.rocketjourney.controlcenterrocketjourney.structure.network.RJRetrofit
import com.rocketjourney.controlcenterrocketjourney.structure.network.utils.Utils
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.component_toolbar_title.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateAccountActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var invitationCode: String
    private lateinit var buttonCreate: Button

    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        setSupportActionBar(componentToolbar.toolbar)
        Utils.hideToolbarTitle(supportActionBar)

        componentToolbar.textViewToolbarTitle.text = getString(R.string.create_account)
        componentToolbar.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_yellow_24dp)
        componentToolbar.toolbar.setNavigationOnClickListener { finish() }

        invitationCode = intent.getStringExtra(LaunchActivity.EXTRA_INVITATION_CODE)
        email = intent.getStringExtra(LaunchActivity.EXTRA_EMAIL)

        editTextEmail.setText(email)
    }

    override fun onClick(v: View?) {

        if (v == buttonCreate) {

            sendRequestCreateAccount()

        }

    }

    private fun sendRequestCreateAccount() {

        val email = editTextEmail.text.toString()
        val firstName = editTextFirstName.text.toString()
        val lastName = editTextLastName.text.toString()
        val password = editTextPassword.text.toString()

        if (!Utils.isValidEmail(email)) {
            Utils.showShortToast(getString(R.string.invalid_email))
            return
        }

        if (firstName.isEmpty()) {
            Utils.showShortToast(getString(R.string.field_cannot_be_empty, "First name")) //ward
            return
        }

        if (lastName.isEmpty()) {
            Utils.showShortToast(getString(R.string.field_cannot_be_empty, "Last name")) //ward
            return
        }

        if (password.length < 6) {
            Utils.showShortToast(getString(R.string.password_should_be_6_characters))
            return
        }

        val user = User(email, firstName, lastName, password)

        val request = SignUpRequest(user, invitationCode)

        buttonCreate.isEnabled = false

        RJRetrofit.getInstance().create(LoginInterface::class.java).signUpRequest(request).enqueue(object : Callback<SignUpResponse> {

            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {

                buttonCreate.isEnabled = true

                Utils.showShortToast("Response code:${response.code()}")//ward

                when (response.code()) {

                    //ward
                    201 -> {

                        val intent = Intent(applicationContext, HomeActivity::class.java)
                        startActivity(intent)
                        setResult(Activity.RESULT_OK)
                        finish()

                    }

                    404 -> {

                    }

                    422 -> {

                    }

                }

            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                buttonCreate.isEnabled = true
                Utils.showShortToast("Error en la conexion(?)") //ward
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_create, menu)
        var menuItem = menu?.findItem(R.id.menuCreate)

        buttonCreate = menuItem?.actionView as Button

        buttonCreate.setTextColor(getColor(R.color.yellow_ff))
        buttonCreate.setText(R.string.create)
        buttonCreate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        buttonCreate.typeface = Utils.montserratBold()
        buttonCreate.setBackgroundColor(Color.TRANSPARENT)
        buttonCreate.setOnClickListener(this)

        return true
    }
}
