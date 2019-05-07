package com.geek.firebaseauthexample.viewmodel;

import android.app.Application;

import com.geek.firebaseauthexample.interfaces.CreateEventCallback;
import com.geek.firebaseauthexample.model.Event;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import static com.geek.firebaseauthexample.MyApplication.getApp;

public class CreateEventViewModel extends AndroidViewModel {
    private static final String TAG = "CreateEventViewModel";
    private long timeStamp = 0;
    private final String dateFormat = "dd MMM, yyyy";
    private final String timeFormat = "hh:mm a";
    private Calendar calendar;
    private MutableLiveData<String> formattedDate;
    private MutableLiveData<String> formattedTime;
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat simpleTimeFormat;

    public CreateEventViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(Event event) {
        formattedDate = new MutableLiveData<>();
        formattedTime = new MutableLiveData<>();
        if (event.getTimestamp() == 0) {
            getCurrentTimeStamp();
        } else {
            timeStamp = event.getTimestamp();
        }
        simpleDateFormat = new SimpleDateFormat(dateFormat);
        simpleTimeFormat = new SimpleDateFormat(timeFormat);
    }

    public void createEvent(Event event, final CreateEventCallback createEventCallback) {
        event.setTimestamp(timeStamp);
        getApp().getFirebaseDB().child(getApp().getFirebaseUser().getUid()).child(String.valueOf(event.getId())).setValue(event)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        createEventCallback.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        createEventCallback.onFailure();
                    }
                });
    }

    public int createUniqueEventId() {
        Random rand = new Random();
        return rand.nextInt(100);
    }

    public LiveData<String> getDate() {
        formattedDate.postValue(simpleDateFormat.format(timeStamp));
        return formattedDate;
    }

    public LiveData<String> getTime() {
        formattedTime.postValue(simpleTimeFormat.format(timeStamp));
        return formattedTime;
    }

    private void getCurrentTimeStamp() {
        if (timeStamp == 0) {
            calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            timeStamp = calendar.getTimeInMillis();
        }
    }

    public void setDate(int year, int month, int day) {
        calendar.set(year, month, day);
        timeStamp = calendar.getTimeInMillis();
        formattedDate.postValue(simpleDateFormat.format(timeStamp));
    }

    public void setTime(int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        timeStamp = calendar.getTimeInMillis();
        formattedTime.postValue(simpleTimeFormat.format(timeStamp));
    }
}
