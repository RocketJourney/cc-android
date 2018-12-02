package com.rocketjourney.controlcenterrocketjourney.home.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.home.HomeActivity
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

        private const val ARG_CLUB_ID = "ARG_CLUB_ID"
        private const val ARG_SPOT_ID_OR_ALL_SPOTS = "ARG_SPOT_ID_OR_ALL_SPOTS"
        private const val ARG_SPOTS_SIZE = "ARG_SPOTS_SIZE"

        fun newInstance(clubId: Int, spotIdOrAllSpots: String, spotsSize: Int): ClubDashboardFragment {

            val fragment = ClubDashboardFragment()

            val args = Bundle()
            args.putInt(ARG_CLUB_ID, clubId)
            args.putString(ARG_SPOT_ID_OR_ALL_SPOTS, spotIdOrAllSpots)
            args.putInt(ARG_SPOTS_SIZE, spotsSize)

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

        if (!::user.isInitialized) user = SessionManager.getCurrentSession()!!

        textViewNumLocations = view.findViewById(R.id.textViewNumLocations)
        textViewUsersThatCheckedIn = view.findViewById(R.id.textViewCheckedInUsers)
        textViewUsersWithTeam = view.findViewById(R.id.textViewUsersWithTeam)

        val clubId = arguments?.getInt(ARG_CLUB_ID)
        val spotIdOrAllSpots = arguments?.getString(ARG_SPOT_ID_OR_ALL_SPOTS)
        val spotsSize = arguments?.getInt(ARG_SPOTS_SIZE)

        updateDashboardData(clubId!!, spotIdOrAllSpots!!, spotsSize!!)

        return view
    }

    private fun setDashboardData(clubId: Int, spotIdOrAllSpots: String, spotsSize: Int) {
        val args = Bundle()
        args.putInt(ARG_CLUB_ID, clubId)
        args.putString(ARG_SPOT_ID_OR_ALL_SPOTS, spotIdOrAllSpots)
        args.putInt(ARG_SPOTS_SIZE, spotsSize)
        arguments = args
    }

    private fun refreshData(isAllSpots: Boolean?, spotsSize: Int?, usersThatCheckedIn: Int?, usersWithTeam: Int?) {

        if (isAllSpots!!) {
            textViewNumLocations.visibility = View.VISIBLE
            textViewNumLocations.text = getString(R.string.num_locations, spotsSize.toString())
        } else textViewNumLocations.visibility = View.GONE

        textViewUsersThatCheckedIn.text = usersThatCheckedIn.toString()
        textViewUsersWithTeam.text = usersWithTeam.toString()

    }

    fun updateDashboardData(clubId: Int, spotIdOrAllSpots: String, spotsSize: Int) {

        setDashboardData(clubId, spotIdOrAllSpots, spotsSize)

        if (!::user.isInitialized) user = SessionManager.getCurrentSession()!!

        RJRetrofit.getInstance().create(HomeInterface::class.java).getSpotStatus(user!!.token, clubId, spotIdOrAllSpots)
                .enqueue(object : Callback<SpotStatusResponse> {

                    override fun onResponse(call: Call<SpotStatusResponse>, response: Response<SpotStatusResponse>) {

                        handleSpotStatusResponse(response, spotIdOrAllSpots == HomeActivity.ALL_SPOTS, spotsSize)

                    }

                    override fun onFailure(call: Call<SpotStatusResponse>, t: Throwable) {

                    }

                })

    }

    private fun handleSpotStatusResponse(response: Response<SpotStatusResponse>, isAllSpots: Boolean?, spotsSize: Int) {

        when (response.code()) {

            200 -> {
                refreshData(isAllSpots, spotsSize, response.body()?.spotStatus!!.totalUsersCheckedIn, response.body()?.spotStatus!!.totalUsersWithTeam)

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