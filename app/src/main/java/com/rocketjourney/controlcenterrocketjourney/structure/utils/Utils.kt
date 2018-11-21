package com.rocketjourney.controlcenterrocketjourney.structure.network.utils

import android.content.Context
import android.graphics.Typeface
import android.preference.PreferenceManager
import android.support.v7.app.ActionBar
import android.widget.Toast
import com.rocketjourney.controlcenterrocketjourney.structure.RocketJourneyApp

class Utils {

    companion object {

        val EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

        val SHARED_PREFERENCES_HAS_SESSION = "SHARED_PREFERENCES_HAS_SESSION"

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
         * Shared Preferences!
         */

        fun saveStringToPrefs(context: Context, key: String, value: String) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = prefs.edit()
            editor.putString(key, value)
            editor.commit()
        }

        fun getStringFromPrefs(context: Context, key: String, defaultValue: String): String? {
            val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
            try {
                return sharedPrefs.getString(key, defaultValue)
            } catch (e: Exception) {
                e.printStackTrace()
                return defaultValue
            }

        }

        fun saveBooleanToPrefs(context: Context, key: String, value: Boolean) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = prefs.edit()
            editor.putBoolean(key, value)
            editor.commit()
        }

        fun getBooleanFromPrefs(context: Context, key: String, defaultValue: Boolean): Boolean {
            val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
            try {
                return sharedPrefs.getBoolean(key, defaultValue)
            } catch (e: Exception) {
                e.printStackTrace()
                return defaultValue
            }

        }

        fun saveIntegerToPrefs(context: Context, key: String, value: Int) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = prefs.edit()
            editor.putInt(key, value)
            editor.commit()
        }

        fun getIntegerFromPrefs(context: Context, key: String, defaultValue: Int): Int {
            val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
            try {
                return sharedPrefs.getInt(key, defaultValue)
            } catch (e: Exception) {
                e.printStackTrace()
                return defaultValue
            }

        }

        fun cleanSharedPreferences(context: Context) {
            val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
            sharedPrefs.edit().clear().commit()
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