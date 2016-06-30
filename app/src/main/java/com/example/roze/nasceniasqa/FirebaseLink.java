package com.example.roze.nasceniasqa;

import com.firebase.client.Firebase;

/**
 * Created by Roze on 6/18/2016.
 */
public class FirebaseLink extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
