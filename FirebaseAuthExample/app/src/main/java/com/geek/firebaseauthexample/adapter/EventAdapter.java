package com.geek.firebaseauthexample.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.geek.firebaseauthexample.R;
import com.geek.firebaseauthexample.model.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventVH> {
    List<Event> events;
    private final String dateFormat = "dd MMM, yyyy hh:mm a";
    private SimpleDateFormat simpleDateFormat;
    private EventClickListener listener;

    public EventAdapter(EventClickListener clickListener) {
        events = new ArrayList<>();
        this.listener = clickListener;
        simpleDateFormat = new SimpleDateFormat(dateFormat);
    }

    public void populateItems(List<Event> events) {
        if (this.events != null)
            this.events.clear();
        this.events.addAll(events);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View convertView = inflater.inflate(R.layout.row_event, viewGroup, false);
        return (new EventVH(convertView));
    }

    @Override
    public void onBindViewHolder(@NonNull EventVH eventVH, int i) {
        Event event = events.get(i);
        eventVH.bind(event);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    class EventVH extends RecyclerView.ViewHolder {
        TextView tvEventTitle;
        TextView tvEventDate;
        TextView tvAgenda;
        TextView tvAttendees;
        ImageView ivEdit;
        ImageView ivDelete;

        public EventVH(@NonNull View itemView) {
            super(itemView);
            tvEventTitle = itemView.findViewById(R.id.tvEventTitle);
            tvEventDate = itemView.findViewById(R.id.tvDate);
            tvAgenda = itemView.findViewById(R.id.tvAgenda);
            tvAttendees = itemView.findViewById(R.id.tvAttendees);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }

        void bind(final Event event) {
            tvEventTitle.setText(event.getTitle());
            tvEventDate.setText(simpleDateFormat.format(event.getTimestamp()));
            tvAgenda.setText(event.getAgenda());
            tvAttendees.setText(event.getAttendees());
            ivEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEditBtnTapped(event);
                }
            });
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteBtnTapped(event);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEventTapped(event);
                }
            });
        }
    }

    public interface EventClickListener {
        void onEventTapped(Event event);

        void onEditBtnTapped(Event event);

        void onDeleteBtnTapped(Event event);
    }
}