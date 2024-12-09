package com.example.studybuddy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private LinearLayout eventsListContainer;
    private TextView selectedDateTextView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = findViewById(R.id.calendarView);
        eventsListContainer = findViewById(R.id.eventsListContainer);
        selectedDateTextView = findViewById(R.id.selectedDateTextView);
        databaseHelper = new DatabaseHelper(this);

        // Set default text in selectedDateTextView
        selectedDateTextView.setText("Select a Date");

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Format the selected date as mm/dd/yyyy
            String selectedDate = (month + 1) + "/" + dayOfMonth + "/" + year;
            selectedDateTextView.setText(selectedDate);
            populateEventsForDate(selectedDate);
        });
    }

    private void populateEventsForDate(String date) {
        eventsListContainer.removeAllViews();

        // Get tasks from the database
        List<Task> tasks = databaseHelper.getAllTasks();
        for (Task task : tasks) {
            if (task.getDueDate().equals(date)) {
                // Inflate each task into the layout
                View eventView = LayoutInflater.from(this).inflate(R.layout.event_item, eventsListContainer, false);

                TextView eventTitle = eventView.findViewById(R.id.eventTitle);
                TextView eventDescription = eventView.findViewById(R.id.eventDescription);

                eventTitle.setText(task.getTitle());
                eventDescription.setText(task.getDescription());

                // Add the task to the events list container
                eventsListContainer.addView(eventView);
            }
        }
    }
}
