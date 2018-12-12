package com.rocketjourney.controlcenterrocketjourney.structure.notifications

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebasePushNotifications : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage?) {
        val data = message?.data.toString()

        if(data == ""){

        }
    }
}