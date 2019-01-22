package com.firebase.fcm

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class FCMConfig(context: Context) {

    init {
        FirebaseApp.initializeApp(context.applicationContext)
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
    }
}