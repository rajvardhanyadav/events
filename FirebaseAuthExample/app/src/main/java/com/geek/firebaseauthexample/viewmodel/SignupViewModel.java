package com.geek.firebaseauthexample.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class SignupViewModel extends AndroidViewModel {
    private static final String TAG = "SignupViewModel";
    private Context context;

    public SignupViewModel(@NonNull Application application) {
        super(application);
        this.context = application;
    }


}
