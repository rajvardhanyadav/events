package com.geek.firebaseauthexample.utils.filter;

public interface Filter<D, F, T> {
    public boolean isInValidRange(D object, F from, T to);
}
