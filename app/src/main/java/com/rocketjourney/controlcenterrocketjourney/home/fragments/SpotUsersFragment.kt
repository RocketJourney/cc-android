package com.rocketjourney.controlcenterrocketjourney.home.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.home.adapters.SpotUsersRecyclerViewAdapter
import com.rocketjourney.controlcenterrocketjourney.home.adapters.SpotUsersRecyclerViewAdapter.Companion.NUM_ITEMS_PER_PAGE
import com.rocketjourney.controlcenterrocketjourney.home.interfaces.HomeInterface
import com.rocketjourney.controlcenterrocketjourney.home.objects.UserSpotData
import com.rocketjourney.controlcenterrocketjourney.home.responses.SpotUsersResponse
import com.rocketjourney.controlcenterrocketjourney.structure.managers.SessionManager
import com.rocketjourney.controlcenterrocketjourney.structure.network.RJRetrofit
import com.rocketjourney.controlcenterrocketjourney.structure.objects.User
import kotlinx.android.synthetic.main.item_club.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SpotUsersFragment : Fragment() {

    companion object {

        private const val ARG_CLUB_ID = "ARG_CLUB_ID"
        private const val ARG_SPOT_ID_OR_ALL_SPOTS = "ARG_SPOT_ID_OR_ALL_SPOTS"

        const val ADD_USERS = 0
        const val SET_USERS = 1

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

    var currentPage = 1
    var isRequestInQueue: Boolean = false
    var noMoreRequests: Boolean = false
    var clubId: Int? = 0
    var spotIdOrAllSpots: String? = ""

    lateinit var users: ArrayList<UserSpotData?>

    lateinit var adapter: SpotUsersRecyclerViewAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager

    val onScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

            var lastItem = layoutManager.findLastVisibleItemPosition()
            var itemCount = adapter.itemCount - 1

            if (!noMoreRequests && !isRequestInQueue && lastItem == itemCount) {
                updateUsersList(clubId, spotIdOrAllSpots, ADD_USERS)
            }

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_spot_users, container, false)

        if (user == null)
            user = SessionManager.getCurrentSession()

        resetValuesForRequests()

        clubId = arguments?.getInt(ARG_CLUB_ID)
        spotIdOrAllSpots = arguments?.getString(ARG_SPOT_ID_OR_ALL_SPOTS)

        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewSpotUsers)
        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.addOnScrollListener(onScrollListener)

        updateUsersList(clubId, spotIdOrAllSpots, SET_USERS)

        return view
    }

    fun resetValuesForRequests(){
        currentPage = 1
        isRequestInQueue = false
    }

    private fun setSpotUsersData(clubId: Int, spotIdOrAllSpots: String) {
        val args = Bundle()
        args.putInt(ARG_CLUB_ID, clubId)
        args.putString(ARG_SPOT_ID_OR_ALL_SPOTS, spotIdOrAllSpots)
        arguments = args
    }

    fun updateUsersList(clubId: Int?, spotIdOrAllSpots: String?, addOrSet: Int) {

        setSpotUsersData(clubId!!, spotIdOrAllSpots!!)

        if (user == null)
            user = SessionManager.getCurrentSession()

        isRequestInQueue = true
        RJRetrofit.getInstance().create(HomeInterface::class.java).getSpotUsers(user!!.token, clubId, spotIdOrAllSpots, currentPage)
                .enqueue(object : Callback<SpotUsersResponse> {

                    override fun onResponse(call: Call<SpotUsersResponse>, response: Response<SpotUsersResponse>) {

                        try {

                            isRequestInQueue = false
                            handleUsersSpotResponse(response, addOrSet)

                        } catch (e: KotlinNullPointerException) {
                            e.printStackTrace()
                        }

                    }

                    override fun onFailure(call: Call<SpotUsersResponse>, t: Throwable) {
                        //ward
                        isRequestInQueue = false
                    }

                })

    }

    private fun handleUsersSpotResponse(response: Response<SpotUsersResponse>, addOrSet: Int) {

        when (response.code()) {

            200 -> {

                currentPage++

                if (!::users.isInitialized || !::adapter.isInitialized) {

                    users = response.body()?.data!!.users

                    if (users.size == NUM_ITEMS_PER_PAGE) users!!.add(null)

                    adapter = SpotUsersRecyclerViewAdapter(users, activity!!.applicationContext)

                } else {

                    users = response.body()?.data!!.users

                    if (addOrSet == ADD_USERS)
                        adapter.addUsers(users)
                    else if (addOrSet == SET_USERS)
                        adapter.setNewUsers(users)
                }

                noMoreRequests = users.size < SpotUsersRecyclerViewAdapter.NUM_ITEMS_PER_PAGE

                recyclerView.adapter = adapter

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