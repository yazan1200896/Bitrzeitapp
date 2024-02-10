package com.example.bitrzeitapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Events> eventsList;
    private Context context;

    public EventAdapter(List<Events> eventsList, Context context) {
        this.eventsList = eventsList;
        this.context = context;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Events event = eventsList.get(position);

        holder.textViewEventName.setText("Event Name: " + event.getEventName());
        holder.textViewLocation.setText("Location: " + event.getLocation());
        holder.textViewDate.setText("Date: " + event.getDate());
        holder.textViewUserId.setText("User ID: " + event.getUserId());
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public void addEvent(Events event) {
        eventsList.add(event);
        notifyDataSetChanged();
    }

    public void clear() {
        eventsList.clear();
        notifyDataSetChanged();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView textViewEventName, textViewLocation, textViewDate, textViewUserId;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewEventName = itemView.findViewById(R.id.textViewEventName);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewUserId = itemView.findViewById(R.id.textViewUserId);
        }
    }
}
