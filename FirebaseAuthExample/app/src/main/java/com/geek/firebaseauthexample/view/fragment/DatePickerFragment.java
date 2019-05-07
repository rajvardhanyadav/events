package com.geek.firebaseauthexample.view.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

import androidx.fragment.app.DialogFragment;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    DatePickerFragment.OnDateSelection onDateSelectionListener;
    String type;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void setListener(DatePickerFragment.OnDateSelection onDateSelectionListener) {
        this.onDateSelectionListener = onDateSelectionListener;
    }

    public void setListener(DatePickerFragment.OnDateSelection onDateSelectionListener, String type) {
        this.onDateSelectionListener = onDateSelectionListener;
        this.type = type;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        if (type == null || type.isEmpty())
            onDateSelectionListener.onDateSet(year, month, day);
        else
            onDateSelectionListener.onDateSet(year, month, day, type);
    }

    public interface OnDateSelection {
        void onDateSet(int year, int month, int day);

        void onDateSet(int year, int month, int day, String type);
    }
}