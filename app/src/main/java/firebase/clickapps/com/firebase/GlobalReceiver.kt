package firebase.clickapps.com.firebase

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.RemoteMessage

class GlobalReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        Log.i(javaClass.simpleName, "intent = $intent")
        val remoteMessage = intent?.getParcelableExtra<RemoteMessage>("value")
        Log.i(javaClass.simpleName, "remote = " + remoteMessage?.from!!)
    }
}