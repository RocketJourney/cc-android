package com.rocketjourney.controlcenterrocketjourney.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.home.HomeActivity
import com.rocketjourney.controlcenterrocketjourney.login.adapters.SelectClubRecyclerViewAdapter
import com.rocketjourney.controlcenterrocketjourney.login.interfaces.ClickedClub
import com.rocketjourney.controlcenterrocketjourney.structure.managers.SessionManager
import com.rocketjourney.controlcenterrocketjourney.structure.network.utils.Utils
import com.rocketjourney.controlcenterrocketjourney.structure.objects.Club
import com.rocketjourney.controlcenterrocketjourney.structure.objects.User
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_choose_club_register.*
import kotlinx.android.synthetic.main.component_toolbar_title.view.*

class ChooseClubRegisterActivity : AppCompatActivity() {

    lateinit var user: User
    lateinit var clubsAdapter: SelectClubRecyclerViewAdapter

    private val clickedGym = object : ClickedClub {
        override fun clickedClub(club: Club?) {

            val realm = Realm.getDefaultInstance()

            realm.executeTransaction {
                user.currentClub = club
                it.insertOrUpdate(user)
            }

            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_club_register)

        user = SessionManager.getCurrentSession()!!

        setSupportActionBar(componentToolbar.toolbar)
        Utils.hideToolbarTitle(supportActionBar)

        componentToolbar.textViewToolbarTitle.text = getString(R.string.choose_club)
        componentToolbar.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_yellow_24dp)
        componentToolbar.toolbar.setNavigationOnClickListener { finish() }

        clubsAdapter = SelectClubRecyclerViewAdapter(user.clubs, applicationContext, clickedGym)
        recyclerViewChooseClubRegister.adapter = clubsAdapter
        recyclerViewChooseClubRegister.layoutManager = LinearLayoutManager(applicationContext)
    }
}
