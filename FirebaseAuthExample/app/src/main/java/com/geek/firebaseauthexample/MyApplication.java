package com.geek.firebaseauthexample;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyApplication extends Application {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    public static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        if (mAuth == null)
            mAuth = FirebaseAuth.getInstance();
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance().getReference("events");
        }
        if (myApplication == null)
            myApplication = this;
    }

    public static MyApplication getApp() {
        return myApplication;
    }

    public FirebaseAuth getFirebaseAuth() {
        return mAuth;
    }

    public FirebaseUser getFirebaseUser() {
        return mAuth.getCurrentUser();
    }

    public DatabaseReference getFirebaseDB() {
        return mDatabase;
    }

    public boolean isUserLoggedIn() {
        if (mAuth != null) {
            if (mAuth.getCurrentUser() != null)
                return true;
            else
                return false;
        } else
            return false;
    }
}
