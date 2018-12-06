package com.rocketjourney.controlcenterrocketjourney.home.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.home.objects.SpotStructure
import kotlinx.android.synthetic.main.item_invite_spot_check_box.view.*

class SpotsInviteUsersRecyclerViewAdapter(private val spots: ArrayList<SpotStructure>, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_DESCRIPTION = 0
        private const val TYPE_ALL_SPOTS = 1
        private const val TYPE_SPOT = 2
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)

        return when (p1) {

            TYPE_DESCRIPTION -> {
                ViewHolderDescription(inflater.inflate(R.layout.item_spots_invite_description, p0, false))
            }

            TYPE_ALL_SPOTS -> {
                ViewHolderAllSpots(inflater.inflate(R.layout.item_invite_all_spot_check_box, p0, false))
            }

            TYPE_SPOT -> {
                ViewHolderSpot(inflater.inflate(R.layout.item_invite_spot_check_box, p0, false))
            }

            else -> {
                return View()
            }
        }
    }

    override fun getItemCount(): Int {
        return spots.size
    }

    override fun onBindViewHolder(p0: ViewHolderSpot, p1: Int) {

    }

    override fun getItemViewType(position: Int): Int {

        return when (spots[position].itemType) {

            SpotStructure.SpotItemType.DESCRIPTION -> {
                TYPE_DESCRIPTION
            }

            SpotStructure.SpotItemType.ALL_SPOTS -> {
                TYPE_ALL_SPOTS
            }

            SpotStructure.SpotItemType.SPOT -> {
                TYPE_SPOT
            }
        }

    }

    class ViewHolderSpot(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val radioButtonSpot = itemView.radioButtonSpot
    }

    class ViewHolderAllSpots(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val radioButtonSpot = itemView.radioButtonSpot
    }

    class ViewHolderDescription(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}