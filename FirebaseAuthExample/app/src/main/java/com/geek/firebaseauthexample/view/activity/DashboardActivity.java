package com.geek.firebaseauthexample.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.geek.firebaseauthexample.R;
import com.geek.firebaseauthexample.adapter.EventAdapter;
import com.geek.firebaseauthexample.model.Event;
import com.geek.firebaseauthexample.view.fragment.DateRangeFragment;
import com.geek.firebaseauthexample.viewmodel.DashboardViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.geek.firebaseauthexample.MyApplication.getApp;

public class DashboardActivity extends BaseActivity implements View.OnClickListener, EventAdapter.EventClickListener, DateRangeFragment.DateRangeSelectionListener {
    private static final String TAG = "DashboardActivity";
    private DashboardViewModel dashboardViewModel;
    private FloatingActionButton fabCreateEvent;
    private EventAdapter eventAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        fabCreateEvent = findViewById(R.id.fabCreateEvent);
        recyclerView = findViewById(R.id.recylerview);
        fabCreateEvent.setOnClickListener(this);
        eventAdapter = new EventAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(eventAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getEvents();
    }

    private void getEvents() {
        showProgressDialog();
        dashboardViewModel.getEvents().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                dismissProgressDialog();
                eventAdapter.populateItems(events);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fabCreateEvent:
                startActivity(new Intent(DashboardActivity.this, CreateEventActivity.class));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_filter:
                selectDateRange();
                return true;
            case R.id.menu_events:

                return true;
            case R.id.menu_logout:
                getApp().getFirebaseAuth().signOut();
                startActivity(new Intent(this, SignInActivity.class));
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void selectDateRange() {
        androidx.fragment.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DialogFragment dialogFragment = new DateRangeFragment();
        ((DateRangeFragment) dialogFragment).setListener(this);
        dialogFragment.show(ft, "Select Date Range");
    }

    @Override
    public void onEventTapped(Event item) {

    }

    @Override
    public void onEditBtnTapped(Event event) {
        Intent intent = new Intent(DashboardActivity.this, CreateEventActivity.class);
        intent.putExtra("event", event);
        startActivity(intent);
    }

    @Override
    public void onDeleteBtnTapped(Event event) {
        Toast.makeText(this, "Deleting event...",
                Toast.LENGTH_SHORT).show();
        dashboardViewModel.deleteEvent(event);
    }

    @Override
    public void onDateRangeSelected(long fromDateTimeStamp, long toDateTimeStamp) {
        dashboardViewModel.filterEvents(fromDateTimeStamp, toDateTimeStamp);
    }
}
