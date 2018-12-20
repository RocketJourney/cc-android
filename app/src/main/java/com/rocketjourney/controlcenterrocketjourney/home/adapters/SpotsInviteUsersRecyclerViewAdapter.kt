package com.rocketjourney.controlcenterrocketjourney.home.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.home.HomeActivity
import com.rocketjourney.controlcenterrocketjourney.home.interfaces.OnEnableNextButtonInterface
import com.rocketjourney.controlcenterrocketjourney.home.objects.SpotStructure
import kotlinx.android.synthetic.main.item_invite_spot_check_box.view.*

class SpotsInviteUsersRecyclerViewAdapter(private val spots: ArrayList<SpotStructure>, val context: Context, val enableNextButton: OnEnableNextButtonInterface) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_DESCRIPTION = 0
        private const val TYPE_ALL_SPOTS = 1
        private const val TYPE_SPOT = 2
    }

    private val views = ArrayList<RecyclerView.ViewHolder>()

    private val allSpotsListener = object : CompoundButton.OnCheckedChangeListener {

        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {

            for (spot in spots) {
                spot.checked = isChecked
            }

            notifyDataSetChanged()

        }
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
                ViewHolderSpot(inflater.inflate(R.layout.item_invite_spot_check_box, p0, false))
            }
        }
    }

    override fun onBindViewHolder(view: RecyclerView.ViewHolder, p1: Int) {

        if (view is ViewHolderAllSpots) {

            view.checkBoxSpot.text = context.resources.getString(R.string.all_locations)

            view.checkBoxSpot.setOnCheckedChangeListener(allSpotsListener)

        } else if (view is ViewHolderSpot) {

            view.checkBoxSpot.text = spots[p1].spot?.branchName
            view.checkBoxSpot.isChecked = spots[p1].checked

            view.checkBoxSpot.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {

                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                    spots[p1].checked = isChecked

                    if (!isChecked && spots[1].itemType == SpotStructure.SpotItemType.ALL_SPOTS && spots[1].checked) {

                        spots[1].checked = false
                        val allSpotsView = views[1] as ViewHolderAllSpots
                        allSpotsView.checkBoxSpot.setOnCheckedChangeListener(null)
                        allSpotsView.checkBoxSpot.isChecked = false
                        allSpotsView.checkBoxSpot.setOnCheckedChangeListener(allSpotsListener)

                    }

                    if (isChecked) {
                        enableNextButton.enableNextButton(isChecked)
                    } else {
                        enableNextButton.enableNextButton(isSomeSpotEnabled())
                    }

                }
            })

        }

        views.add(view)

    }

    private fun isSomeSpotEnabled(): Boolean {
        spots.forEach {
            if (it.checked) return true
        }
        return false
    }

    fun getSelectedSpotsId(): ArrayList<Int> {

        val idsList = ArrayList<Int>()

        spots.forEach {

            if (it.itemType == SpotStructure.SpotItemType.SPOT && it.checked)
                idsList.add(it.spot!!.id)

        }

        return idsList
    }

    fun getPermission(): String {

        return if (spots[1].itemType == SpotStructure.SpotItemType.ALL_SPOTS && spots[1].checked) {

            HomeActivity.ALL_SPOTS

        } else {

            HomeActivity.SOME_SPOTS

        }

    }

    override fun getItemCount(): Int {
        return spots.size
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
        val checkBoxSpot = itemView.checkBoxSpot
    }

    class ViewHolderAllSpots(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBoxSpot = itemView.checkBoxSpot
    }

    class ViewHolderDescription(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}