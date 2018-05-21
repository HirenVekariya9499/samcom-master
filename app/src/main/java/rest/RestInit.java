package rest;

import android.app.Application;

/**
 * Created by Arjun on 21-05-2018.
 */

public class RestInit extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        RestClient.init(this);
    }
}
