package com.geek.firebaseauthexample.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geek.firebaseauthexample.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DateRangeFragment extends DialogFragment implements View.OnClickListener, DatePickerFragment.OnDateSelection {
    private LinearLayout llFromDate;
    private LinearLayout llToDate;
    private TextView tvFromDate;
    private TextView tvToDate;
    private Button btnDone;
    private long fromDateTimeStamp;
    private long toDateTimeStamp;
    private final String dateFormat = "dd MMM, yyyy";
    private SimpleDateFormat simpleDateFormat;
    private final String TYPE_FROM_DATE = "fromDate";
    private final String TYPE_TO_DATE = "toDate";
    private Calendar calendar;
    private DateRangeSelectionListener dateRangeSelectionListener;

    public DateRangeFragment() {
        // Required empty public constructor
    }

    public void setListener(DateRangeSelectionListener dateRangeSelectionListener) {
        this.dateRangeSelectionListener = dateRangeSelectionListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_date_range, container, false);
        llFromDate = view.findViewById(R.id.layoutFromDate);
        llToDate = view.findViewById(R.id.layoutToDate);
        tvFromDate = view.findViewById(R.id.tvFromDate);
        tvToDate = view.findViewById(R.id.tvToDate);
        btnDone = view.findViewById(R.id.btnDone);
        llFromDate.setOnClickListener(this);
        llToDate.setOnClickListener(this);
        btnDone.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.layoutFromDate:
                DialogFragment fromDatePickerFragment = new DatePickerFragment();
                ((DatePickerFragment) fromDatePickerFragment).setListener(this, TYPE_FROM_DATE);
                fromDatePickerFragment.show(getChildFragmentManager(), "FromDate");
                break;
            case R.id.layoutToDate:
                DialogFragment toDatePickerFragment = new DatePickerFragment();
                ((DatePickerFragment) toDatePickerFragment).setListener(this, TYPE_TO_DATE);
                toDatePickerFragment.show(getChildFragmentManager(), "ToDate");
                break;
            case R.id.btnDone:
                dateRangeSelectionListener.onDateRangeSelected(fromDateTimeStamp, toDateTimeStamp);
                dismiss();
                break;
        }
    }

    private void init() {
        calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        fromDateTimeStamp = calendar.getTimeInMillis();
        toDateTimeStamp = calendar.getTimeInMillis();
        simpleDateFormat = new SimpleDateFormat(dateFormat);
        tvFromDate.setText(simpleDateFormat.format(fromDateTimeStamp));
        tvToDate.setText(simpleDateFormat.format(toDateTimeStamp));
    }

    @Override
    public void onDateSet(int year, int month, int day) {

    }

    @Override
    public void onDateSet(int year, int month, int day, String type) {
        calendar.set(year, month, day);
        if (type.equals(TYPE_FROM_DATE)) {
            fromDateTimeStamp = calendar.getTimeInMillis();
            tvFromDate.setText(simpleDateFormat.format(fromDateTimeStamp));
        } else if (type.equals(TYPE_TO_DATE)) {
            toDateTimeStamp = calendar.getTimeInMillis();
            tvToDate.setText(simpleDateFormat.format(toDateTimeStamp));
        }
    }

    public interface DateRangeSelectionListener {
        void onDateRangeSelected(long fromDateTimeStamp, long toDateTimeStamp);
    }
}