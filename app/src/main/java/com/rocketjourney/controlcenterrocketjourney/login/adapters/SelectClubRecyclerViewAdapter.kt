package com.rocketjourney.controlcenterrocketjourney.login.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.login.interfaces.ClickedClub
import com.rocketjourney.controlcenterrocketjourney.structure.network.utils.Utils
import com.rocketjourney.controlcenterrocketjourney.structure.objects.Club
import com.rocketjourney.controlcenterrocketjourney.structure.utils.RoundCornersTransform
import com.squareup.picasso.Picasso
import io.realm.RealmList
import kotlinx.android.synthetic.main.item_club.view.*

class SelectClubRecyclerViewAdapter(val clubs: RealmList<Club>, val context: Context, val clickedClub: ClickedClub) : RecyclerView.Adapter<SelectClubRecyclerViewAdapter.ViewHolderClub>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolderClub {
        return ViewHolderClub(LayoutInflater.from(context).inflate(R.layout.item_club, p0, false))
    }

    override fun getItemCount(): Int {
        return clubs.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolderClub, p1: Int) {

        val club = clubs[p1]

        viewHolder.textViewName.text = club?.name
        Picasso.get().load(club?.logo).fit().centerCrop().transform(RoundCornersTransform(Utils.ROUND_CORNERS_CLUBS_RECYCLER_VIEW, 0)).into(viewHolder.imageViewLogo)

        viewHolder.linearLayoutContainer.setOnClickListener {
            clickedClub.clickedClub(club)
        }
    }

    class ViewHolderClub(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val linearLayoutContainer = itemView.linearLayoutContainer
        val imageViewLogo = itemView.imageViewGymLogo
        val textViewName = itemView.texteViewGymName
    }
}