package com.rocketjourney.controlcenterrocketjourney.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.home.adapters.SpotsInviteUsersRecyclerViewAdapter
import com.rocketjourney.controlcenterrocketjourney.home.objects.SpotStructure
import com.rocketjourney.controlcenterrocketjourney.structure.network.utils.Utils
import kotlinx.android.synthetic.main.activity_invite_users.*
import kotlinx.android.synthetic.main.component_toolbar_title.view.*

class InviteUsersActivity : AppCompatActivity() {

    lateinit var spots: ArrayList<SpotStructure>
    lateinit var adapter: SpotsInviteUsersRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_users)

        spots = intent.getSerializableExtra(HomeActivity.SERIALIZABLE_EXTRA_SPOTS) as ArrayList<SpotStructure>

        setSupportActionBar(componentToolbar.toolbar)
        Utils.hideToolbarTitle(supportActionBar)

        componentToolbar.textViewToolbarTitle.text = "Invite users!!"//ward
        componentToolbar.toolbar.setNavigationIcon(R.drawable.ic_close_yellow)
        componentToolbar.toolbar.setNavigationOnClickListener { finish() }

        adapter = SpotsInviteUsersRecyclerViewAdapter(spots, applicationContext)

        recyclerViewInviteUsers.layoutManager = LinearLayoutManager(applicationContext)
        recyclerViewInviteUsers.adapter = adapter
    }
}
