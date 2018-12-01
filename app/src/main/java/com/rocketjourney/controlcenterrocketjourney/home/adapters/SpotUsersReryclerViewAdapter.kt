package com.rocketjourney.controlcenterrocketjourney.home.adapters

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.home.objects.UserSpotData
import com.rocketjourney.controlcenterrocketjourney.structure.views.CircleTransform
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user_club.view.*

class SpotUsersReryclerViewAdapter(var users: ArrayList<UserSpotData>, val context: Context) : RecyclerView.Adapter<SpotUsersReryclerViewAdapter.ViewHolderUsersOnClub>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolderUsersOnClub {
        return ViewHolderUsersOnClub(LayoutInflater.from(context).inflate(R.layout.item_user_club, p0, false))
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(view: ViewHolderUsersOnClub, p1: Int) {

        val user = users[p1]
        Picasso.get().load(user.profilePic)
                .fit().centerCrop()
                .transform(CircleTransform())
                .into(view.imageViewUserImage)

        view.textViewUserName.text = "${user.firstName} ${user.lastName}"
        view.textViewUserStreak.text = user.streak.toString()
    }

    fun addUsers(users: ArrayList<UserSpotData>) {
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    fun setNewUsers(users: ArrayList<UserSpotData>) {
        this.users = users
        notifyDataSetChanged()
    }

    class ViewHolderUsersOnClub(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewUserImage = itemView.imageViewUserImage
        val textViewUserName = itemView.textViewUserName
        val textViewUserStreak = itemView.textViewUserStreak
    }
}