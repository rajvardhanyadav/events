package com.geek.firebaseauthexample.utils.filter;

import java.util.ArrayList;
import java.util.List;

public class FilterEvents<F, T> {
    public <E> List filterList(List<E> originalList, Filter filter, F from, T to) {
        List<E> filterList = new ArrayList<E>();
        for (E object : originalList) {
            if (filter.isInValidRange(object, from, to)) {
                filterList.add(object);
            } else {
                continue;
            }
        }
        return filterList;
    }
}
