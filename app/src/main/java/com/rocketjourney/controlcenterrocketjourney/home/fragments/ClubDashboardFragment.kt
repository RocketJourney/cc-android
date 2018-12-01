package com.rocketjourney.controlcenterrocketjourney.home.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.home.interfaces.HomeInterface
import com.rocketjourney.controlcenterrocketjourney.home.responses.SpotStatusResponse
import com.rocketjourney.controlcenterrocketjourney.structure.managers.SessionManager
import com.rocketjourney.controlcenterrocketjourney.structure.network.RJRetrofit
import com.rocketjourney.controlcenterrocketjourney.structure.objects.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClubDashboardFragment : Fragment() {

    companion object {

        private const val ARG_ALL_SPOTS = "ARG_ALL_SPOTS"
        private const val ARG_TOTAL_SPOTS = "ARG_TOTAL_SPOTS"
        private const val ARG_USERS_THAT_CHECKED_IN = "ARG_USERS_THAT_CHECKED_IN"
        private const val ARG_USERS_WITH_TEAM = "ARG_USERS_WITH_TEAM"

        fun newInstance(allSpots: Boolean, totalSpots: Int, usersThatCheckedIn: Int, usersWithTeam: Int): ClubDashboardFragment {

            val fragment = ClubDashboardFragment()

            val args = Bundle()
            args.putBoolean(ARG_ALL_SPOTS, allSpots)
            args.putInt(ARG_TOTAL_SPOTS, totalSpots)
            args.putInt(ARG_USERS_THAT_CHECKED_IN, usersThatCheckedIn)
            args.putInt(ARG_USERS_WITH_TEAM, usersWithTeam)

            fragment.arguments = args

            return fragment
        }
    }

    lateinit var user: User

    private lateinit var textViewNumLocations: TextView
    private lateinit var textViewUsersThatCheckedIn: TextView
    private lateinit var textViewUsersWithTeam: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_club_dashboard, container, false)

        user = SessionManager.getCurrentSession()!!

        textViewNumLocations = view.findViewById(R.id.textViewNumLocations)
        textViewUsersThatCheckedIn = view.findViewById(R.id.textViewCheckedInUsers)
        textViewUsersWithTeam = view.findViewById(R.id.textViewUsersWithTeam)

        val allSpots = arguments?.getBoolean(ARG_ALL_SPOTS)
        val totalSpots = arguments?.getInt(ARG_TOTAL_SPOTS)
        val usersThatCheckedIn = arguments?.getInt(ARG_USERS_THAT_CHECKED_IN)
        val usersWithTeam = arguments?.getInt(ARG_USERS_WITH_TEAM)

        refreshData(allSpots, totalSpots, usersThatCheckedIn, usersWithTeam)

        return view
    }

    fun setDashboardData(allSpots: Boolean, totalSpots: Int, usersThatCheckedIn: Int, usersWithTeam: Int) {
        val args = Bundle()
        args.putBoolean(ARG_ALL_SPOTS, allSpots)
        args.putInt(ARG_TOTAL_SPOTS, totalSpots)
        args.putInt(ARG_USERS_THAT_CHECKED_IN, usersThatCheckedIn)
        args.putInt(ARG_USERS_WITH_TEAM, usersWithTeam)
        arguments = args
    }

    fun refreshData(isAllSpots: Boolean?, totalSpots: Int?, usersThatCheckedIn: Int?, usersWithTeam: Int?) {

        setDashboardData(isAllSpots!!, totalSpots!!, usersThatCheckedIn!!, usersWithTeam!!)

        if (isAllSpots!!) {
            textViewNumLocations.visibility = View.VISIBLE
            textViewNumLocations.text = getString(R.string.num_locations, totalSpots.toString())
        } else textViewNumLocations.visibility = View.GONE

        textViewUsersThatCheckedIn.text = usersThatCheckedIn.toString()
        textViewUsersWithTeam.text = usersWithTeam.toString()

    }

    fun updateDashboardData(clubId: Int, spotIdOrAllSpots: String) {

        RJRetrofit.getInstance().create(HomeInterface::class.java).getSpotStatus(user!!.token, clubId, spotIdOrAllSpots)
                .enqueue(object : Callback<SpotStatusResponse> {

                    override fun onResponse(call: Call<SpotStatusResponse>, response: Response<SpotStatusResponse>) {
                        handleSpotStatusResponse(response, spotIdOrAllSpots == getString(R.string.all_locations))
                    }

                    override fun onFailure(call: Call<SpotStatusResponse>, t: Throwable) {

                    }

                })

    }

    private fun handleSpotStatusResponse(response: Response<SpotStatusResponse>, isAllSpots: Boolean) {

        when (response.code()) {

            200 -> {
                refreshData(isAllSpots, spots.size, response.body()?.spotStatus!!.totalUsersCheckedIn, response.body()?.spotStatus!!.totalUsersWithTeam)

            }

            //ward
            401 -> {

            }

            //ward
            403 -> {

            }

            //ward
            404 -> {

            }

        }

    }


}