package com.rocketjourney.controlcenterrocketjourney.login

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.View
import android.widget.Button
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.structure.network.utils.Utils
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.component_toolbar_title.view.*

class ForgotPasswordActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var buttonSend: Button

    override fun onClick(v: View?) {

        if (v == buttonSend) {
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

        Utils.showShortToast("Todo en orden :+1:")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_login, menu)
        var menuItem = menu?.findItem(R.id.menuLogin)

        buttonSend = menuItem?.actionView as Button

        buttonSend.text = getString(R.string.send)
        buttonSend.setTextColor(getColor(R.color.yellow_ff))
        buttonSend.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        buttonSend.typeface = Utils.montserratBlack()
        buttonSend.setBackgroundColor(Color.TRANSPARENT)
        buttonSend.setOnClickListener(this)

        return true
    }
}
