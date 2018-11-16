package com.rocketjourney.controlcenterrocketjourney.login

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.TypedValue
import android.view.Menu
import android.view.View
import android.widget.Button
import com.rocketjourney.controlcenterrocketjourney.LaunchActivity
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

    companion object {
        const val ACTIVITY_FOR_RESULT_LOG_IN = 101
    }

    private lateinit var invitationCode: String

    private lateinit var buttonNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_email)

        setSupportActionBar(componentToolbar.toolbar)
        Utils.hideToolbarTitle(supportActionBar)

        componentToolbar.textViewToolbarTitle.text = getString(R.string.create_account)
        componentToolbar.toolbar.setNavigationIcon(R.drawable.ic_close_yellow)
        componentToolbar.toolbar.setNavigationOnClickListener { finish() }

        invitationCode = intent.getStringExtra(LaunchActivity.EXTRA_INVITATION_CODE)

    }

    override fun onClick(v: View?) {

        if (v == buttonNext) {
            sendRequest()
        }

    }

    private fun sendRequest() {

        val email = editTextEmail.text.toString()

        if (!Utils.isValidEmail(email)) {
            Utils.showShortToast(getString(R.string.invalid_email))
            return
        }

        buttonNext.isEnabled = false

        RJRetrofit.getInstance().create(LoginInterface::class.java).validateEmail(invitationCode, email).enqueue(object : Callback<Void> {

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                buttonNext.isEnabled = true

                when (response.code()) {

                    /**
                     * Valid link, user not registered
                     */
                    200 -> {

                        val intent = Intent(application, CreateAccountActivity::class.java)
                        intent.putExtra(LaunchActivity.EXTRA_INVITATION_CODE, invitationCode)
                        startActivityForResult(intent, ACTIVITY_FOR_RESULT_LOG_IN)

                    }

                    /**
                     * Valid link, user registered and linked with club or already linked
                     */
                    201, 304 -> {

                        val intent = Intent(applicationContext, ChooseClubRegisterActivity::class.java)//ward cambiar al activity del home cuando se cree
                        startActivity(intent)
                        finish()

                    }

                    /**
                     * Link has expired
                     */
                    400 -> {

                        val linkExpiredAlert = AlertDialog.Builder(this@ConfirmEmailActivity, R.style.StyleAlertDialog)
                        linkExpiredAlert.setTitle(getString(R.string.link_expired))
                        linkExpiredAlert.setMessage(getString(R.string.ask_your_gym_for_another_link))
                        linkExpiredAlert.setPositiveButton(getString(R.string.ok), object : DialogInterface.OnClickListener {

                            override fun onClick(dialog: DialogInterface?, which: Int) {

                            }

                        })
                        linkExpiredAlert.show()

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode != Activity.RESULT_OK) return

        if (requestCode == ACTIVITY_FOR_RESULT_LOG_IN) {
            finish()
        }

    }
}
