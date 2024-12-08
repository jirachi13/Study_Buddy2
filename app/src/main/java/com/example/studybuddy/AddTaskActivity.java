package com.example.studybuddy;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    private EditText taskTitleInput, taskDescriptionInput;
    private TextView dueDateTextView;
    private Button submitButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskTitleInput = findViewById(R.id.taskTitleInput);
        taskDescriptionInput = findViewById(R.id.taskDescriptionInput);
        dueDateTextView = findViewById(R.id.dueDateTextView);
        submitButton = findViewById(R.id.submitButton);
        databaseHelper = new DatabaseHelper(this);

        dueDateTextView.setOnClickListener(v -> openDatePicker());

        submitButton.setOnClickListener(v -> {
            String title = taskTitleInput.getText().toString().trim();
            String description = taskDescriptionInput.getText().toString().trim();
            String dueDate = dueDateTextView.getText().toString().trim();

            if (!title.isEmpty() && !description.isEmpty() && !dueDate.isEmpty()) {
                databaseHelper.addTask(title, description, dueDate);
                finish();
            }
        });
    }

    private void openDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, dayOfMonth) -> {
            dueDateTextView.setText((selectedMonth + 1) + "/" + dayOfMonth + "/" + selectedYear);
        }, year, month, day);

        datePickerDialog.show();
    }
}
