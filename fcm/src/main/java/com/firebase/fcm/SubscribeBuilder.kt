package com.firebase.fcm

import android.content.ContentValues
import android.util.Log
import com.firebase.fcm.callback.OnResultCallback
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import java.util.*

class SubscribeBuilder {


    private var topic: Array<out String> = emptyArray()
    private var l: OnResultCallback<Boolean>? = null

    constructor(vararg topic: String) {
        this.topic = topic
    }

    fun listener(l: OnResultCallback<Boolean>): SubscribeBuilder {
        this.l = l
        return this
    }

    fun build() {
        for (channel in topic) {
            FirebaseMessaging.getInstance().subscribeToTopic(channel)
                .addOnCompleteListener {
                    var msg = "subScribe"
                    if (!it.isSuccessful) {
                        msg = "error in subScribing"
                    }
                    Log.d(ContentValues.TAG, msg)
                    l?.onResult(it.isSuccessful)
                }
        }
    }

}