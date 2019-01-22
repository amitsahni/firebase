package com.firebase.fcm.observer

import android.arch.lifecycle.MutableLiveData
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.google.firebase.messaging.RemoteMessage
import android.R.attr.action


class LocalFirebaseLiveData(ctx: Context) : MutableLiveData<RemoteMessage>() {


    private var context: Context? = null
    private var action = ""

    init {
        context = ctx
        action = ctx.packageName + ".firebase.local"
    }

    override fun onActive() {
        LocalBroadcastManager.getInstance(context)
            .registerReceiver(receiver, IntentFilter(action))
        super.onActive()
    }

    override fun onInactive() {
        LocalBroadcastManager.getInstance(context)
            .unregisterReceiver(receiver)
        super.onInactive()
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val remoteMessage = intent.getParcelableExtra<RemoteMessage>("value")
            postValue(remoteMessage)
        }
    }
}