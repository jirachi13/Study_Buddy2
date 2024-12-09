package com.example.studybuddy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TaskDetailActivity extends AppCompatActivity {

    private TextView taskDetailTitle, taskDetailDescription, taskDetailDueDate;
    private ImageView completeIcon, deleteIcon;
    private Button viewTasksButton;
    private int taskId;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        // Find views
        taskDetailTitle = findViewById(R.id.taskDetailTitle);
        taskDetailDescription = findViewById(R.id.taskDetailDescription);
        taskDetailDueDate = findViewById(R.id.taskDetailDueDate);
        completeIcon = findViewById(R.id.taskCompleteIcon);
        deleteIcon = findViewById(R.id.taskDeleteIcon);
        viewTasksButton = findViewById(R.id.viewTaskButton);  // Ensure this ID is correct

        databaseHelper = new DatabaseHelper(this);

        // Get the task ID passed from TaskManagerActivity
        taskId = getIntent().getIntExtra("task_id", -1);

        if (taskId != -1) {
            Task task = databaseHelper.getTaskById(taskId);
            if (task != null) {
                // Set task details in the views
                taskDetailTitle.setText(task.getTitle());
                taskDetailDescription.setText(task.getDescription());
                taskDetailDueDate.setText(task.getDueDate());

                // Complete action
                completeIcon.setOnClickListener(v -> {
                    // Mark task as complete (you can set a flag or update the task status)
                    Toast.makeText(TaskDetailActivity.this, "Task marked as complete", Toast.LENGTH_SHORT).show();
                    // Optionally, update the task's status in the database
                    finish(); // Close activity and return to task manager
                });

                // Delete action
                deleteIcon.setOnClickListener(v -> {
                    // Delete the task from the database
                    databaseHelper.deleteTask(taskId);
                    Toast.makeText(TaskDetailActivity.this, "Task deleted", Toast.LENGTH_SHORT).show();
                    finish(); // Close activity and return to task manager
                });

                // View Tasks Button - Just close this activity and return to Task Manager
                viewTasksButton.setOnClickListener(v -> finish());  // Close the activity and return to the previous screen
            } else {
                // Show error message if task is not found
                Toast.makeText(this, "Task not found", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Show error message if invalid task ID is passed
            Toast.makeText(this, "Invalid task ID", Toast.LENGTH_SHORT).show();
        }
    }
}
