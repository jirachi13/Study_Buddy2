package com.example.studybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView taskManagerIcon = findViewById(R.id.taskManagerIcon);
        ImageView pomodoroTimerIcon = findViewById(R.id.pomodoroTimerIcon);
        ImageView addTaskIcon = findViewById(R.id.addTaskIcon);
        ImageView notesIcon = findViewById(R.id.notesIcon);
        ImageView calendarIcon = findViewById(R.id.calendarIcon);

        taskManagerIcon.setOnClickListener(v -> startActivity(new Intent(this, TaskManagerActivity.class)));
        pomodoroTimerIcon.setOnClickListener(v -> startActivity(new Intent(this, PomodoroTimerActivity.class)));
        addTaskIcon.setOnClickListener(v -> startActivity(new Intent(this, AddTaskActivity.class)));
        notesIcon.setOnClickListener(v -> startActivity(new Intent(this, NotesActivity.class)));
        calendarIcon.setOnClickListener(v -> startActivity(new Intent(this, CalendarActivity.class)));
    }
}
