package firebase.clickapps.com.firebase;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.firebase.fcm.observer.LocalFirebaseLiveData;
import com.google.firebase.messaging.RemoteMessage;

public class MainActivity extends AppCompatActivity {
    String key = "AAAA3Y0DNlI:APA91bFG6Rr6-aRlSTfX60ONkNi1uqsrgqdSbCWKEumWnFBbh0YmPSah8BJZpJamQGjzUEIKxTr1ZB5kqTuSIVni2FOEnNqTjVOx5SeXPzVDbR61LjuiGtazD67DTs2Oma-AwpNJeR_C";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.subsribe)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] topics = new String[]{"global_en"};
                        //startActivity(new Intent("android.settings.CAST_SETTINGS"));
                    }
                });
        new LocalFirebaseLiveData(this)
                .observe(this, new Observer<RemoteMessage>() {
                    @Override
                    public void onChanged(@Nullable RemoteMessage remoteMessage) {
                        Log.i(getLocalClassName(), "remote = " + remoteMessage.getFrom());
                    }
                });
    }
}
