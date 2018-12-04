package com.rocketjourney.controlcenterrocketjourney.home.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.home.objects.UserSpotData
import com.rocketjourney.controlcenterrocketjourney.structure.views.CircleTransform
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_loading.view.*
import kotlinx.android.synthetic.main.item_user_club.view.*

class SpotUsersRecyclerViewAdapter(var users: ArrayList<UserSpotData?>?, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_PROGRESS = 0
        private const val TYPE_ITEM = 1

        const val NUM_ITEMS_PER_PAGE = 30
    }

    override fun onBindViewHolder(view: RecyclerView.ViewHolder, pos: Int) {

        if (view is ViewHolderUsersOnClub) {

            val user = users!![pos]
            Picasso.get().load(user?.profilePic)
                    .fit().centerCrop()
                    .transform(CircleTransform())
                    .into(view.imageViewUserImage)

            view.textViewUserName.text = "${user?.firstName} ${user?.lastName}".replace("null", "")
            view.textViewUserStreak.text = user?.streak.toString()

        }

    }


    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(context)

        return if (viewType == TYPE_ITEM) {
            ViewHolderUsersOnClub(inflater.inflate(R.layout.item_user_club, p0, false))
        } else {
            ViewHolderProgressBar(inflater.inflate(R.layout.item_loading, p0, false))
        }
    }

    override fun getItemCount(): Int {
        return users!!.size
    }

    override fun getItemViewType(position: Int): Int {

        return if (users!![position] != null) {
            TYPE_ITEM
        } else {
            TYPE_PROGRESS
        }

    }

    fun addUsers(newUsers: ArrayList<UserSpotData?>) {

        if (users!![users!!.size - 1] == null) users!!.removeAt(users!!.size - 1)

        users!!.addAll(newUsers)

        if (newUsers.size == NUM_ITEMS_PER_PAGE) users!!.add(null)

        notifyDataSetChanged()
    }

    fun setNewUsers(newUsers: ArrayList<UserSpotData?>) {
        this.users = newUsers

        if (newUsers.size == NUM_ITEMS_PER_PAGE) users!!.add(null)

        notifyDataSetChanged()
    }

    class ViewHolderUsersOnClub(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewUserImage = itemView.imageViewUserImage
        val textViewUserName = itemView.textViewUserName
        val textViewUserStreak = itemView.textViewUserStreak
    }

    class ViewHolderProgressBar(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar = itemView.progressBar
    }
}