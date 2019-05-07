package com.geek.firebaseauthexample.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.geek.firebaseauthexample.R;
import com.geek.firebaseauthexample.interfaces.CreateEventCallback;
import com.geek.firebaseauthexample.model.Event;
import com.geek.firebaseauthexample.view.fragment.DatePickerFragment;
import com.geek.firebaseauthexample.view.fragment.TimePickerFragment;
import com.geek.firebaseauthexample.viewmodel.CreateEventViewModel;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class CreateEventActivity extends BaseActivity implements View.OnClickListener,
        CreateEventCallback,
        DatePickerFragment.OnDateSelection, TimePickerFragment.OnTimeSelection {
    private static final String TAG = "CreateEventActivity";
    private CreateEventViewModel createEventViewModel;
    private EditText etEventTitle;
    private EditText etAgenda;
    private EditText etAttendees;
    private Button btnCreate;
    private TextView tvDate;
    private TextView tvTime;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        etEventTitle = findViewById(R.id.etEventTitle);
        etAgenda = findViewById(R.id.etAgenda);
        etAttendees = findViewById(R.id.etAttendees);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        btnCreate = findViewById(R.id.btnCreate);
        tvDate.setOnClickListener(this);
        tvTime.setOnClickListener(this);
        btnCreate.setOnClickListener(this);
        if (getIntent().hasExtra("event")) {
            event = (Event) getIntent().getSerializableExtra("event");
            if (event != null) {
                etEventTitle.setText(event.getTitle());
                etAgenda.setText(event.getAgenda());
                etAttendees.setText(event.getAttendees());
                btnCreate.setText(R.string.update);
            }
        } else {
            event = new Event();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        createEventViewModel = ViewModelProviders.of(this).get(CreateEventViewModel.class);
        createEventViewModel.init(event);
        if (event.getId() == 0)
            event.setId(createEventViewModel.createUniqueEventId());
    }

    @Override
    protected void onResume() {
        super.onResume();
        createEventViewModel.getDate().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvDate.setText(s);
            }
        });

        createEventViewModel.getTime().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvTime.setText(s);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnCreate:
                String title = etEventTitle.getText().toString();
                String agenda = etAgenda.getText().toString();
                String attendees = etAttendees.getText().toString();
                if (title.isEmpty() || agenda.isEmpty() || attendees.isEmpty()) {
                    Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    createEvent(title, agenda, attendees);
                }
                break;
            case R.id.tvDate:
                DialogFragment datePickerFragment = new DatePickerFragment();
                ((DatePickerFragment) datePickerFragment).setListener(this);
                datePickerFragment.show(getSupportFragmentManager(), "Date");
                break;
            case R.id.tvTime:
                DialogFragment timePickerFragment = new TimePickerFragment();
                ((TimePickerFragment) timePickerFragment).setListener(this);
                timePickerFragment.show(getSupportFragmentManager(), "Time");
                break;
        }
    }

    private void createEvent(String title, String agenda, String attendees) {
        showProgressDialog();
        event.setTitle(title);
        event.setAgenda(agenda);
        event.setAttendees(attendees);
        createEventViewModel.createEvent(event, this);
    }

    @Override
    public void onSuccess() {
        dismissProgressDialog();
        Toast.makeText(this, "Event created successfully",
                Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onFailure() {
        dismissProgressDialog();
        Toast.makeText(this, "Event creation failed. Please try again",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateSet(int year, int month, int day) {
        createEventViewModel.setDate(year, month, day);
    }

    @Override
    public void onDateSet(int year, int month, int day, String type) {

    }

    @Override
    public void onTimeSet(int hourOfDay, int minute) {
        createEventViewModel.setTime(hourOfDay, minute);
    }
}