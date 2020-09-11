package com.example.taylorshop.Models;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}


