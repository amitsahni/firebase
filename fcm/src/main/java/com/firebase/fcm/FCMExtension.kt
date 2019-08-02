package com.firebase.fcm

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

fun Array<String>.subscribe() {
    for (channel in this) {
        FirebaseMessaging.getInstance().subscribeToTopic(channel)
            .addOnCompleteListener {
                var msg = "subScribe"
                if (!it.isSuccessful) {
                    msg = "error in subScribing"
                    Log.e("Error", msg)
                } else {
                    msg = "$channel subscribed"
                    Log.i("Success", msg)
                }

            }
    }
}

fun String.subscribedTopic(f: (String) -> Unit) {
    FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
        if (it.isSuccessful) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://iid.googleapis.com/iid/info/${it.result?.token}?details=true")
                .addHeader("Authorization", "Bearer $this")
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.i("onFailure", e.message)
                    f("")
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val body = response.body()?.string()
                        Log.i("onResponse", body)
                        val jsonObject = JSONObject(body)
                        val rel = jsonObject.getJSONObject("rel")
                        val topic = rel.getJSONObject("topics")
                        val stringBuilder = StringBuilder()
                        for (key in topic.keys()) {
                            stringBuilder.append(key)
                            stringBuilder.append(",")
                        }
                        val topics = stringBuilder.toString().substring(0, stringBuilder.length - 1)
                        Log.i("onResponse", "Topics = $topics")
                        f(topics)
                    } else {
                        Log.i("onResponse", "Error in getting topics")
                        f("")
                    }
                }

            })
        } else {
            f("")
        }
    }
}