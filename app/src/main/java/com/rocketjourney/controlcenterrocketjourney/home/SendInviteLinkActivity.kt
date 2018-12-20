package com.rocketjourney.controlcenterrocketjourney.home

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.View
import android.widget.Button
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.structure.network.utils.Utils
import kotlinx.android.synthetic.main.activity_send_invite_link.*
import kotlinx.android.synthetic.main.component_toolbar_title.view.*

class SendInviteLinkActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var link: String
    lateinit var buttonDone: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_invite_link)

        link = intent.getStringExtra(SelectSpotsInviteActivity.EXTRA_INVITE_LINK)

        setSupportActionBar(componentToolbar.toolbar)
        Utils.hideToolbarTitle(supportActionBar)

        componentToolbar.textViewToolbarTitle.text = getString(R.string.send_link)
        componentToolbar.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_yellow_24dp)
        componentToolbar.toolbar.setNavigationOnClickListener { finish() }

        linearLayoutSendLinkToFriend.setOnClickListener(this)
        linearLayoutCopyLinkToClipboard.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when (v) {

            linearLayoutSendLinkToFriend -> {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.join_cc_to_manage_all_things_related_to_rj, link))
                sendIntent.type = "text/plain"
                startActivity(Intent.createChooser(sendIntent, getString(R.string.send_link_to_colleague_s_via)))
            }

            linearLayoutCopyLinkToClipboard -> {
                Utils.copyToClipboard(applicationContext, link, getString(R.string.link_copied))
            }

            buttonDone -> {
                setResult(Activity.RESULT_OK)
                finish()
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_next, menu)
        var menuItem = menu?.findItem(R.id.menuNext)

        buttonDone = menuItem?.actionView as Button

        buttonDone.setTextColor(getColor(R.color.yellow_ff))
        buttonDone.setText(getString(R.string.done_terminar))
        buttonDone.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        buttonDone.typeface = Utils.montserratBold()
        buttonDone.setBackgroundColor(Color.TRANSPARENT)
        buttonDone.setOnClickListener(this)

        return true
    }
}
