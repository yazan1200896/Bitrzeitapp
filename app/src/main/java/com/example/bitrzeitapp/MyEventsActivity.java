package com.example.bitrzeitapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyEventsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);
        FirebaseApp.initializeApp(this);

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("event");

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);

        // Initialize UI elements
        recyclerView = findViewById(R.id.recyclerViewMyEvents);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventAdapter = new EventAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(eventAdapter);

        // Retrieve and display events for the current user from Firebase
        retrieveAndDisplayMyEvents();
    }

    private void retrieveAndDisplayMyEvents() {
        // Get user ID from SharedPreferences
        String userId = sharedPreferences.getString("id", "");

        // Add a ValueEventListener to listen for changes in the Firebase database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear previous events
                eventAdapter.clear();

                // Iterate through all events
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    Events event = eventSnapshot.getValue(Events.class);

                    // Check if the event and its userId are not null
                    if (event != null && event.getUserId() != null && event.getUserId().equals(userId)) {
                        eventAdapter.addEvent(event);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error if needed
            }
        });
    }
}

