package com.firebase.fcm.callback

interface OnResultCallback<T> {

    fun onResult(t: T)

}