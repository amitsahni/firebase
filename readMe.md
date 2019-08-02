#### FCM PUSH Notification

#### Subscribe for topics

```aidl
new FCMBuilder(getApplicationContext())
              .subScribe("hello")
              .build();
```

#### List of subscribed topis

```aidl
new FCMBuilder(getApplicationContext())
              .subScribed(key, new onResultCallback<String>() {
                   @Override
                   public void onResult(String s) {
                         Log.i(getLocalClassName(), "channels = " + s);
                   }
               });
```

#### Get Push on broadcast receiver

Intent Filer - packageName+".push" 
eg if you package name is `com.android.test` then intent filter for push will be `com.android.test.push`

```java
LocalBroadcastManager.getInstance(this)
                .registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        Log.i(getPackageName(), "intent = " + intent);
                        RemoteMessage remoteMessage = intent.getParcelableExtra("value");

                    }
                }, new IntentFilter(getApplication().getPackageName() + ".push"));
```