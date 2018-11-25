package com.rocketjourney.controlcenterrocketjourney.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.Menu
import android.view.View
import android.widget.Button
import com.rocketjourney.controlcenterrocketjourney.LaunchActivity
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.home.HomeActivity
import com.rocketjourney.controlcenterrocketjourney.login.interfaces.LoginInterface
import com.rocketjourney.controlcenterrocketjourney.login.objects.UserData
import com.rocketjourney.controlcenterrocketjourney.login.requests.SignUpRequest
import com.rocketjourney.controlcenterrocketjourney.login.responses.SignUpResponse
import com.rocketjourney.controlcenterrocketjourney.structure.managers.SessionManager
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

        cleanViews()

        editTextEmail.setText(email)
    }

    override fun onClick(v: View?) {

        if (v == buttonCreate) {

            sendRequestCreateAccount()

        }

    }

    private fun cleanViews() {
        textInputLayoutEmail.error = ""
        textInputLayoutFirstName.error = ""
        textInputLayoutLastName.error = ""
        textInputLayoutPassword.error = ""
    }

    private fun sendRequestCreateAccount() {

        cleanViews()

        val email = editTextEmail.text.toString()
        val firstName = editTextFirstName.text.toString()
        val lastName = editTextLastName.text.toString()
        val password = editTextPassword.text.toString()

        if (!Utils.isValidEmail(email)) {
            textInputLayoutEmail.error = getString(R.string.invalid_email)
            return
        }

        if (firstName.isEmpty()) {
            textInputLayoutFirstName.error = getString(R.string.this_field_cant_be_empty)
            return
        }

        if (lastName.isEmpty()) {
            textInputLayoutLastName.error = getString(R.string.this_field_cant_be_empty)
            return
        }

        if (password.length < 6) {
            textInputLayoutPassword.error = getString(R.string.password_should_be_6_characters)
            return
        }

        val user = UserData(email, firstName, lastName, password)

        val request = SignUpRequest(user, invitationCode)

        buttonCreate.isEnabled = false
        progressBar.visibility = View.VISIBLE

        RJRetrofit.getInstance().create(LoginInterface::class.java).signUpRequest(request).enqueue(object : Callback<SignUpResponse> {

            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {

                buttonCreate.isEnabled = true
                progressBar.visibility = View.GONE

                when (response.code()) {

                    //ward
                    201 -> {

                        SessionManager.closeSession()
                        SessionManager.createSession(email, response.body()!!.data)

                        val intent = Intent(applicationContext, HomeActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)

                    }

                    404 -> {

                    }

                    422 -> {

                    }

                }

            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                buttonCreate.isEnabled = true
                progressBar.visibility = View.GONE
                Utils.showShortToast(getString(R.string.no_network_connection))
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
