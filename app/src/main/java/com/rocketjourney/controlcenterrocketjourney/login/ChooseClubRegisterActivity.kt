package com.rocketjourney.controlcenterrocketjourney.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.structure.network.utils.Utils
import kotlinx.android.synthetic.main.activity_choose_club_register.*
import kotlinx.android.synthetic.main.component_toolbar_title.view.*

class ChooseClubRegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_club_register)

        setSupportActionBar(componentToolbar.toolbar)
        Utils.hideToolbarTitle(supportActionBar)

        componentToolbar.textViewToolbarTitle.text = getString(R.string.choose_club)
        componentToolbar.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_yellow_24dp)
        componentToolbar.toolbar.setNavigationOnClickListener { finish() }
    }
}
