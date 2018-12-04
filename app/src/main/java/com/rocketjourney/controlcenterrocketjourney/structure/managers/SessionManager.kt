package com.rocketjourney.controlcenterrocketjourney.structure.managers

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.rocketjourney.controlcenterrocketjourney.login.FirstScreenActivity
import com.rocketjourney.controlcenterrocketjourney.login.objects.SignUpResponseData
import com.rocketjourney.controlcenterrocketjourney.structure.objects.User
import io.realm.Realm

class SessionManager {

    companion object {
        fun createSession(email: String, response: SignUpResponseData): User {

            var user = User()
            user.id = response.userId
            user.email = email
            user.firstName = ""//ward
            user.lastName = ""//ward
            user.token = response.jwt
            user.currentClub = response.club
            user.clubs.addAll(response.clubs)

            val realm = Realm.getDefaultInstance()
            realm.executeTransaction {
                user = it.copyToRealm(user)
            }

            return user
        }

        fun getCurrentSession(): User? {
            val realm = Realm.getDefaultInstance()

            val user = realm.where(User::class.java).findFirst()
            return user
        }

        fun closeSession(context: Context) {
            val realm = Realm.getDefaultInstance()
            realm.executeTransaction { realm.deleteAll() }

            val intent = Intent(context, FirstScreenActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}