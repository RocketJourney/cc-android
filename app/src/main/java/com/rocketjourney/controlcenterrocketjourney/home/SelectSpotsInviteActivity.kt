package com.rocketjourney.controlcenterrocketjourney.home

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.TypedValue
import android.view.Menu
import android.view.View
import android.widget.Button
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.home.adapters.SpotsInviteUsersRecyclerViewAdapter
import com.rocketjourney.controlcenterrocketjourney.home.interfaces.HomeInterface
import com.rocketjourney.controlcenterrocketjourney.home.interfaces.OnEnableNextButtonInterface
import com.rocketjourney.controlcenterrocketjourney.home.objects.ClubInfo
import com.rocketjourney.controlcenterrocketjourney.home.objects.Invitation
import com.rocketjourney.controlcenterrocketjourney.home.objects.SpotStructure
import com.rocketjourney.controlcenterrocketjourney.home.requests.InviteRequest
import com.rocketjourney.controlcenterrocketjourney.home.responses.InviteResponse
import com.rocketjourney.controlcenterrocketjourney.structure.managers.SessionManager
import com.rocketjourney.controlcenterrocketjourney.structure.network.RJRetrofit
import com.rocketjourney.controlcenterrocketjourney.structure.network.utils.Utils
import com.rocketjourney.controlcenterrocketjourney.structure.objects.User
import kotlinx.android.synthetic.main.activity_invite_users.*
import kotlinx.android.synthetic.main.component_toolbar_title.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectSpotsInviteActivity : AppCompatActivity(), View.OnClickListener, OnEnableNextButtonInterface {

    companion object {
        const val EXTRA_INVITE_LINK = "EXTRA_INVITE_LINK"
        const val ACTIVITY_FOR_RESULT_DONE = 100
    }

    override fun onClick(v: View?) {

        when (v) {

            buttonNext -> {
                doInviteRequest()
            }

        }

    }

    override fun enableNextButton(shouldEnableButton: Boolean) {
        buttonNext.isEnabled = shouldEnableButton

        if (shouldEnableButton) {
            buttonNext.alpha = 1f
        } else {
            buttonNext.alpha = 0.2f
        }
    }

    var user: User? = null

    private lateinit var buttonNext: Button

    private lateinit var spots: ArrayList<SpotStructure>
    private lateinit var club: ClubInfo
    private lateinit var adapter: SpotsInviteUsersRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_users)

        user = SessionManager.getCurrentSession()

        spots = intent.getSerializableExtra(HomeActivity.SERIALIZABLE_EXTRA_SPOTS) as ArrayList<SpotStructure>
        club = intent.getSerializableExtra(HomeActivity.SERIALIZABLE_EXTRA_CLUB) as ClubInfo

        setSupportActionBar(componentToolbar.toolbar)
        Utils.hideToolbarTitle(supportActionBar)

        componentToolbar.textViewToolbarTitle.text = getString(R.string.invite_colleague)
        componentToolbar.toolbar.setNavigationIcon(R.drawable.ic_close_yellow)
        componentToolbar.toolbar.setNavigationOnClickListener {
            finish()
            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_center_to_bottom)
        }

        adapter = SpotsInviteUsersRecyclerViewAdapter(spots, applicationContext, this)

        recyclerViewInviteUsers.layoutManager = LinearLayoutManager(applicationContext)
        recyclerViewInviteUsers.adapter = adapter
    }

    private fun doInviteRequest() {

        val ids = adapter.getSelectedSpotsId()

        val request = InviteRequest()
        val invitation = Invitation()

        invitation.spots = ids
        invitation.clubId = club.id
        invitation.permission = adapter.getPermission()

        if (invitation.permission == HomeActivity.ALL_SPOTS) {
            invitation.spots = ArrayList()
        }

        request.invitation = invitation

        recyclerViewInviteUsers.alpha = 0.2f
        viewBlockClicks.visibility = View.VISIBLE

        buttonNext.alpha = 0.2f
        buttonNext.isEnabled = false

        RJRetrofit.getInstance().create(HomeInterface::class.java).createInviteRequest(user!!.token, request)
                .enqueue(object : Callback<InviteResponse> {

                    override fun onResponse(call: Call<InviteResponse>, response: Response<InviteResponse>) {

                        recyclerViewInviteUsers.alpha = 1f
                        viewBlockClicks.visibility = View.GONE
                        buttonNext.isEnabled = true
                        buttonNext.alpha = 1f

                        when (response.code()) {

                            201 -> {

                                val intent = Intent(this@SelectSpotsInviteActivity, SendInviteLinkActivity::class.java)
                                intent.putExtra(EXTRA_INVITE_LINK, response.body()?.data?.invitationLink)
                                startActivityForResult(intent, ACTIVITY_FOR_RESULT_DONE)

                            }

                            422 -> {

                            }

                        }

                    }

                    override fun onFailure(call: Call<InviteResponse>, t: Throwable) {
                        recyclerViewInviteUsers.alpha = 1f
                        viewBlockClicks.visibility = View.GONE
                        buttonNext.isEnabled = true
                        buttonNext.alpha = 1f
                        //ward
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
        buttonNext.typeface = Utils.montserratBold()
        buttonNext.setBackgroundColor(Color.TRANSPARENT)
        buttonNext.setOnClickListener(this)
        buttonNext.alpha = 0.2f

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode != Activity.RESULT_OK) return

        if (ACTIVITY_FOR_RESULT_DONE == requestCode) {
            finish()
            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_center_to_bottom)
        }

    }
}
