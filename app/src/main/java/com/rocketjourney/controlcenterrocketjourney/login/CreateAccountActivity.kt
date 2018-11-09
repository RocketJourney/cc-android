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
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.component_toolbar_title.view.*

class CreateAccountActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var buttonCreate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        setSupportActionBar(componentToolbar.toolbar)
        Utils.hideToolbarTitle(supportActionBar)

        componentToolbar.textViewToolbarTitle.text = "Create account" //ward
        componentToolbar.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_yellow_24dp)
        componentToolbar.toolbar.setOnClickListener { finish() }
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
            Utils.showShortToast(getString(R.string.field_cannot_be_empty, "first name")) //ward
            return
        }

        if (lastName.isEmpty()) {
            Utils.showShortToast(getString(R.string.field_cannot_be_empty, "last name")) //ward
            return
        }

        if (password.isEmpty()) {
            Utils.showShortToast(getString(R.string.field_cannot_be_empty, getString(R.string.password)))
            return
        }

        //TODO
        Utils.showShortToast("ready to go!")//ward
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_create, menu)
        var menuItem = menu?.findItem(R.id.menuCreate)

        buttonCreate = menuItem?.actionView as Button

        buttonCreate.setTextColor(getColor(R.color.yellow_ff))
        buttonCreate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        buttonCreate.typeface = Utils.montserratBlack()
        buttonCreate.setBackgroundColor(Color.TRANSPARENT)
        buttonCreate.setOnClickListener(this)

        return true
    }
}
