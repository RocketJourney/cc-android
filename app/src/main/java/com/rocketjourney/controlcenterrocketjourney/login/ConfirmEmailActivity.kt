package com.rocketjourney.controlcenterrocketjourney.login

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.TypedValue
import android.view.Menu
import android.view.View
import android.widget.Button
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.login.interfaces.LoginInterface
import com.rocketjourney.controlcenterrocketjourney.structure.network.RJRetrofit
import com.rocketjourney.controlcenterrocketjourney.structure.network.utils.Utils
import kotlinx.android.synthetic.main.activity_confirm_email.*
import kotlinx.android.synthetic.main.component_toolbar_title.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfirmEmailActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var buttonNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_email)

        setSupportActionBar(componentToolbar.toolbar)
        Utils.hideToolbarTitle(supportActionBar)

        componentToolbar.textViewToolbarTitle.text = getString(R.string.create_account)
        componentToolbar.toolbar.setNavigationIcon(R.drawable.ic_close_yellow)
        componentToolbar.toolbar.setNavigationOnClickListener { finish() }
    }

    override fun onClick(v: View?) {

        if (v == buttonNext) {
            sendRequest()
        }

    }

    private fun sendRequest() {
        buttonNext.isEnabled = false

        val email = editTextEmail.text.toString()

        if (!Utils.isValidEmail(email)) {
            Utils.showShortToast(getString(R.string.invalid_email))
            return
        }

        //ward inicializar este user
        val user = ""

        RJRetrofit.getInstance().create(LoginInterface::class.java).validateEmail(user, email).enqueue(object : Callback<Void> {

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                buttonNext.isEnabled = true

                when (response.code()) {//ward completar los responses

                    /**
                     * Valid link, user not registered
                     */
                    200 -> {

                        val intent = Intent(application, CreateAccountActivity::class.java)
                        startActivity(intent)

                    }

                    /**
                     * Valid link, user registered and linked with club
                     */
                    201 -> {

                    }

                    /**
                     * Valid link, user registered and already linked with club
                     */
                    304 -> {

                        val emailAlreadyRegistered = AlertDialog.Builder(applicationContext, R.style.StyleAlertDialog)
                        emailAlreadyRegistered.setTitle(getString(R.string.email_is_already_registered))
                        emailAlreadyRegistered.setMessage(getString(R.string.email_already_linked_to_this_club))

                    }

                    /**
                     * Link has expired
                     */
                    400 -> {

                    }

                    /**
                     * Link has expired
                     */
                    404 -> {

                    }

                }

            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                buttonNext.isEnabled = true
                Utils.showShortToast("Error en la conexion(?)") //ward
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_next, menu)
        var menuItem = menu?.findItem(R.id.menuNext)

        buttonNext = menuItem?.actionView as Button

        buttonNext.setTextColor(getColor(R.color.yellow_ff))
        buttonNext.setText(R.string.next)
        buttonNext.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        buttonNext.typeface = Utils.montserratBlack()
        buttonNext.setBackgroundColor(Color.TRANSPARENT)
        buttonNext.setOnClickListener(this)

        return true
    }
}
