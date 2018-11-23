package com.rocketjourney.controlcenterrocketjourney.home

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.structure.managers.SessionManager
import com.rocketjourney.controlcenterrocketjourney.structure.network.utils.Utils
import com.rocketjourney.controlcenterrocketjourney.structure.objects.User
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.component_toolbar_title.view.*

class HomeActivity : AppCompatActivity() {

    var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        user = SessionManager.getCurrentSession()

        setSupportActionBar(componentToolbar.toolbar)
        Utils.hideToolbarTitle(supportActionBar)

        componentToolbar.textViewToolbarTitle.text = user?.currentClub?.name!!

        initLeftDrawer()
        fillLeftDrawerInfo()

    }

    private fun fillLeftDrawerInfo() {
        val frameLayoutHeader = navigationViewHome.getHeaderView(0)
        val textViewUserName = frameLayoutHeader.findViewById<TextView>(R.id.textViewUserName)
        val textViewClubName = frameLayoutHeader.findViewById<TextView>(R.id.textViewClubName)
        val imageButtonAddUser = frameLayoutHeader.findViewById<ImageButton>(R.id.imageButtonAddUser)

        textViewUserName.text = "${user?.firstName} ${user?.lastName}"
        textViewClubName.text = user?.currentClub?.name
    }

    private fun initLeftDrawer() {

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)

            navigationViewHome.setNavigationItemSelectedListener { menuItem ->

                // set item as selected to persist highlight
                menuItem.isChecked = true

                // close drawer when item is tapped
                drawerLayoutHome.closeDrawers()

                // Add code here to update the UI based on the item selected
                // For example, swap UI fragments here

                true
            }
        }

        val menu = navigationViewHome.getMenu()
        menu.add("Title1")
        menu.add("Title2")
        menu.add("Title3")
        menu.add("Title4")
//        menu.add(R.id.nav_refer, 124, Menu.NONE, "Title2")
//        menu.add(R.id.nav_refer, 125, Menu.NONE, "Title3")
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
