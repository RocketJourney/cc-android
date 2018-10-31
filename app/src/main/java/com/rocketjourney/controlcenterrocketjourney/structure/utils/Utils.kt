package com.rocketjourney.controlcenterrocketjourney.structure.network.utils

import android.graphics.Typeface
import android.support.v7.app.ActionBar
import android.widget.Toast
import com.rocketjourney.controlcenterrocketjourney.structure.RocketJourneyApp

class Utils {

    companion object {

        val EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

        /**
         * String utils
         */

        fun isValidEmail(email: String): Boolean {
            return email.matches(EMAIL_PATTERN.toRegex())
        }

        /**
         * Toolbar utils
         */

        fun hideToolbarTitle(actionBar: ActionBar?) {
            actionBar?.setDisplayShowTitleEnabled(false)
        }

        /**
         * Toast utils
         */

        fun showShortToast(text: String) {
            Toast.makeText(RocketJourneyApp.context, text, Toast.LENGTH_SHORT).show()
        }

        fun showLongToast(text: String) {
            Toast.makeText(RocketJourneyApp.context, text, Toast.LENGTH_LONG).show()
        }

        /**
         * Font utils section
         */

        fun montserratBlack(): Typeface {
            return Typeface.createFromAsset(RocketJourneyApp.context.assets, "fonts/Montserrat-Black.ttf")
        }

        fun montserratBold(): Typeface {
            return Typeface.createFromAsset(RocketJourneyApp.context.assets, "fonts/Montserrat-Bold.ttf")
        }

        fun montserratLight(): Typeface {
            return Typeface.createFromAsset(RocketJourneyApp.context.assets, "fonts/Montserrat-Light.ttf")
        }

        fun montserratRegular(): Typeface {
            return Typeface.createFromAsset(RocketJourneyApp.context.assets, "fonts/Montserrat-Regular.ttf")
        }

    }
}