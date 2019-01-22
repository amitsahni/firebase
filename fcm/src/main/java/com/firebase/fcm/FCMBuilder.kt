package com.firebase.fcm

import android.content.Context
import android.support.annotation.NonNull
import android.util.Log
import com.firebase.fcm.callback.OnResultCallback
import com.google.firebase.iid.FirebaseInstanceId
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class FCMBuilder(@NonNull val context: Context) {


    fun subScribe(vararg topics: String): SubscribeBuilder {
        return SubscribeBuilder(*topics)
    }

    fun subScribed(key: String, result: OnResultCallback<String>) {
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            if (it.isSuccessful) {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("https://iid.googleapis.com/iid/info/${it.result?.token}?details=true")
                    .addHeader("Authorization", "Bearer $key")
                    .build()

                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        result.onResult("")
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) {
                            val body = response.body()?.string()
                            Log.i(this@FCMBuilder.javaClass.simpleName, "Response = $body")
                            val jsonObject = JSONObject(body)
                            val rel = jsonObject.getJSONObject("rel")
                            val topic = rel.getJSONObject("topics")
                            val stringBuilder = StringBuilder()
                            for (key in topic.keys()) {
                                Log.i(this@FCMBuilder.javaClass.simpleName, "key = $key")
                                stringBuilder.append(key)
                                stringBuilder.append(",")
                            }
                            result.onResult(stringBuilder.toString().substring(0, stringBuilder.length - 1))
                        } else {
                            result.onResult("")
                        }
                    }

                })
            } else {
                result.onResult("")
            }
        }
    }

}