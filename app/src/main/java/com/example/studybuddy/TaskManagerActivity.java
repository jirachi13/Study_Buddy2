package com.example.studybuddy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class TaskManagerActivity extends AppCompatActivity {

    private LinearLayout taskListContainer;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager);

        taskListContainer = findViewById(R.id.taskListContainer);
        databaseHelper = new DatabaseHelper(this);

        populateTaskList();
    }

    private void populateTaskList() {
        taskListContainer.removeAllViews();

        List<Task> tasks = databaseHelper.getAllTasks();
        for (Task task : tasks) {
            View taskView = LayoutInflater.from(this).inflate(R.layout.task_item, taskListContainer, false);

            TextView taskTitle = taskView.findViewById(R.id.taskTitle);
            TextView taskDescription = taskView.findViewById(R.id.taskDescription);
            TextView taskDueDate = taskView.findViewById(R.id.taskDueDate);

            taskTitle.setText(task.getTitle());
            taskDescription.setText(task.getDescription());
            taskDueDate.setText(task.getDueDate());

            taskListContainer.addView(taskView);
        }
    }
}
