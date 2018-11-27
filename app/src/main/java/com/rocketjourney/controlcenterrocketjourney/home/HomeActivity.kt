package com.rocketjourney.controlcenterrocketjourney.home

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.login.FirstScreenActivity
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
        initBottomNavigation()

    }

    private fun initBottomNavigation() {
        bottomNavigationHome.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {

            override fun onNavigationItemSelected(menu: MenuItem): Boolean {

                when (menu.itemId) {

                    R.id.menuDashboard -> {
                        Utils.showShortToast("dashboard")
                    }

                    R.id.menuUsers -> {
                        Utils.showShortToast("users")
                    }

                }

                return true
            }

        })
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


        val menu = navigationViewHome.menu
        menu.add("Title1")
        menu.add("Title2")
        menu.add("Title3")
        menu.add("Title4")

        menu.add(1, R.id.menuNext, 100, "prueba1")
        menu.add(1, R.id.menuNext, 100, "prueba2")
        menu.add(2, R.id.menuNext, 100, "Sports World")
        menu.add(2, R.id.menuNext, 100, "54D")

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

    fun logout(view: View){
        SessionManager.closeSession()

        val intent = Intent(applicationContext, FirstScreenActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
