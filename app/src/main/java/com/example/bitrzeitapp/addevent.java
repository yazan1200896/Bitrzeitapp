package com.example.bitrzeitapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class addevent extends AppCompatActivity {

    private EditText eventNameEditText;
    private Spinner locationSpinner;
    private DatePicker datePicker;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;
    private Button buttonAddEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addevent);
        FirebaseApp.initializeApp(this);

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("event");

        // Initialize shared preferences
        sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);

        // Initialize UI elements
        eventNameEditText = findViewById(R.id.editTextEventName);
        locationSpinner = findViewById(R.id.spinnerLocation);
        datePicker = findViewById(R.id.datePicker);
        buttonAddEvent = findViewById(R.id.addevent);
        buttonAddEvent.setEnabled(false);  // Initially set the button to be disabled

        // Initialize location spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.location_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);

        // Add a text change listener to the eventNameEditText
        eventNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not needed for this example
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Check if the text field is empty
                boolean isTextFieldEmpty = charSequence.toString().trim().isEmpty();

                // Enable or disable the button based on the text field's emptiness
                buttonAddEvent.setEnabled(!isTextFieldEmpty);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Not needed for this example
            }
        });

        buttonAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get values from UI elements
                String eventName = eventNameEditText.getText().toString();
                String location = locationSpinner.getSelectedItem().toString();

                // Check if the text field is not empty
                if (!eventName.isEmpty()) {
                    // Format the selected date from DatePicker
                    int day = datePicker.getDayOfMonth();
                    int month = datePicker.getMonth();
                    int year = datePicker.getYear();
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, day);
                    Date selectedDate = calendar.getTime();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = dateFormat.format(selectedDate);

                    // Get user ID from SharedPreferences
                    String userId = sharedPreferences.getString("id", "");

                    // Add event to Firebase with user ID
                    addEventToFirebase(eventName, location, formattedDate, userId);

                    // Show success message
                    Toast.makeText(addevent.this, "Event added successfully", Toast.LENGTH_SHORT).show();

                    // Clear text field
                    eventNameEditText.getText().clear();
                } else {
                    // Show an error message or handle the case when the text field is empty
                    Toast.makeText(addevent.this, "Event name cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addEventToFirebase(String eventName, String location, String date, String userId) {
        // Create a unique key for the event
        String eventId = databaseReference.push().getKey();

        // Create an Event object
        Events event = new Events(eventName, location, date, userId);

        // Add the event to Firebase
        databaseReference.child(eventId).setValue(event);
    }
}
