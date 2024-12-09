package com.example.studybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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

        Button addTaskButton = findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(v -> {
            // Redirect to add task activity
            startActivity(new Intent(this, AddTaskActivity.class));
        });

        populateTaskList();
    }

    private void populateTaskList() {
        taskListContainer.removeAllViews();

        List<Task> tasks = databaseHelper.getAllTasks();
        if (tasks.isEmpty()) {
            TextView emptyView = new TextView(this);
            emptyView.setText("No tasks available.");
            emptyView.setTextSize(16);
            emptyView.setTextColor(getResources().getColor(R.color.gray));
            taskListContainer.addView(emptyView);
        } else {
            for (Task task : tasks) {
                View taskView = LayoutInflater.from(this).inflate(R.layout.task_item, taskListContainer, false);

                TextView taskTitle = taskView.findViewById(R.id.taskTitle);
                TextView taskDescription = taskView.findViewById(R.id.taskDescription);
                TextView taskDueDate = taskView.findViewById(R.id.taskDueDate);

                taskTitle.setText(task.getTitle());
                taskDescription.setText(task.getDescription());
                taskDueDate.setText(task.getDueDate());

                // Set a click listener for each task item
                taskView.setOnClickListener(v -> {
                    // Pass the task ID to the task detail activity
                    Intent intent = new Intent(TaskManagerActivity.this, TaskDetailActivity.class);
                    intent.putExtra("task_id", task.getId());
                    startActivity(intent);
                });

                taskListContainer.addView(taskView);
            }
        }
    }
}
