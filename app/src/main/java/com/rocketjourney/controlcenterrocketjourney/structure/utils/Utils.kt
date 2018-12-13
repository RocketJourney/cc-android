package com.rocketjourney.controlcenterrocketjourney.structure.network.utils

import android.content.Context
import android.graphics.Typeface
import android.preference.PreferenceManager
import android.support.v7.app.ActionBar
import android.support.v7.app.AlertDialog
import android.util.DisplayMetrics
import android.util.TypedValue
import android.widget.TextView
import android.widget.Toast
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.structure.RJControlCenter
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.view.View
import android.view.inputmethod.InputMethodManager


class Utils {

    companion object {

        private const val EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

        const val SHARED_PREFERENCES_PUSH_NOTIFICATIONS_ARE_REGISTERED = "SHARED_PREFERENCES_PUSH_NOTIFICATIONS_ARE_REGISTERED"

        const val PUSH_NOTIFICATIONS_COUNT = "PUSH_NOTIFICATIONS_COUNT"

        const val ROUND_CORNERS_CLUBS_RECYCLER_VIEW = 24

        /**
         * String utils
         */

        fun isValidEmail(email: String): Boolean {
            return email.matches(EMAIL_PATTERN.toRegex())
        }

        fun copyToClipboard(context: Context, textToCopy: String, textToShowInToast: String) {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(textToShowInToast, textToCopy)
            clipboard.primaryClip = clip

            Toast.makeText(context, textToShowInToast, Toast.LENGTH_SHORT).show()
        }

        /**
         * Converter utils
         */
        fun dpToPx(context: Context?, dp: Int): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context?.resources?.displayMetrics).toInt()
        }

        fun spToPixels(context: Context?, sp: Int): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp.toFloat(), context?.resources?.displayMetrics).toInt()
        }

        fun pxToDp(px: Float, context: Context?): Float {
            val resources = context?.resources
            val metrics = resources?.displayMetrics
            return px / (metrics?.densityDpi!!.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
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
            Toast.makeText(RJControlCenter.context, text, Toast.LENGTH_SHORT).show()
        }

        fun showLongToast(text: String) {
            Toast.makeText(RJControlCenter.context, text, Toast.LENGTH_LONG).show()
        }

        /**
         * Keyboard utils
         */
        fun hideKeyboard(activity: Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view = activity.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view!!.windowToken, 0)
        }

        /**
         * AlertDialog utils
         */

        fun giveDesignToAlertDialog(alerDialog: AlertDialog, context: Context) {

            alerDialog.setCancelable(false)
            alerDialog.getButton(AlertDialog.BUTTON_POSITIVE).typeface = montserratBold()
            alerDialog.getButton(AlertDialog.BUTTON_POSITIVE).textSize = 17f
            alerDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.resources.getColor(R.color.yellow_ff))

            alerDialog.getButton(AlertDialog.BUTTON_NEGATIVE).typeface = montserratBold()
            alerDialog.getButton(AlertDialog.BUTTON_NEGATIVE).textSize = 17f
            alerDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.resources.getColor(R.color.red_ff33))

            val textViewTitleDialog = alerDialog.findViewById<TextView>(android.support.v7.appcompat.R.id.alertTitle)
            val textViewMessageDialog = alerDialog.findViewById<TextView>(android.R.id.message)

            textViewTitleDialog?.typeface = Utils.montserratRegular()
            textViewMessageDialog?.typeface = Utils.montserratRegular()

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
            return Typeface.createFromAsset(RJControlCenter.context.assets, "fonts/Montserrat-Black.ttf")
        }

        fun montserratBold(): Typeface {
            return Typeface.createFromAsset(RJControlCenter.context.assets, "fonts/Montserrat-Bold.ttf")
        }

        fun montserratLight(): Typeface {
            return Typeface.createFromAsset(RJControlCenter.context.assets, "fonts/Montserrat-Light.ttf")
        }

        fun montserratRegular(): Typeface {
            return Typeface.createFromAsset(RJControlCenter.context.assets, "fonts/Montserrat-Regular.ttf")
        }

    }
}