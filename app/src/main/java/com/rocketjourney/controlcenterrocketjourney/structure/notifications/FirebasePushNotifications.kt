package com.rocketjourney.controlcenterrocketjourney.structure.notifications

import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.rocketjourney.controlcenterrocketjourney.LaunchActivity
import com.rocketjourney.controlcenterrocketjourney.R
import com.rocketjourney.controlcenterrocketjourney.structure.RJControlCenter
import com.rocketjourney.controlcenterrocketjourney.structure.managers.NotificationManager
import com.rocketjourney.controlcenterrocketjourney.structure.objects.PushNotificationsData

class FirebasePushNotifications : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage?) {
        val data = Gson().fromJson(message?.data.toString(), PushNotificationsData::class.java)

        showNotification(data)
    }

    private fun showNotification(data: PushNotificationsData) {

        val typeNotif = data.payload.type

        val title = NotificationManager.rjControlCenter
        var description = ""

        var pendingIntent = Intent(RJControlCenter.context, LaunchActivity::class.java)

        if (NotificationManager.NEW_CHECKIN_ON_SPOT_FOR_CC == typeNotif) {
            description = getString(R.string.user_checked_in_at, data.message.locArgs[0], data.message.locArgs[1])
        }

        NotificationManager.sendNotification(title, description, pendingIntent)
    }
}