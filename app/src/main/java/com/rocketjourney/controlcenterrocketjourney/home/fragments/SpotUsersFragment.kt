package com.rocketjourney.controlcenterrocketjourney.home.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.home.adapters.SpotUsersReryclerViewAdapter
import com.rocketjourney.controlcenterrocketjourney.home.interfaces.HomeInterface
import com.rocketjourney.controlcenterrocketjourney.home.objects.UserSpotData
import com.rocketjourney.controlcenterrocketjourney.home.responses.SpotUsersResponse
import com.rocketjourney.controlcenterrocketjourney.structure.managers.SessionManager
import com.rocketjourney.controlcenterrocketjourney.structure.network.RJRetrofit
import com.rocketjourney.controlcenterrocketjourney.structure.network.utils.Utils
import com.rocketjourney.controlcenterrocketjourney.structure.objects.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SpotUsersFragment : Fragment() {

    companion object {

        private const val ARG_CLUB_ID = "ARG_CLUB_ID"
        private const val ARG_SPOT_ID_OR_ALL_SPOTS = "ARG_SPOT_ID_OR_ALL_SPOTS"

        fun newInstance(clubId: Int, spotIdOrAllSpots: String): SpotUsersFragment {

            val fragment = SpotUsersFragment()

            val args = Bundle()
            args.putInt(ARG_CLUB_ID, clubId)
            args.putString(ARG_SPOT_ID_OR_ALL_SPOTS, spotIdOrAllSpots)

            fragment.arguments = args

            return fragment
        }

    }

    var user: User? = null

    var clubId: Int? = 0
    var spotIdOrAllSpots: String? = ""

    lateinit var users: ArrayList<UserSpotData>

    lateinit var adapter: SpotUsersReryclerViewAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_spot_users, container, false)

        user = SessionManager.getCurrentSession()

        clubId = arguments?.getInt(ARG_CLUB_ID)
        spotIdOrAllSpots = arguments?.getString(ARG_SPOT_ID_OR_ALL_SPOTS)

        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewSpotUsers)
        recyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        updateUsersList(clubId, spotIdOrAllSpots)

        return view
    }

    fun setClubData(clubId: Int, spotIdOrAllSpots: String) {
        val args = Bundle()
        args.putInt(ARG_CLUB_ID, clubId)
        args.putString(ARG_SPOT_ID_OR_ALL_SPOTS, spotIdOrAllSpots)
        arguments = args
    }

    fun updateUsersList(clubId: Int?, spotIdOrAllSpots: String?) {

        //ward remover el 1 del parametro de page
        RJRetrofit.getInstance().create(HomeInterface::class.java).getSpotUsers(user!!.token, clubId, spotIdOrAllSpots, 1)
                .enqueue(object : Callback<SpotUsersResponse> {

                    override fun onResponse(call: Call<SpotUsersResponse>, response: Response<SpotUsersResponse>) {

                        try {

                            handleUsersSpotResponse(response)

                        } catch (e: KotlinNullPointerException) {
                            e.printStackTrace()
                        }

                    }

                    override fun onFailure(call: Call<SpotUsersResponse>, t: Throwable) {
                        //ward
                    }

                })

    }

    private fun handleUsersSpotResponse(response: Response<SpotUsersResponse>) {

        when (response.code()) {

            200 -> {

                if (!::users.isInitialized || !::adapter.isInitialized) {

                    users = response.body()?.data!!.users
                    adapter = SpotUsersReryclerViewAdapter(users, activity!!.applicationContext)

                    recyclerView.adapter = adapter

                } else {

                    users = response.body()?.data!!.users
                    adapter.setNewUsers(users)
                    recyclerView.adapter = adapter

                }

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