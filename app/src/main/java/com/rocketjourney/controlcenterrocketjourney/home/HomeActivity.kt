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
import com.rocketjourney.controlcenterrocketjourney.home.responses.ClubDataResponse
import com.rocketjourney.controlcenterrocketjourney.home.responses.SpotStatusResponse
import com.rocketjourney.controlcenterrocketjourney.login.FirstScreenActivity
import com.rocketjourney.controlcenterrocketjourney.structure.managers.SessionManager
import com.rocketjourney.controlcenterrocketjourney.structure.network.RJRetrofit
import com.rocketjourney.controlcenterrocketjourney.structure.network.utils.Utils
import com.rocketjourney.controlcenterrocketjourney.structure.objects.User
import com.rocketjourney.controlcenterrocketjourney.structure.utils.RoundCornersTransform
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
                Utils.showShortToast("Create invite")
            }

        }

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

                            spotUsersFragment!!.updateUsersList(clubInfo.id, currentSpotId)
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

        if (menuItem.title == lastMenuSelected?.title) return

        menuItem.isChecked = true

        if (lastMenuSelected != null) {
            lastMenuSelected?.isChecked = false
        }

        lastMenuSelected = menuItem

        when (menuItem.title) {

            getString(R.string.terms_of_service) -> {
                //ward
            }

            getString(R.string.privacy_policy) -> {
                //ward
            }

            getString(R.string.log_out) -> {
                SessionManager.closeSession()

                val intent = Intent(applicationContext, FirstScreenActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

            else -> {

                val isAllSpots: Boolean = menuItem.title == getString(R.string.all_locations)

                currentSpotId = if (isAllSpots) {

                    ALL_SPOTS

                } else {

                    if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.M) {

                        for (spot in spots) {
                            if (spot.name == menuItem.title) {
                                spot.id.toString()
                            }
                        }

                        ""

                    } else {

                        spots.stream().filter { i -> i.name == menuItem.title }.findFirst().get().id.toString()

                    }
                }

                if (currentFragment is ClubDashboardFragment) {

                    dashboardFragment.updateDashboardData(clubInfo.id, currentSpotId, spots.size)

                } else if (currentFragment is SpotUsersFragment) {

                    spotUsersFragment?.updateUsersList(clubInfo.id, currentSpotId)

                }


            }

        }

    }

    private fun handleClubSpotsResponse(response: Response<ClubDataResponse>) {

        when (response.code()) {

            200 -> {

                val data = response.body()?.data as ClubData
                clubInfo = data.clubInfo

                Picasso.get().load(clubInfo.badgeUrl)
                        .transform(RoundCornersTransform(Utils.ROUND_CORNERS_CLUBS_RECYCLER_VIEW, 0, true, true))
                        .fit().centerCrop().into(componentToolbar.imageViewToolbarLogo)

                if (data.user?.permission == OWNER) {
                    imageButtonAddUser.visibility = View.VISIBLE
                } else {
                    imageButtonAddUser.visibility = View.GONE
                }

                textViewUserName.text = "${data.user.firstName} ${data.user.lastName}"

                val menu = navigationViewHome.menu

                var groupCount = 0
                spots = ArrayList<AccesibleSpot>(data.accesibleSpots)

                if (spots.size > 1) {
                    menu.add(groupCount, R.id.menuItem, 100, getString(R.string.all_locations))
                    groupCount++
                }

                for (spot in spots) {
                    menu.add(groupCount, R.id.menuItem, 100, spot.name)
                }

                groupCount++

                menu.add(groupCount, R.id.menuItem, 100, getString(R.string.terms_of_service))
                menu.add(groupCount, R.id.menuItem, 100, getString(R.string.privacy_policy))
                menu.add(groupCount, R.id.menuItem, 100, getString(R.string.log_out))

                dashboardFragment = ClubDashboardFragment.newInstance(clubInfo.id, currentSpotId, spots.size)
                setFragment(dashboardFragment)
                handleMenuItemSelected(menu.getItem(0))

            }

            //ward
            401 -> {

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
