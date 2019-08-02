package firebase.clickapps.com.firebase;

import android.app.Application;
import com.firebase.fcm.FCMConfig;

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new FCMConfig(this);
    }
}
