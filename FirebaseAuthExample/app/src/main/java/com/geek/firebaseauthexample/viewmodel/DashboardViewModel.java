package com.geek.firebaseauthexample.viewmodel;

import android.app.Application;

import com.geek.firebaseauthexample.model.Event;
import com.geek.firebaseauthexample.utils.filter.Filter;
import com.geek.firebaseauthexample.utils.filter.FilterEvents;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import static com.geek.firebaseauthexample.MyApplication.getApp;

public class DashboardViewModel extends AndroidViewModel {
    private MutableLiveData<List<Event>> events = new MutableLiveData<>();
    private List<Event> updatedEvents;

    public DashboardViewModel(@NonNull Application application) {
        super(application);

    }

    public LiveData<List<Event>> getEvents() {
        getAllEvents();
        return events;
    }

    public void getAllEvents() {
        DatabaseReference eventRef = getApp().getFirebaseDB().child(getApp().getFirebaseUser().getUid());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                updatedEvents = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Event event = new Event();
                    event.setId(ds.child("id").getValue(Integer.class));
                    event.setTitle(ds.child("title").getValue(String.class));
                    event.setAgenda(ds.child("agenda").getValue(String.class));
                    event.setAttendees(ds.child("attendees").getValue(String.class));
                    event.setTimestamp(ds.child("timestamp").getValue(Long.class));
                    updatedEvents.add(event);
                }
                events.postValue(updatedEvents);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        eventRef.addListenerForSingleValueEvent(valueEventListener);
    }

    public void updateEvent(Event event) {

    }

    public void deleteEvent(Event event) {
        DatabaseReference eventRef = getApp().getFirebaseDB().child(getApp().getFirebaseUser().getUid()).child(String.valueOf(event.getId()));
        eventRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                getAllEvents();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }


    public void filterEvents(long fromDateTimeStamp, long toDateTimeStamp) {
        Filter<Event, Long, Long> filter = new Filter<Event, Long, Long>() {
            public boolean isInValidRange(Event event, Long from, Long to) {
                if (event.getTimestamp() >= from && event.getTimestamp() <= to)
                    return true;
                else
                    return false;
            }
        };
        List<Event> filteredItems = new FilterEvents<>().filterList(updatedEvents, filter, fromDateTimeStamp, toDateTimeStamp);
        events.postValue(filteredItems);
    }
}
