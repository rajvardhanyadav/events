package com.geek.firebaseauthexample.interfaces;

import com.geek.firebaseauthexample.model.Event;

import java.util.List;

public interface EventCallback {
    void onSuccess(List<Event> events);
    void onFailure();
}
