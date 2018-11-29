package com.rocketjourney.controlcenterrocketjourney.home.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rocketjourney.controlcenterrocketjourney.R

class ClubDashboardFragment : Fragment() {

    companion object {

        private const val ARG_ALL_SPOTS = "ARG_ALL_SPOTS"
        private const val ARG_USERS_THAT_CHECKED_IN = "ARG_USERS_THAT_CHECKED_IN"
        private const val ARG_USERS_WITH_TEAM = "ARG_USERS_WITH_TEAM"

        fun newInstance(allSpots: Boolean, usersThatCheckedIn: Int, usersWithTeam: Int): ClubDashboardFragment {

            val fragment = ClubDashboardFragment()

            val args = Bundle()
            args.putBoolean(ARG_ALL_SPOTS, allSpots)
            args.putInt(ARG_USERS_THAT_CHECKED_IN, usersThatCheckedIn)
            args.putInt(ARG_USERS_WITH_TEAM, usersWithTeam)

            fragment.arguments = args

            return fragment
        }
    }

    private lateinit var textViewNumLocations: TextView
    private lateinit var textViewUsersThatCheckedIn: TextView
    private lateinit var textViewUsersWithTeam: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_club_dashboard, container, false)

        textViewNumLocations = view.findViewById(R.id.textViewNumLocations)
        textViewUsersThatCheckedIn = view.findViewById(R.id.textViewCheckedInUsers)
        textViewUsersWithTeam = view.findViewById(R.id.textViewUsersWithTeam)

        val allSpots = arguments!!.getBoolean(ARG_ALL_SPOTS)
        val usersThatCheckedIn = arguments!!.getInt(ARG_USERS_THAT_CHECKED_IN)
        val usersWithTeam = arguments!!.getInt(ARG_USERS_WITH_TEAM)

        refreshData(allSpots, usersThatCheckedIn, usersWithTeam)

        return view
    }

    fun refreshData(allSpots: Boolean, usersThatCheckedIn: Int, usersWithTeam: Int) {

        if (allSpots) textViewNumLocations.visibility = View.VISIBLE
        else textViewNumLocations.visibility = View.GONE

        textViewUsersThatCheckedIn.text = usersThatCheckedIn as String
        textViewUsersWithTeam.text = usersWithTeam as String

    }
}