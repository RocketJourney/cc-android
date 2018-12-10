package com.rocketjourney.controlcenterrocketjourney.home

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.home.fragments.ClubDashboardFragment
import com.rocketjourney.controlcenterrocketjourney.home.fragments.SpotUsersFragment
import com.rocketjourney.controlcenterrocketjourney.home.interfaces.HomeInterface
import com.rocketjourney.controlcenterrocketjourney.home.objects.AccesibleSpot
import com.rocketjourney.controlcenterrocketjourney.home.objects.ClubData
import com.rocketjourney.controlcenterrocketjourney.home.objects.ClubInfo
import com.rocketjourney.controlcenterrocketjourney.home.objects.SpotStructure
import com.rocketjourney.controlcenterrocketjourney.home.responses.ClubDataResponse
import com.rocketjourney.controlcenterrocketjourney.structure.managers.SessionManager
import com.rocketjourney.controlcenterrocketjourney.structure.network.RJRetrofit
import com.rocketjourney.controlcenterrocketjourney.structure.network.utils.Utils
import com.rocketjourney.controlcenterrocketjourney.structure.objects.User
import com.rocketjourney.controlcenterrocketjourney.structure.utils.RoundCornersTransform
import com.rocketjourney.controlcenterrocketjourney.structure.views.WebViewActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.component_toolbar_title.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private const val OWNER = "owner"
        const val ALL_SPOTS = "all_spots"
        const val SOME_SPOTS = "some_spots"

        const val SERIALIZABLE_EXTRA_SPOTS = "SERIALIZABLE_EXTRA_SPOTS"
        const val SERIALIZABLE_EXTRA_CLUB = "SERIALIZABLE_EXTRA_CLUB"
    }

    var user: User? = null

    lateinit var textViewUserName: TextView
    lateinit var textViewClubName: TextView
    lateinit var imageButtonAddUser: ImageButton

    lateinit var clubInfo: ClubInfo
    lateinit var spots: ArrayList<AccesibleSpot>

    private var currentSpotId = ALL_SPOTS
    private var lastMenuSelected: MenuItem? = null
    private var currentFragment: Fragment? = null

    lateinit var dashboardFragment: ClubDashboardFragment
    var spotUsersFragment: SpotUsersFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        user = SessionManager.getCurrentSession()

        println(user)

        setSupportActionBar(componentToolbar.toolbar)
        Utils.hideToolbarTitle(supportActionBar)

        componentToolbar.textViewToolbarTitle.text = user?.currentClub?.name!!
        componentToolbar.imageViewToolbarLogo.visibility = View.VISIBLE

        initLeftDrawer()
        fillLeftDrawerInfo()
        initBottomNavigation()

    }

    private fun setFragment(fragment: Fragment) {

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayoutHomeContainer, fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()

        currentFragment = fragment

    }

    override fun onClick(v: View?) {

        when (v) {

            imageButtonAddUser -> {
                launchInviteActivity()
            }

        }

    }

    private fun launchInviteActivity() {

        val spotsStructure = ArrayList<SpotStructure>()

        spotsStructure.add(SpotStructure(SpotStructure.SpotItemType.DESCRIPTION, null, false))
        spotsStructure.add(SpotStructure(SpotStructure.SpotItemType.ALL_SPOTS, null, false))

        for (spot in spots) {
            spotsStructure.add(SpotStructure(SpotStructure.SpotItemType.SPOT, spot, false))
        }

        val intent = Intent(this@HomeActivity, InviteUsersActivity::class.java)
        intent.putExtra(SERIALIZABLE_EXTRA_SPOTS, spotsStructure)
        intent.putExtra(SERIALIZABLE_EXTRA_CLUB, clubInfo)
        startActivity(intent)

        overridePendingTransition(R.anim.anim_bottom_to_center, R.anim.anim_fade_out)

    }

    private fun initBottomNavigation() {
        bottomNavigationHome.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {

            override fun onNavigationItemSelected(menu: MenuItem): Boolean {

                when (menu.itemId) {

                    R.id.menuDashboard -> {

                        if (currentFragment !is ClubDashboardFragment) {

                            dashboardFragment.updateDashboardData(clubInfo.id, currentSpotId, spots.size)
                            setFragment(dashboardFragment)

                        }

                    }

                    R.id.menuUsers -> {

                        if (spotUsersFragment == null) {

                            spotUsersFragment = SpotUsersFragment.newInstance(clubInfo.id, currentSpotId)

                        }

                        if (currentFragment !is SpotUsersFragment) {
                            spotUsersFragment!!.updateSpotUsersData(clubInfo.id, currentSpotId)
                            setFragment(spotUsersFragment!!)

                        }

                    }

                }

                return true
            }

        })
    }

    private fun fillLeftDrawerInfo() {
        val frameLayoutHeader = navigationViewHome.getHeaderView(0)
        textViewUserName = frameLayoutHeader.findViewById<TextView>(R.id.textViewUserName)
        textViewClubName = frameLayoutHeader.findViewById<TextView>(R.id.textViewClubName)

        imageButtonAddUser = frameLayoutHeader.findViewById<ImageButton>(R.id.imageButtonAddUser)
        imageButtonAddUser.setOnClickListener(this@HomeActivity)

        textViewClubName.text = user?.currentClub?.name

        progressBarHome.visibility = View.VISIBLE

        RJRetrofit.getInstance().create(HomeInterface::class.java).getClubSpots(user?.token!!, user?.currentClub!!.id).enqueue(object : Callback<ClubDataResponse> {

            override fun onResponse(call: Call<ClubDataResponse>, response: Response<ClubDataResponse>) {

                progressBarHome.visibility = View.GONE
                handleClubSpotsResponse(response)

            }

            override fun onFailure(call: Call<ClubDataResponse>, t: Throwable) {
                progressBarHome.visibility = View.GONE
            }

        })
    }

    private fun initLeftDrawer() {

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)

            navigationViewHome.setNavigationItemSelectedListener { menuItem ->

                drawerLayoutHome.closeDrawers()
                handleMenuItemSelected(menuItem)

                true
            }
        }

    }

    private fun handleMenuItemSelected(menuItem: MenuItem) {

        val selectedSpotName = menuItem.title

        if (selectedSpotName == lastMenuSelected?.title) return

        menuItem.isChecked = true

        if (lastMenuSelected != null) {
            lastMenuSelected?.isChecked = false
        }

        lastMenuSelected = menuItem

        when (selectedSpotName) {

            getString(R.string.terms_of_service) -> {

                val intent = Intent(this@HomeActivity, WebViewActivity::class.java)
                intent.putExtra(WebViewActivity.EXTRA_TITLE, getString(R.string.terms_of_service))
                intent.putExtra(WebViewActivity.EXTRA_URL, getString(R.string.local_terms_path))
                startActivity(intent)

            }

            getString(R.string.privacy_policy) -> {
                //ward
            }

            getString(R.string.log_out) -> {
                SessionManager.closeSessionAndDisplayFirstScreen(applicationContext)
            }

            else -> {

                componentToolbar.textViewToolbarTitle.text = selectedSpotName
                val isAllSpots: Boolean = selectedSpotName == getString(R.string.all_locations) || selectedSpotName == getString(R.string.all_my_spots)

                currentSpotId = if (isAllSpots) {

                    ALL_SPOTS

                } else {

                    if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.M) {

                        getSpotId(selectedSpotName.toString())

                    } else {

                        spots.stream().filter { i -> i.branchName == selectedSpotName }.findFirst().get().id.toString()

                    }
                }

                if (currentFragment is ClubDashboardFragment) {

                    dashboardFragment.updateDashboardData(clubInfo.id, currentSpotId, spots.size)

                } else if (currentFragment is SpotUsersFragment) {

                    spotUsersFragment?.resetValuesForRequests()
                    spotUsersFragment?.updateUsersList(clubInfo.id, currentSpotId, SpotUsersFragment.SET_USERS)

                }

            }

        }

    }

    private fun getSpotId(branchName: String): String {
        for (spot in spots) {
            if (spot.branchName == branchName) {
                return spot.id.toString()
            }
        }
        return ""
    }

    private fun handleClubSpotsResponse(response: Response<ClubDataResponse>) {

        when (response.code()) {

            200 -> {

                val data = response.body()?.data as ClubData
                clubInfo = data.clubInfo

                Picasso.get().load(clubInfo.badgeUrl)
                        .transform(RoundCornersTransform(Utils.ROUND_CORNERS_CLUBS_RECYCLER_VIEW, 0, true, true))
                        .fit().centerCrop().into(componentToolbar.imageViewToolbarLogo)

                val permissions = data.user?.permission

                if (permissions == OWNER) {
                    imageButtonAddUser.visibility = View.VISIBLE
                } else {
                    imageButtonAddUser.visibility = View.GONE
                }

                textViewUserName.text = "${data.user.firstName} ${data.user.lastName}"

                val menu = navigationViewHome.menu

                var groupCount = 0
                spots = ArrayList<AccesibleSpot>(data.accesibleSpots)

                if (spots.size > 0) {
                    menu.add(groupCount, R.id.menuItem, 100,
                            if (permissions == OWNER || permissions == ALL_SPOTS)
                                getString(R.string.all_locations)
                            else
                                getString(R.string.all_my_spots)
                    )
                    groupCount++
                }

                spots.sortBy {
                    it.name
                }

                for (spot in spots) {
                    menu.add(groupCount, R.id.menuItem, 100, spot.branchName)
                }

                groupCount++

                menu.add(groupCount, R.id.menuItem, 100, getString(R.string.terms_of_service))
//                menu.add(groupCount, R.id.menuItem, 100, getString(R.string.privacy_policy))
                menu.add(groupCount, R.id.menuItem, 100, getString(R.string.log_out))

                dashboardFragment = ClubDashboardFragment.newInstance(clubInfo.id, currentSpotId, spots.size)
                setFragment(dashboardFragment)
                handleMenuItemSelected(menu.getItem(0))

            }

            //ward
            401 -> {
                SessionManager.closeSessionAndDisplayFirstScreen(applicationContext)
            }

            //ward
            403 -> {

            }

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayoutHome.openDrawer(GravityCompat.START)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }
}
