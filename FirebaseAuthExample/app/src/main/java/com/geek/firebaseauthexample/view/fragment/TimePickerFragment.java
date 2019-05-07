package com.geek.firebaseauthexample.view.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;

import androidx.fragment.app.DialogFragment;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    private OnTimeSelection onTimeSelection;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void setListener(TimePickerFragment.OnTimeSelection onTimeSelection) {
        this.onTimeSelection = onTimeSelection;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minute, false);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        onTimeSelection.onTimeSet(hourOfDay, minute);
    }

    public interface OnTimeSelection {
        void onTimeSet(int hourOfDay, int minute);
    }
}
